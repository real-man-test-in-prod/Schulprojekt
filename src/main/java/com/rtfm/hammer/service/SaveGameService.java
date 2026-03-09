package com.rtfm.hammer.service;

import com.rtfm.hammer.model.Medal;
import com.rtfm.hammer.model.SaveGame;
import com.rtfm.hammer.repository.SaveGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@Transactional
public class SaveGameService {

    private final SaveGameRepository saveGameRepository;
    private final SaveCodeGenerator saveCodeGenerator;

    public SaveGameService(SaveGameRepository saveGameRepository, SaveCodeGenerator saveCodeGenerator) {
        this.saveGameRepository = saveGameRepository;
        this.saveCodeGenerator = saveCodeGenerator;
    }

    public String save(String playerName, String gameStateJson, LocalDate date, int score) {
        String code = saveCodeGenerator.generate();

        while (saveGameRepository.findByCode(code).isPresent()) {
            code = saveCodeGenerator.generate();
        }
        SaveGame saveGame = new SaveGame();
        saveGame.setCode(code);
        saveGame.setDate(date);
        saveGame.setSaveGameName(playerName);
        saveGame.setSaveGameState(gameStateJson);
        saveGame.setScore(score);
        saveGameRepository.save(saveGame);

        return code;
    }

    public SaveGame loadByCode(String code) {
        return saveGameRepository.findByCode(code.toUpperCase().trim()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String update(String saveCode, String gameStateJson, LocalDate date, int score) {
        SaveGame saveGame = loadByCode(saveCode);
        saveGame.setSaveGameState(gameStateJson);
        saveGame.setDate(date);
        saveGame.setScore(score);
        saveGameRepository.save(saveGame);
        return saveCode;
    }

    public Medal getMedalForSaveGame(SaveGame saveGame) {
        return Medal.MedalForScore(saveGame.getScore());
        
    }
}
