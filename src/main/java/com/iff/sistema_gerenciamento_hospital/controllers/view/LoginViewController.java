package com.iff.sistema_gerenciamento_hospital.controllers.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginViewController {
    @GetMapping
    public String login(Model model) {
        return "login";
    }
}
