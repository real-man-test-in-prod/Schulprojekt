package com.rtfm.hammer.service;

import com.rtfm.hammer.model.GapOption;
import com.rtfm.hammer.model.McAnswer;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.repository.GapOptionRepository;
import com.rtfm.hammer.repository.McAnswerRepository;
import com.rtfm.hammer.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private GapOptionRepository gapOptionRepository;

    @Mock
    private McAnswerRepository mcAnswerRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void shouldReturnQuestionWhenValidCode() {
        Question question = new Question();
        question.setQuestionId(1);
        when(questionRepository.findByQuestionId(1)).thenReturn(question);

        Question result = questionService.getQuestionByCode(1);

        assertEquals(question, result);
        verify(questionRepository).findByQuestionId(1);
    }

    @Test
    void shouldValidateGapCorrectlyWhenAnswerMatches() {
        GapOption correctOption = new GapOption();
        correctOption.setOptionText("Correct Answer");
        correctOption.setIsCorrect(true);
        when(gapOptionRepository.findByGapIdAndIsCorrectTrue(1)).thenReturn(List.of(correctOption));

        boolean result = questionService.validateGAP("1", "Correct Answer");

        assertTrue(result);
    }

    @Test
    void shouldValidateGapIncorrectlyWhenAnswerDoesNotMatch() {
        GapOption correctOption = new GapOption();
        correctOption.setOptionText("Correct Answer");
        correctOption.setIsCorrect(true);
        when(gapOptionRepository.findByGapIdAndIsCorrectTrue(1)).thenReturn(List.of(correctOption));

        boolean result = questionService.validateGAP("1", "Wrong Answer");

        assertFalse(result);
    }

    @Test
    void shouldCalculateMcScoreCorrectly() {
        Question question = new Question();
        question.setPoints(10);

        McAnswer answer1 = new McAnswer();
        answer1.setAnswerId(1);
        answer1.setIsCorrect(true);

        McAnswer answer2 = new McAnswer();
        answer2.setAnswerId(2);
        answer2.setIsCorrect(true);

        when(mcAnswerRepository.findByQuestionIdAndIsCorrectTrue(1)).thenReturn(List.of(answer1, answer2));

        Map<String, String> userAnswers = Map.of("1", "1", "2", "1");

        int score = questionService.validateMC(question, "1", userAnswers);

        // 2 correct out of 2, points 10, so 10
        assertEquals(10, score);
    }

    @Test
    void shouldCalculateMcScoreWhenPartialCorrect() {
        Question question = new Question();
        question.setPoints(10);

        McAnswer answer1 = new McAnswer();
        answer1.setAnswerId(1);
        answer1.setIsCorrect(true);

        McAnswer answer2 = new McAnswer();
        answer2.setAnswerId(2);
        answer2.setIsCorrect(true);

        when(mcAnswerRepository.findByQuestionIdAndIsCorrectTrue(1)).thenReturn(List.of(answer1, answer2));

        Map<String, String> userAnswers = Map.of("1", "1", "2", "0");

        int score = questionService.validateMC(question, "1", userAnswers);

        // 1 correct out of 2, points 10, so 5
        assertEquals(5, score);
    }
}