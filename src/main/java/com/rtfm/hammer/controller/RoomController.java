package com.rtfm.hammer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {
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

        return "rooms/room";
        }
}
