package com.beautyProject.beautyProject.repository;

import com.beautyProject.beautyProject.model.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
    List<QuizAnswer> findByQuizResultId(Long quizResultId);
}