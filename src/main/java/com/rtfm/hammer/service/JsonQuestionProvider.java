package com.rtfm.hammer.service;

import com.rtfm.hammer.model.dialogue.QuestionBank;
import com.rtfm.hammer.model.dialogue.QuestionItem;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class JsonQuestionProvider implements QuestionProvider {

    @Override
    public List<QuestionItem> getQuestionsForRoom(String roomCode, List<String> questionRefs) {
        try {
            var resource = new ClassPathResource("static/dialogue/Questions_DB.json");
            var mapper = new ObjectMapper();
            QuestionBank bank = mapper.readValue(resource.getInputStream(), QuestionBank.class);

            // Create map for faster lookup
            Map<String, QuestionItem> questionMap = bank.questions().stream()
                    .collect(Collectors.toMap(QuestionItem::id, q -> q));

            // Return questions in the order of questionRefs, filtering out any refs not found
            return questionRefs.stream()
                    .map(questionMap::get)
                    .filter(q -> q != null)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Could not load questions from Questions_DB.json", e);
        }
    }
}
