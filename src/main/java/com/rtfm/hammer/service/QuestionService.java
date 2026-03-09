package com.rtfm.hammer.service;

import com.rtfm.hammer.model.GapOption;
import com.rtfm.hammer.model.Question;
import com.rtfm.hammer.repository.GapOptionRepository;
import com.rtfm.hammer.repository.McAnswerRepository;
import com.rtfm.hammer.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final GapOptionRepository gapOptionRepository;
    private final McAnswerRepository mcAnswerRepository;

    public QuestionService(QuestionRepository questionRepository, GapOptionRepository gapOptionRepository, McAnswerRepository mcAnswerRepository) {
        this.questionRepository = questionRepository;
        this.gapOptionRepository = gapOptionRepository;
        this.mcAnswerRepository = mcAnswerRepository;
    }

    public Question getQuestionByCode(Integer code) {
        return questionRepository.findByQuestionId(code);
    }

    public boolean validateGAP(String gapID, String answer) {
        List<GapOption> gapField = gapOptionRepository.findByGapIdAndIsCorrectTrue(Integer.parseInt(gapID));
        return gapField.stream()
                .anyMatch(option -> option.getOptionText().equals(answer));
    }

    public boolean validateMC(Map<String, String> mcAnswer) {
        return false;
    }
}
