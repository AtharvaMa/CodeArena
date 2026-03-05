package com.example.codearena.services;

import com.example.codearena.entity.Submission;
import com.example.codearena.entity.TestCase;
import com.example.codearena.entity.Verdict;
import com.example.codearena.repository.SubmissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final SubmissionRepo submissionRepo;
    private final RestTemplate restTemplate;
    private final CodeWrapperService codeWrapperService;

    private final String JDOODLE_URL = "https://api.jdoodle.com/v1/execute";

    // 🔥 Paste your JDoodle credentials here
    private final String CLIENT_ID = "e602b4dd6b86febffddb82820750f066";
    private final String CLIENT_SECRET = "bf044be48730c970793072cc1549244aeface90a03114bd549292d35cbedd346";


    public Submission executeSubmission(Long submissionId) {
        Submission submission = submissionRepo.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        List<TestCase> testCases = submission.getProblem().getTestCases();
        String jdoodleLanguage = getJdoodleLanguage(submission.getLanguage());
        String versionIndex = getLanguageVersion(submission.getLanguage());

        boolean allPassed = true;

        for (TestCase testCase : testCases) {

            String finalCode = submission.getCode();
            String lang = submission.getLanguage().toUpperCase().trim();

            if (lang.equals("JAVA")) {
                finalCode = codeWrapperService.wrapJava(finalCode);
            } else if (lang.equals("PYTHON")) {
                finalCode = codeWrapperService.wrapPython(finalCode);
            } else if (lang.equals("CPP")) {
                finalCode = codeWrapperService.wrapCpp(finalCode);
            }

            // 1. Prepare the JDoodle JSON Payload
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("clientId", CLIENT_ID);
            requestBody.put("clientSecret", CLIENT_SECRET);
            requestBody.put("script", finalCode);
            requestBody.put("language", jdoodleLanguage);
            requestBody.put("versionIndex", versionIndex);
            requestBody.put("stdin", testCase.getInputData());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            try {
                // 2. Send to JDoodle
                ResponseEntity<Map> response = restTemplate.exchange(
                        JDOODLE_URL, HttpMethod.POST, entity, Map.class
                );

                Map<String, Object> responseBody = response.getBody();

                // 3. Extract output and memory/cpu stats
                String output = (String) responseBody.get("output");
                Integer statusCode = (Integer) responseBody.get("statusCode");

                // 🔥 Let's see EXACTLY what JDoodle is returning!
                System.out.println("JDoodle Full Response: " + responseBody);

                Object cpuObj = responseBody.get("cpuTime");
                Object memObj = responseBody.get("memory");

                try {
                    // Handle CPU Time (Fallback to 0 if it was too fast to measure)
                    if (cpuObj != null) {
                        double cpuSec = Double.parseDouble(String.valueOf(cpuObj));
                        submission.setExecutionTimeMs((int)(cpuSec * 1000.0));
                    } else {
                        submission.setExecutionTimeMs(0); // 0 ms execution!
                    }

                    // Handle Memory (Convert KB to MB)
                    if (memObj != null) {
                        double memoryKb = Double.parseDouble(String.valueOf(memObj));
                        submission.setMemoryUsedMb((int)(memoryKb / 1024.0));
                    }

                    System.out.println("Successfully saved Stats -> Time: " + submission.getExecutionTimeMs() + "ms, Mem: " + submission.getMemoryUsedMb() + "MB");
                } catch (Exception e) {
                    System.out.println("Error parsing JDoodle stats: " + e.getMessage());
                }

                if (statusCode != 200) {
                    // 🔥 Add this line so we can see EXACTLY why it failed!
                    System.out.println("JDoodle Compiler Error: " + output);

                    submission.setVerdict(Verdict.CE);
                    allPassed = false;
                    break;
                }
                System.out.println("JDoodle Actual Output: '" + output + "'");
                System.out.println("Database Expected Output: '" + testCase.getExpectedOutput() + "'");


                // 4. Check if output matches expected output
                // .trim() removes any accidental spaces or hidden newlines!
                if (output != null && output.trim().equals(testCase.getExpectedOutput().trim())) {
                    // Test case passed! Continue to the next one.
                    continue;
                } else {
                    submission.setVerdict(Verdict.FAILED);
                    allPassed = false;
                    break; // Stop testing if one fails
                }

            } catch (Exception e) {
                System.out.println("Error calling JDoodle: " + e.getMessage());
                submission.setVerdict(Verdict.FAILED);
                allPassed = false;
                break;
            }
        }

        if (allPassed) {
            submission.setVerdict(Verdict.PASSED);
        }

       return  submissionRepo.save(submission);
    }

    // JDoodle uses specific string names for languages
    private String getJdoodleLanguage(String language) {
        return switch (language.toUpperCase()) {
            case "JAVA" -> "java";
            case "PYTHON" -> "python3";
            case "CPP" -> "cpp";
            default -> throw new RuntimeException("Unsupported Language");
        };
    }

    // JDoodle requires a version index (e.g., version 4 of Java is Java 17)
    private String getLanguageVersion(String language) {
        return switch (language.toUpperCase()) {
            case "JAVA", "CPP" -> "4";
            case "PYTHON" -> "3";
            default -> "0";
        };
    }
}