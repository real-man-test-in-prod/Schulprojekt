package com.rtfm.hammer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index()
    {
        return "Home";
    }

    @GetMapping("/progress")
    public String progress(@RequestParam(required = false) String code, Model model) {
        model.addAttribute("saveCode", code);
        return "progress";
    }
}
