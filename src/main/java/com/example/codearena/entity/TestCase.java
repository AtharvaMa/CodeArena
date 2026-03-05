package com.example.codearena.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String inputData;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @Builder.Default // 🔥 Add this
    @Column(nullable = false)
    private Boolean hidden = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
}
