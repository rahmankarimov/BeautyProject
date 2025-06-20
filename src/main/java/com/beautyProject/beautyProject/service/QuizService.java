package com.beautyProject.beautyProject.service;

import com.beautyProject.beautyProject.model.dto.QuizQuestionDTO;
import com.beautyProject.beautyProject.model.dto.QuizResultDTO;
import com.beautyProject.beautyProject.model.dto.QuizSubmissionDTO;

import java.util.List;

public interface QuizService {
    List<QuizQuestionDTO> getAllQuestions();
    QuizResultDTO submitQuiz(QuizSubmissionDTO submission);
    QuizResultDTO getUserLatestResult(Long userId);
    List<QuizResultDTO> getUserQuizHistory(Long userId);
}