CREATE TABLE question_set
(
    question_set_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    team_id         INT                NOT NULL,
    title           VARCHAR(255)       NOT NULL,
    CONSTRAINT fk_question_set_team FOREIGN KEY (team_id) REFERENCES team (team_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);