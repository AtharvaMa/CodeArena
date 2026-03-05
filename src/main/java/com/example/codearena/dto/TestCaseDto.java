package com.example.codearena.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCaseDto {
    private String input;
    private String output;
}
