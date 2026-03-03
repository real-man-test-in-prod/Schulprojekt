CREATE TABLE save_game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code varchar(255),
    save_game_state longtext,
    date DATE,
    save_game_name varchar(255)
);