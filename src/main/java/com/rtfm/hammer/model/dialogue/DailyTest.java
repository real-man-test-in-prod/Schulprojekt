package com.rtfm.hammer.model.dialogue;

import java.util.List;

public record DailyTest(DialogueLine intro, List<String> questionRefs, DialogueLine outro) {}