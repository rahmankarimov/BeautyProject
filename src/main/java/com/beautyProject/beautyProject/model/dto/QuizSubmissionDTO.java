package com.beautyProject.beautyProject.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class QuizSubmissionDTO {
    private Long userId;
    private Map<Integer, String> answers; // questionNumber -> selectedOption
}