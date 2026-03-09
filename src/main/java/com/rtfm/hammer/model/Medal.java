package com.rtfm.hammer.model;

public enum Medal {
    NONE,
    BROONZE,
    SILVER,
    GOLD;
    
    public static Medal MedalForScore(int score) {
        if (score >= 90) return GOLD;
        if (score >= 75) return SILVER; 
        if (score >= 50) return BROONZE;
        return NONE;
    }
}
