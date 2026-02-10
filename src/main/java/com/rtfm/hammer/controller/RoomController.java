package com.rtfm.hammer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {
    @GetMapping("/rooms/{roomId}")
        public String room1(@PathVariable String roomId) {
            return "rooms/" + roomId;
        }
}
