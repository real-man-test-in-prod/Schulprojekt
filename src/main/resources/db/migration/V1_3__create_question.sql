CREATE TABLE question
(
    question_id     INT AUTO_INCREMENT     NOT NULL PRIMARY KEY,
    question_set_id INT                    NOT NULL,
    question_type   ENUM ('MC','TF','GAP') NOT NULL,
    start_text      TEXT,
    image_url       TEXT,
    end_text        TEXT,
    allows_multiple BOOLEAN                NOT NULL DEFAULT FALSE,
    points          INT                    NOT NULL DEFAULT 1,
    CONSTRAINT fk_question_question_set FOREIGN KEY (question_set_id) REFERENCES question_set (question_set_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);