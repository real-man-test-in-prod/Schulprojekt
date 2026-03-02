CREATE TABLE question_theme
(
    question_id INT NOT NULL,
    theme_id    INT NOT NULL,
    PRIMARY KEY (question_id, theme_id),
    CONSTRAINT fk_qt_question FOREIGN KEY (question_id) REFERENCES question (question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_qt_theme FOREIGN KEY (theme_id) REFERENCES theme (theme_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);