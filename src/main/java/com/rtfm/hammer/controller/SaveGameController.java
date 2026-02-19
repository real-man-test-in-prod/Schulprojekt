package com.rtfm.hammer.controller;

import com.rtfm.hammer.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class SaveGameController {

    private final StorageService storageService;

    public SaveGameController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/saves")
    public String index()
    {
        return "Saves";
    }

    @PostMapping("saves")
    public ResponseEntity<String> store(@RequestBody String saveGame) throws IOException {
        storageService.save("test.txt", saveGame);
        return ResponseEntity.status(HttpStatus.OK).body("Saved Successfully");
    }

}
