package com.example.codearena.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    private String timeComplexity;

    private String spaceComplexity;

    @Builder.Default // 🔥 Add this
    @Column(nullable = false)
    private Integer timeLimitMs = 1000;

    @Builder.Default // 🔥 Add this
    @Column(nullable = false)
    private Integer memoryLimitMb = 256;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TestCase> testCases;

    @Builder.Default // 🔥 Add this
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
