package com.example.codearena.services;

import com.example.codearena.dto.ProblemDto;
import com.example.codearena.dto.TestCaseDto;
import com.example.codearena.entity.Problem;
import com.example.codearena.entity.TestCase;
import com.example.codearena.repository.ProblemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepo problemRepo;

    public Problem createProblem(Problem problem) {

        // 🔥 1. Set the default values that Jackson set to null
        problem.setCreatedAt(LocalDateTime.now());

        if (problem.getTimeLimitMs() == null) {
            problem.setTimeLimitMs(1000);
        }
        if (problem.getMemoryLimitMb() == null) {
            problem.setMemoryLimitMb(256);
        }

        // 🔥 2. Link each test case back to the parent problem
        if (problem.getTestCases() != null) {
            for (TestCase testCase : problem.getTestCases()) {
                testCase.setProblem(problem);
            }
        }

        // 3. Save to database
        return problemRepo.save(problem);
    }

    public List<ProblemDto> getAllProblems() {
        List<Problem> problems = problemRepo.findAll();

        return problems.stream().map(problem -> {

            // 🔥 1. Filter out hidden test cases and convert to TestCaseDto
            List<TestCaseDto> visibleTestCases = problem.getTestCases().stream()
                    .filter(tc -> !tc.getHidden()) // Keep only if hidden is false
                    .map(tc -> TestCaseDto.builder()
                            .input(tc.getInputData())
                            .output(tc.getExpectedOutput())
                            .build())
                    .collect(Collectors.toList());

            // 🔥 2. Pack the safe data into a ProblemDto
            return ProblemDto.builder()
                    .id(problem.getId())
                    .title(problem.getTitle())
                    .description(problem.getDescription())
                    .difficulty(problem.getDifficulty().name())
                    .timeComplexity(problem.getTimeComplexity())
                    .spaceComplexity(problem.getSpaceComplexity())
                    .testCases(visibleTestCases) // Only the safe test cases are attached!
                    .build();

        }).collect(Collectors.toList());
    }
}