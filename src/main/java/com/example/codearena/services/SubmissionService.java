package com.example.codearena.services;

import com.example.codearena.entity.Problem;
import com.example.codearena.entity.Submission;
import com.example.codearena.entity.User;
import com.example.codearena.entity.Verdict;
import com.example.codearena.repository.ProblemRepo;
import com.example.codearena.repository.SubmissionRepo;
import com.example.codearena.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@lombok.RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepo submissionRepository;
    private final ProblemRepo problemRepository;
    private final UserRepository userRepository;
    private final JudgeService judgeService;

    public Submission submitCode(SubmissionRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Submission submission = Submission.builder()
                .code(request.getCode())
                .language(request.getLanguage())
                .verdict(Verdict.PENDING)
                .user(user)
                .problem(problem)
                .build();

        submission = submissionRepository.save(submission);

        // Call judge
        Verdict verdict = judgeService.evaluate(submission);

        submission.setVerdict(verdict);

        return submissionRepository.save(submission);
    }


}
