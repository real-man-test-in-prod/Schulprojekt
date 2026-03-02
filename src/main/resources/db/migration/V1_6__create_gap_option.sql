CREATE TABLE gap_option
(
    gap_option_id INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    gap_id        INT     NOT NULL,
    option_text   TEXT    NOT NULL,
    is_correct    BOOLEAN NOT NULL DEFAULT FALSE,
    option_order  INT     NOT NULL,
    CONSTRAINT uq_gap_option_gap_order UNIQUE (gap_id, option_order),
    CONSTRAINT fk_gap_option_gap FOREIGN KEY (gap_id) REFERENCES gap_field (gap_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);