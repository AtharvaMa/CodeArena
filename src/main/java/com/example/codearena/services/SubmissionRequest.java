package com.example.codearena.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Getter
@Setter
@AllArgsConstructor
public class SubmissionRequest {
    private String code;
    private String language;
    private Long problemId;

}
