package com.rtfm.hammer.controller;

import com.rtfm.hammer.dto.QuestionRequest;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @Test
    void shouldReturnQuestionWhenValidId() {
        Question question = new Question();
        question.setQuestionId(1);
        when(questionService.getQuestionByCode(1)).thenReturn(question);

        Question result = questionController.getQuestion("1");

        assertEquals(question, result);
    }

    @Test
    void shouldThrowBadRequestWhenInvalidId() {
        assertThrows(ResponseStatusException.class, () -> questionController.getQuestion("abc"));
    }

    @Test
    void shouldReturnZeroForIncorrectGapQuestion() {
        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionType("GAP");
        question.setPoints(10);
        when(questionService.getQuestionByCode(1)).thenReturn(question);
        when(questionService.validateGAP("gap1", "wrong")).thenReturn(false);

        QuestionRequest request = new QuestionRequest();
        try {
            java.lang.reflect.Field field = QuestionRequest.class.getDeclaredField("answers");
            field.setAccessible(true);
            field.set(request, Map.of("gap1", "wrong"));
        } catch (Exception e) {
            fail("Could not set answers");
        }

        int result = questionController.validateQuestion("1", request);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnFullPointsForCorrectGapQuestion() {
        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionType("GAP");
        question.setPoints(10);
        when(questionService.getQuestionByCode(1)).thenReturn(question);
        when(questionService.validateGAP("gap1", "correct")).thenReturn(true);

        QuestionRequest request = new QuestionRequest();
        try {
            java.lang.reflect.Field field = QuestionRequest.class.getDeclaredField("answers");
            field.setAccessible(true);
            field.set(request, Map.of("gap1", "correct"));
        } catch (Exception e) {
            fail("Could not set answers");
        }

        int result = questionController.validateQuestion("1", request);

        assertEquals(10, result);
    }
}