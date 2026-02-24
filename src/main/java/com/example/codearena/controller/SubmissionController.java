package com.example.codearena.controller;


import com.example.codearena.entity.Submission;
import com.example.codearena.services.SubmissionRequest;
import com.example.codearena.services.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


@RequestMapping("/api/submissions")
@RestController
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<Submission> submit(
            @RequestBody SubmissionRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        Submission submission =
                submissionService.submitCode(request, username);

        return ResponseEntity.ok(submission);
    }
}
