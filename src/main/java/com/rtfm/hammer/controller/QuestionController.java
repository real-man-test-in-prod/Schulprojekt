package com.rtfm.hammer.controller;


import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("{questionID}")
    public Question getQuestion(@PathVariable String questionID) {
        if (questionID.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be empty!");
        }
        return questionService.getQuestion(Integer.parseInt(questionID));
    }
}
