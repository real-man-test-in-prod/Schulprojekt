package com.rtfm.hammer.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchoolController {

    @GetMapping("/school")
    public String home() {
        return "school";
    }
}
