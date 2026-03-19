package com.rtfm.hammer.service;

import com.rtfm.hammer.model.dialogue.QuestionBank;
import com.rtfm.hammer.model.dialogue.QuestionItem;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JsonQuestionProvider implements QuestionProvider {

    @Override
    public List<QuestionItem> getQuestionsForRoom(String roomCode, List<String> questionRefs) {
        try {
            // roomCode arrives as e.g. "room_db" or "room_ki" — strip the "room_" prefix
            // to get the bare code ("db", "ki", etc.) used in the filenames.
            String code = roomCode.startsWith("room_") ? roomCode.substring(5) : roomCode;

            // The "db" room uses a legacy filename with uppercase letters (Questions_DB.json).
            // All other rooms follow the lowercase convention: questions_<code>.json.
            String filename = code.equals("db")
                    ? "Questions_DB.json"
                    : "questions_" + code + ".json";

            var resource = new ClassPathResource("static/dialogue/" + filename);
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
            throw new RuntimeException("Could not load questions for room: " + roomCode, e);
        }
    }
}
