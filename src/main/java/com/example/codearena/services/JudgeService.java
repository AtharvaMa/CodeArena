package com.example.codearena.services;


import com.example.codearena.entity.Submission;
import com.example.codearena.entity.Verdict;
import org.springframework.stereotype.Service;

@Service
public class JudgeService {

    public Verdict evaluate(Submission submission) {

        // TODO: integrate Docker execution later

        // Dummy logic for now
        if (submission.getCode().contains("return")) {
            return Verdict.PASSED;
        }

        return Verdict.FAILED;
    }
}
