package com.rtfm.hammer.model.dialogue;

import java.util.List;

public record RoomDay(int dayIndex, String title, String setting, List<DialogueLine> dialogue, DailyTest dailyTest) {}