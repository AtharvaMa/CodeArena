package com.example.codearena.controller;

import com.example.codearena.dto.ProblemDto;
import com.example.codearena.entity.Problem;
import com.example.codearena.services.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<Problem> addProblem(@RequestBody Problem problem) {
        Problem savedProblem = problemService.createProblem(problem);
        return ResponseEntity.ok(savedProblem);
    }

    @GetMapping
    public ResponseEntity<List<ProblemDto>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }
}
