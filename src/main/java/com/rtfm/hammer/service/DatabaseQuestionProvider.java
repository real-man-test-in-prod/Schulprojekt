package com.rtfm.hammer.service;

import com.rtfm.hammer.model.dialogue.QuestionItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// KEIN @Primary – wird erst aktiv wenn @Primary von JsonQuestionProvider entfernt wird
public class DatabaseQuestionProvider implements QuestionProvider {

    @Override
    public List<QuestionItem> getQuestionsForRoom(String roomCode, List<String> questionRefs) {
        // TODO: Implementierung wenn H2 befüllt ist
        throw new UnsupportedOperationException("DB not yet populated");
    }
}
