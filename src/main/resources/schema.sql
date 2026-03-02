BEGIN TRANSACTION;

DROP TABLE IF EXISTS QUESTION_THEME;
DROP TABLE IF EXISTS GAP_OPTION;
DROP TABLE IF EXISTS GAP_FIELD;
DROP TABLE IF EXISTS MC_ANSWER;
DROP TABLE IF EXISTS QUESTION;
DROP TABLE IF EXISTS QUESTION_SET;
DROP TABLE IF EXISTS THEME;
DROP TABLE IF EXISTS TEAM;

CREATE TABLE TEAM (
    team_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE THEME (
    theme_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE QUESTION_SET (
    question_set_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    team_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT fk_question_set_team FOREIGN KEY (team_id) REFERENCES TEAM(team_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE QUESTION (
    question_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    question_set_id INT NOT NULL,
    question_type ENUM('MC','TF','GAP') NOT NULL,
    start_text TEXT,
    image_url TEXT,
    end_text TEXT,
    allows_multiple BOOLEAN NOT NULL DEFAULT FALSE,
    points INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_question_question_set FOREIGN KEY (question_set_id) REFERENCES QUESTION_SET(question_set_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE QUESTION_THEME (
    question_id INT NOT NULL,
    theme_id INT NOT NULL,
    PRIMARY KEY (question_id, theme_id),
    CONSTRAINT fk_qt_question FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_qt_theme FOREIGN KEY (theme_id) REFERENCES THEME(theme_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE MC_ANSWER (
    answer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    option_text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    option_order INT NOT NULL,
    CONSTRAINT uq_mc_answer_question_order UNIQUE (question_id, option_order),
    CONSTRAINT fk_mc_answer_question FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE GAP_FIELD (
    gap_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    gap_index INT NOT NULL,
    text_before TEXT,
    text_after TEXT,
    CONSTRAINT uq_gap_field_question_index UNIQUE (question_id, gap_index),
    CONSTRAINT fk_gap_field_question FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE GAP_OPTION (
    gap_option_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    gap_id INT NOT NULL,
    option_text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    option_order INT NOT NULL,
    CONSTRAINT uq_gap_option_gap_order UNIQUE (gap_id, option_order),
    CONSTRAINT fk_gap_option_gap FOREIGN KEY (gap_id) REFERENCES GAP_FIELD(gap_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

COMMIT;