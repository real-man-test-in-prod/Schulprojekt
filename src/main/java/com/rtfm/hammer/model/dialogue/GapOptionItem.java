package com.rtfm.hammer.model.dialogue;

import java.util.List;

public record GapOptionItem(
        String gapId,
        String textBefore,
        String textAfter,
        List<String> choices,
        String correct
) {}
