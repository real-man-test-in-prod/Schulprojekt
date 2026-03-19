package com.rtfm.hammer.controller;


import com.rtfm.hammer.dto.QuestionRequest;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
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
            boolean allCorrect = true;
            for (Map.Entry<String, String> entry : body.getAnswers().entrySet()) {
                if (!questionService.validateGAP(entry.getKey(), entry.getValue())) {
                    allCorrect = false;
                    break;
                }
            }
            isCorrect = allCorrect;
        }
        return isCorrect ? question.getPoints() : 0;
    }
}
