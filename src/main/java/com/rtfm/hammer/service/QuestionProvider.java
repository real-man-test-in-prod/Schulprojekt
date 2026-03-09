package com.rtfm.hammer.service;

import com.rtfm.hammer.model.dialogue.QuestionItem;
import java.util.List;

public interface QuestionProvider {
    List<QuestionItem> getQuestionsForRoom(String roomCode, List<String> questionRefs);
}
