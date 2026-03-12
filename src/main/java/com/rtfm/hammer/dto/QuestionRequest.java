package com.rtfm.hammer.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class QuestionRequest {
    private Map<String, String> answers;
}
