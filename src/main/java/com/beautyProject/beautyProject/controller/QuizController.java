package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.model.dto.QuizQuestionDTO;
import com.beautyProject.beautyProject.model.dto.QuizResultDTO;
import com.beautyProject.beautyProject.model.dto.QuizSubmissionDTO;
import com.beautyProject.beautyProject.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuizQuestionDTO>> getAllQuestions() {
        logger.info("REST request to get all quiz questions");
        List<QuizQuestionDTO> questions = quizService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDTO> submitQuiz(@RequestBody QuizSubmissionDTO submission) {
        logger.info("REST request to submit quiz for user ID: {}", submission.getUserId());
        QuizResultDTO result = quizService.submitQuiz(submission);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/result/user/{userId}")
    public ResponseEntity<QuizResultDTO> getUserLatestResult(@PathVariable Long userId) {
        logger.info("REST request to get latest quiz result for user ID: {}", userId);
        QuizResultDTO result = quizService.getUserLatestResult(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/user/{userId}")
    public ResponseEntity<List<QuizResultDTO>> getUserQuizHistory(@PathVariable Long userId) {
        logger.info("REST request to get quiz history for user ID: {}", userId);
        List<QuizResultDTO> history = quizService.getUserQuizHistory(userId);
        return ResponseEntity.ok(history);
    }
}