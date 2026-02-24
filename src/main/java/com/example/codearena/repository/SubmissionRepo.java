package com.example.codearena.repository;

import com.example.codearena.entity.Problem;
import com.example.codearena.entity.Submission;
import com.example.codearena.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {

    List<Submission> findByUser(User user);

    List<Submission> findByProblem(Problem problem);

    List<Submission> findByUserAndProblem(User user, Problem problem);
}
