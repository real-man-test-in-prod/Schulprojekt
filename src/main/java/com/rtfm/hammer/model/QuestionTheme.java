package com.rtfm.hammer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "question_theme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTheme {

    @EmbeddedId
    private QuestionThemeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Theme theme;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionThemeId implements java.io.Serializable {
        @Column(name = "question_id")
        private Integer questionId;

        @Column(name = "theme_id")
        private Integer themeId;
    }
}
