package pl.chessWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/account")
    public String showHome() {
        return "home";
    }

    @GetMapping("/managers")
    public String showLeaders() {
        return "managers";
    }

    @GetMapping("/admin")
    public String showSystems() {
        return "admin";
    }
}










