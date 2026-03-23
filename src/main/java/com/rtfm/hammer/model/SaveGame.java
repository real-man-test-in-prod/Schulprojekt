package com.rtfm.hammer.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class SaveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String code;

    @Column(name = "saveGameState")
    private String saveGameState;

    @Column(name = "date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @Column(name = "saveGameName")
    private String saveGameName;

    @Column(name = "score")
    private int score;

    @Column(name = "correctAnswers")
    private int correctAnswers;

    @Column(name = "answeredQuestions")
    private int answeredQuestions;

    @Column(name = "gameFinished")
    private boolean gameFinished;

}
