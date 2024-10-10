package com.example.leo.controller;

import com.example.leo.model.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class ViewController {

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "login";
    }

    @GetMapping("/home")
    public String showHomeForm(Model model, @RequestParam String userName) {
        model.addAttribute("userName", userName);
        return "user-home";
    }
}
