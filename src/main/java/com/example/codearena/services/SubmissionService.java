package com.example.codearena.services;

import com.example.codearena.dto.SubmissionRequestDto;
import com.example.codearena.entity.Problem;
import com.example.codearena.entity.Submission;
import com.example.codearena.entity.User;
import com.example.codearena.entity.Verdict;
import com.example.codearena.repository.ProblemRepo;
import com.example.codearena.repository.SubmissionRepo;
import com.example.codearena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepo submissionRepo;
    private final ProblemRepo problemRepo;
    private final UserRepository userRepository;

    public Submission submitCode(SubmissionRequestDto request) {
        // 🔥 Use the ID from the request instead of SecurityContextHolder
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Problem problem = problemRepo.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Submission submission = Submission.builder()
                .code(request.getCode())
                .language(request.getLanguage())
                .verdict(Verdict.PENDING)
                .user(user)
                .problem(problem)
                .submittedAt(LocalDateTime.now())
                .build();

        return submissionRepo.save(submission);
    }
}
