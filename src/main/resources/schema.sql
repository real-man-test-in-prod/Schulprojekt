BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS TEAM (
    team_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS THEME (
    theme_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS QUESTION_SET (
    question_set_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    team_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT fk_question_set_team FOREIGN KEY (team_id) REFERENCES TEAM(team_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS QUESTION (
    question_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    question_set_id INT NOT NULL,
    question_type ENUM('MC','TF','GAP') NOT NULL,
    start_text TEXT,
    image_url TEXT,
    end_text TEXT,
    allows_multiple BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_question_question_set FOREIGN KEY (question_set_id) REFERENCES QUESTION_SET(question_set_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS QUESTION_THEME (
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

CREATE TABLE IF NOT EXISTS MC_ANSWER (
    answer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    option_text TEXT NOT NULL,
    points INT DEFAULT 0,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    option_order INT NOT NULL,
    CONSTRAINT uq_mc_answer_question_order UNIQUE (question_id, option_order),
    CONSTRAINT fk_mc_answer_question FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS GAP_FIELD (
    gap_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id INT NOT NULL,
    gap_index INT NOT NULL,
    input_type ENUM('FREE_TEXT','CHOICE') NOT NULL DEFAULT 'FREE_TEXT',
    correct_text TEXT,
    case_sensitive BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_gap_field_question_index UNIQUE (question_id, gap_index),
    CONSTRAINT fk_gap_field_question FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS GAP_OPTION (
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