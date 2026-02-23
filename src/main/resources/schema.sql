CREATE TABLE IF NOT EXISTS TEAM (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS QUESTION_SET (
    question_set_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT,
    title VARCHAR(255),
    FOREIGN KEY (team_id) REFERENCES TEAM(team_id)
);

CREATE TABLE IF NOT EXISTS QUESTION (
    question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_set_id BIGINT,
    question_type VARCHAR(10) NOT NULL,
    start_text TEXT,
    image_url VARCHAR(500),
    end_text TEXT,
    FOREIGN KEY (question_set_id) REFERENCES QUESTION_SET(question_set_id)
);

CREATE TABLE IF NOT EXISTS MC_QUESTION (
    question_id BIGINT PRIMARY KEY,
    allows_multiple BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
);

CREATE TABLE IF NOT EXISTS MC_OPTION (
    option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT,
    option_text TEXT,
    points INT,
    is_correct BOOLEAN,
    option_order INT,
    FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
);

CREATE TABLE IF NOT EXISTS GAP (
    gap_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT,
    gap_index INT,
    prefix_text TEXT,
    sufix_text TEXT,
    FOREIGN KEY (question_id) REFERENCES QUESTION(question_id)
);

CREATE TABLE IF NOT EXISTS GAP_OPTION (
    gap_option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gap_id BIGINT,
    option_text TEXT,
    points INT,
    is_correct BOOLEAN,
    option_order INT,
    FOREIGN KEY (gap_id) REFERENCES GAP(gap_id)
);

CREATE TABLE IF NOT EXISTS THEME (
    theme_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
);

CREATE TABLE IF NOT EXISTS QUESTION_THEME (
    question_id BIGINT,
    theme_id BIGINT,
    PRIMARY KEY (question_id, theme_id),
    FOREIGN KEY (question_id) REFERENCES QUESTION(question_id),
    FOREIGN KEY (theme_id) REFERENCES THEME(theme_id)
);