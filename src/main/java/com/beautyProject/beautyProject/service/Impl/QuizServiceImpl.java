package com.beautyProject.beautyProject.service.Impl;

import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.model.dto.*;
import com.beautyProject.beautyProject.model.entity.*;
import com.beautyProject.beautyProject.repository.*;
import com.beautyProject.beautyProject.service.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizResultRepository quizResultRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserRepository userRepository;
    private final SkinTypeRepository skinTypeRepository;
    private final ObjectMapper objectMapper;

    public QuizServiceImpl(QuizResultRepository quizResultRepository,
                           QuizAnswerRepository quizAnswerRepository,
                           UserRepository userRepository,
                           SkinTypeRepository skinTypeRepository,
                           ObjectMapper objectMapper) {
        this.quizResultRepository = quizResultRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userRepository = userRepository;
        this.skinTypeRepository = skinTypeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<QuizQuestionDTO> getAllQuestions() {
        // Static quiz sualları
        return Arrays.asList(
                new QuizQuestionDTO(1, "Gün ərzində üzünüzdə yağlılıq hiss edirsinizmi?",
                        Arrays.asList("A) Bəli, tez-tez yağlanır.", "B) Yalnız alın, burun və çənədə hiss olunur.",
                                "C) Heç vaxt yağlılıq hiss etmirəm.", "D) Dərim ümumiyyətlə mat və qurudur.")),

                new QuizQuestionDTO(2, "Dərinizin görünüşünü necə təsvir edərdiniz?",
                        Arrays.asList("A) Parıltılı və məsamələr genişdir.", "B) Burun və alın parıltılı, yanaqlar normaldır.",
                                "C) Həm parıltı, həm də quruluq yoxdur.", "D) Mat və bəzən çatlamış görünür.")),

                new QuizQuestionDTO(3, "Dəriniz soyuq havalarda necə reaksiya verir?",
                        Arrays.asList("A) Daha da quruyur, gərginlik olur.", "B) Heç bir dəyişiklik olmur.",
                                "C) Yanaqlar quruyur, amma burun və alın normal qalır.", "D) Parıltı artır, yağlılıq çoxalır.")),

                new QuizQuestionDTO(4, "Məsamələrin görünüşü necədir?",
                        Arrays.asList("A) Geniş və çox görünür.", "B) Yalnız T bölgəsində genişdir.",
                                "C) Çox az görünür.", "D) Heç görünmür, dərim incədir.")),

                new QuizQuestionDTO(5, "Dərinin parıltısı ilə bağlı müşahidəniz necədir?",
                        Arrays.asList("A) Bütün üzüm parıldayır.", "B) T bölgəm parıldayır.",
                                "C) Üzüm nə yağlı, nə də quru görünür.", "D) Mat və bəzən pullanır.")),

                new QuizQuestionDTO(6, "Dərinin rahatlığı barədə nə deyə bilərsiniz?",
                        Arrays.asList("A) Gərginlik və quruluq hiss edirəm.", "B) Rahatdır, narahatlıq olmur.",
                                "C) T bölgəsi yağlı, yanaqlar normaldır.", "D) Dərim yağlı hiss olunur.")),

                new QuizQuestionDTO(7, "Dəriyə krem çəkmədiyiniz günlər necə hiss edirsiniz?",
                        Arrays.asList("A) Dərim dərhal quruyur.", "B) Fərq hiss etmirəm.",
                                "C) T bölgəsi normal qalır, yanaqlar quruyur.", "D) Dərim yenə də yağlı qalır.")),

                new QuizQuestionDTO(8, "Günün sonunda dərinizin vəziyyəti necə olur?",
                        Arrays.asList("A) Çox yağlı olur.", "B) T bölgəsi parıldayır, yanaqlar normaldır.",
                                "C) Balanslı qalır.", "D) Quruluq artır."))
        );
    }

    @Override
    @Transactional
    public QuizResultDTO submitQuiz(QuizSubmissionDTO submission) {
        User user = userRepository.findById(submission.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + submission.getUserId()));

        // Balları hesablayırıq
        Map<String, Integer> scores = calculateScores(submission.getAnswers());

        // Ən yüksək bal alan dəri tipini müəyyənləşdiririk
        String determinedSkinTypeName = determineSkinType(scores);

        SkinType determinedSkinType = skinTypeRepository.findByName(determinedSkinTypeName);
        if (determinedSkinType == null) {
            throw new ResourceNotFoundException("Skin type not found: " + determinedSkinTypeName);
        }

        // QuizResult yaradırıq
        QuizResult quizResult = new QuizResult();
        quizResult.setUser(user);
        quizResult.setDeterminedSkinType(determinedSkinType);

        try {
            quizResult.setScores(objectMapper.writeValueAsString(scores));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing scores", e);
        }

        QuizResult savedResult = quizResultRepository.save(quizResult);

        // QuizAnswer-ları yaradırıq
        List<QuizAnswer> answers = submission.getAnswers().entrySet().stream()
                .map(entry -> {
                    QuizAnswer answer = new QuizAnswer();
                    answer.setQuizResult(savedResult);
                    answer.setQuestionNumber(entry.getKey());
                    answer.setSelectedOption(entry.getValue());
                    return answer;
                })
                .collect(Collectors.toList());

        quizAnswerRepository.saveAll(answers);
        savedResult.setAnswers(answers);

        // İstifadəçinin dəri tipini yeniləyirik
        user.setSkinType(determinedSkinType);
        userRepository.save(user);

        return convertToDTO(savedResult, scores);
    }

    private Map<String, Integer> calculateScores(Map<Integer, String> answers) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("oily", 0);
        scores.put("dry", 0);
        scores.put("normal", 0);
        scores.put("combination", 0);

        // Hər sual üçün bal hesablama
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            int questionNum = entry.getKey();
            String answer = entry.getValue();

            addScoresForQuestion(scores, questionNum, answer);
        }

        return scores;
    }

    private void addScoresForQuestion(Map<String, Integer> scores, int questionNum, String answer) {
        // Quiz suallarında verilmiş bal sisteminə uyğun olaraq
        // Hər sual üçün ballar: [oily, combination, normal, dry]
        int[][][] scoreMatrix = {
                // Sual 1 balları: A, B, C, D
                {{3, 2, 0, 0}, {2, 3, 1, 0}, {0, 1, 3, 1}, {0, 0, 1, 3}},
                // Sual 2 balları
                {{3, 1, 0, 0}, {2, 3, 1, 0}, {0, 1, 3, 1}, {0, 0, 1, 3}},
                // Sual 3 balları
                {{0, 1, 1, 3}, {1, 1, 3, 1}, {1, 3, 1, 1}, {3, 2, 0, 0}},
                // Sual 4 balları
                {{3, 2, 0, 0}, {2, 3, 1, 0}, {0, 1, 3, 2}, {0, 0, 1, 3}},
                // Sual 5 balları
                {{3, 1, 0, 0}, {2, 3, 1, 0}, {0, 1, 3, 1}, {0, 0, 1, 3}},
                // Sual 6 balları
                {{0, 0, 1, 3}, {1, 1, 3, 1}, {2, 3, 1, 0}, {3, 1, 0, 0}},
                // Sual 7 balları
                {{0, 0, 1, 3}, {1, 1, 3, 1}, {1, 3, 1, 2}, {3, 1, 0, 0}},
                // Sual 8 balları
                {{3, 1, 0, 0}, {2, 3, 1, 0}, {0, 1, 3, 1}, {0, 0, 1, 3}}
        };

        int answerIndex = answer.charAt(0) - 'A'; // A=0, B=1, C=2, D=3
        int questionIndex = questionNum - 1;

        if (questionIndex < scoreMatrix.length && answerIndex < 4) {
            scores.put("oily", scores.get("oily") + scoreMatrix[questionIndex][answerIndex][0]);
            scores.put("combination", scores.get("combination") + scoreMatrix[questionIndex][answerIndex][1]);
            scores.put("normal", scores.get("normal") + scoreMatrix[questionIndex][answerIndex][2]);
            scores.put("dry", scores.get("dry") + scoreMatrix[questionIndex][answerIndex][3]);
        }
    }

    private String determineSkinType(Map<String, Integer> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("normal");
    }

    @Override
    public QuizResultDTO getUserLatestResult(Long userId) {
        QuizResult result = quizResultRepository.findTopByUserIdOrderByCompletedAtDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz results found for user ID: " + userId));

        try {
            Map<String, Integer> scores = objectMapper.readValue(result.getScores(), Map.class);
            return convertToDTO(result, scores);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing scores", e);
        }
    }

    @Override
    public List<QuizResultDTO> getUserQuizHistory(Long userId) {
        List<QuizResult> results = quizResultRepository.findByUserIdOrderByCompletedAtDesc(userId);

        return results.stream()
                .map(result -> {
                    try {
                        Map<String, Integer> scores = objectMapper.readValue(result.getScores(), Map.class);
                        return convertToDTO(result, scores);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing scores", e);
                    }
                })
                .collect(Collectors.toList());
    }

    private QuizResultDTO convertToDTO(QuizResult result, Map<String, Integer> scores) {
        SkinTypeDTO skinTypeDTO = new SkinTypeDTO();
        skinTypeDTO.setId(result.getDeterminedSkinType().getId());
        skinTypeDTO.setName(result.getDeterminedSkinType().getName());
        skinTypeDTO.setDescription(result.getDeterminedSkinType().getDescription());
        skinTypeDTO.setRecommendationText(result.getDeterminedSkinType().getRecommendationText());

        QuizResultDTO dto = new QuizResultDTO();
        dto.setId(result.getId());
        dto.setUserId(result.getUser().getId());
        dto.setDeterminedSkinType(skinTypeDTO);
        dto.setScores(scores);
        dto.setCompletedAt(result.getCompletedAt());
        dto.setRecommendation(result.getDeterminedSkinType().getRecommendationText());

        return dto;
    }
}