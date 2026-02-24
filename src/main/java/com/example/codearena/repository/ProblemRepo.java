package com.example.codearena.repository;

import com.example.codearena.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepo extends JpaRepository<Problem, Long> {

}
