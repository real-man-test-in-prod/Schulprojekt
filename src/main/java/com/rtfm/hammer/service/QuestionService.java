package com.rtfm.hammer.service;


import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getQuestion(int questionId) {
        return questionRepository.findByQuestionId(questionId);
    }
}
