package com.beautyProject.beautyProject.repository;

import com.beautyProject.beautyProject.model.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    Optional<QuizResult> findTopByUserIdOrderByCompletedAtDesc(Long userId);
    List<QuizResult> findByUserIdOrderByCompletedAtDesc(Long userId);
}