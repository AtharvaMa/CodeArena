package com.example.codearena.controller;


import com.example.codearena.dto.SubmissionRequestDto;
import com.example.codearena.entity.Submission;
import com.example.codearena.services.ExecutionService;
import com.example.codearena.services.SubmissionRequest;
import com.example.codearena.services.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/submissions")
@RestController
public class SubmissionController {

    private final SubmissionService submissionService;
    private final ExecutionService executionService;

    public SubmissionController(SubmissionService submissionService, ExecutionService executionService) {
        this.submissionService = submissionService;
        this.executionService = executionService;
    }

    @PostMapping
    public ResponseEntity<Submission> submitCode(@RequestBody SubmissionRequestDto request) {
        // 1. This saves it to the DB as PENDING and keeps a copy in Java memory
        Submission savedSubmission = submissionService.submitCode(request);

        // 2. This goes to the DB, updates the DB row to PASSED/FAILED
        Submission finishedSubmission=executionService.executeSubmission(savedSubmission.getId());

        // 3. This returns the ORIGINAL Java memory copy to Postman!
        return ResponseEntity.ok(finishedSubmission);
    }
}
