package com.example.codearena.repository;

import com.example.codearena.entity.Problem;
import com.example.codearena.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepo extends JpaRepository<TestCase, Long>{
    List<TestCase> findByProblem(Problem problem);
}
