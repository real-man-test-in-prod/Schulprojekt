package com.rtfm.hammer.controller;


import com.rtfm.hammer.dto.QuestionRequest;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.service.QuestionValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/question/validate")
public class QuestionValidationController {

    private final QuestionValidationService questionService;

    public QuestionValidationController(QuestionValidationService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{questionID}")
    public Question getQuestion(@PathVariable String questionID) {
        int code;

        try {
            code = Integer.parseInt(questionID);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid question code");
        }

        return questionService.getQuestionByCode(code);
    }

    @PostMapping("/{questionID}")
    public int validateQuestion(@PathVariable String questionID, @RequestBody QuestionRequest body) {
        int id = Integer.parseInt(questionID);
        Question question = questionService.getQuestionByCode(id);
        boolean isCorrect = false;

        if (question.getQuestionType().equals("TF") || question.getQuestionType().equals("MC")) {
            int test = questionService.validateMC(question, questionID, body.getAnswers());
            System.out.println(test);
        } else {
            if (body.getAnswers().size() > 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zu viele Antworten");
            }
            Map.Entry<String, String> field = body.getAnswers().entrySet().iterator().next();
            isCorrect = questionService.validateGAP(field.getKey(), field.getValue());
        }
        return isCorrect ? question.getPoints() : 0;
    }
}
