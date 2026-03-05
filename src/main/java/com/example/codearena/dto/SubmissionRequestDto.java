package com.example.codearena.dto;

import lombok.Data;

@Data
public class SubmissionRequestDto {
    private Long problemId;
    private Long userId;
    private String code;
    private String language;
}
