package com.rtfm.hammer.controller;

import com.rtfm.hammer.service.DialogueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class RoomController {
    private DialogueService dialogueService;

    @GetMapping("/rooms/{character}/{id}")
    public String getDialogue(@PathVariable String character, @PathVariable Integer id, Model model) {
        model.addAttribute("dialogue", dialogueService.loadDialogue(character));
        model.addAttribute("id", id);
        return "rooms/room";
    }

    @Deprecated
    @GetMapping("/rooms/{roomId}")
        public String room(@PathVariable String roomId, Model model) {
        String roomText;
        String roomDescription = "";

        if (roomId.equals("room_6")) {
            roomText = "Willkommen im Netzwerk-Raum Raum 6";
            roomDescription="Hier gehen wir die Netzwerk themen durch";
        } else {
            roomText = "Unbekannter Raum ergebnis: "+ roomId;

        }
        model.addAttribute("roomText", roomText);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomDescription", roomDescription);

            // Default dialog content for room 1 (can be overridden per-room)
            model.addAttribute("introText", "You have entered Room 1. Do you wish to continue?");
// Leave imageUrl null when no image is required
            model.addAttribute("imageUrl", null);
            model.addAttribute("outroText", "Choose Continue to proceed or Cancel to stay.");
            model.addAttribute("continueUrl", null);

// Example dialog messages
            model.addAttribute("dialogMessages", List.of(
                    Map.of("sender", "npc", "text", "A hooded figure eyes you from the corner."),
                    Map.of("sender", "player", "text", "Who are you?"),
                    Map.of("sender", "npc", "text", "I am the keeper of this chamber. Choose wisely.")));

// Example choices: one continues to a URL, one replies with NPC text
            model.addAttribute("choices", List.of(
                    Map.of("label", "Ask about the chamber", "reply", "The chamber holds many secrets. Tread carefully."),
                    Map.of("label", "Leave the room", "continueUrl", "/rooms/leave")));


        return "rooms/room";
        }
}
