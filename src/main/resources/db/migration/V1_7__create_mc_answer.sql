CREATE TABLE mc_answer
(
    answer_id    INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id  INT     NOT NULL,
    option_text  TEXT    NOT NULL,
    is_correct   BOOLEAN NOT NULL DEFAULT FALSE,
    option_order INT     NOT NULL,
    CONSTRAINT uq_mc_answer_question_order UNIQUE (question_id, option_order),
    CONSTRAINT fk_mc_answer_question FOREIGN KEY (question_id) REFERENCES question (question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);