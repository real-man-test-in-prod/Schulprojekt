package com.rtfm.hammer.controller;

import com.rtfm.hammer.model.SaveGame;
import com.rtfm.hammer.service.SaveGameService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/saves")
public class SaveGameController {

    private final SaveGameService saveGameService;

    public SaveGameController(SaveGameService saveGameService) {
        this.saveGameService = saveGameService;
    }

    @PostMapping
    public Map<String, String> saveGame(@RequestBody SaveGame saveGame) {
        String code = saveGameService.save(saveGame.getSaveGameName(), saveGame.getSaveGameState(), saveGame.getDate(), saveGame.getScore());
        return Map.of("saveCode", code);
    }

    @GetMapping
    public String index() {
        return "Saves";
    }
   

   
   @GetMapping("/{code}")
    public Map<String, Object> load(@PathVariable String code) {
    SaveGame saveGame = saveGameService.loadByCode(code);

        return Map.of(
            "saveGame", saveGame,
            "medal", saveGameService.getMedalForSaveGame(saveGame)
    );
}


    @PutMapping("/{code}")
    public Map<String, String> update(@PathVariable String code, @RequestBody SaveGame saveGame) {
        saveGameService.update(code, saveGame.getSaveGameState(), saveGame.getDate(), saveGame.getScore());
        return Map.of("status", "updated");
    }

}
