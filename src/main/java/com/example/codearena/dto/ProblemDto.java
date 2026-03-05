package com.example.codearena.dto;

import lombok.Data;

import java.util.List;
@Data
@lombok.Builder
public class ProblemDto {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String timeComplexity;
    private String spaceComplexity;
    private List<TestCaseDto> testCases;
}
