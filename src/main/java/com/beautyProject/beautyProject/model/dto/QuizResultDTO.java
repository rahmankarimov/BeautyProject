package com.beautyProject.beautyProject.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class QuizResultDTO {
    private Long id;
    private Long userId;
    private SkinTypeDTO determinedSkinType;
    private Map<String, Integer> scores;
    private LocalDateTime completedAt;
    private String recommendation;
}