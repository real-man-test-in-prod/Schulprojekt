package com.rtfm.hammer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/rooms/room_1")
    public String room() {
        return "rooms/room_1";
    }
}
