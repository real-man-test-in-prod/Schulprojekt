CREATE TABLE gap_field
(
    gap_id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    gap_index   INT NOT NULL,
    text_before TEXT,
    text_after  TEXT,
    CONSTRAINT uq_gap_field_question_index UNIQUE (question_id, gap_index),
    CONSTRAINT fk_gap_field_question FOREIGN KEY (question_id) REFERENCES question (question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);