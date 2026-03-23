package com.rtfm.hammer.model.dialogue;

import java.util.List;
import java.util.Map;

public record RoomDialogue(
        String roomCode,
        String title,
        String npcName,
        String description,
        Map<String, String> characters,
        List<RoomDay> days,
        DialogueLine completionMessage
) {}