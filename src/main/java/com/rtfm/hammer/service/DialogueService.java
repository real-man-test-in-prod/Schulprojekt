package com.rtfm.hammer.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class DialogueService {

    public String loadDialogue(String filename) {
        try{
            return Files.readString(Paths.get("src/main/resources/static/dialogue/" + filename + ".json"));
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
