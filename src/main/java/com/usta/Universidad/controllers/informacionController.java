package com.usta.Universidad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class informacionController {
    @GetMapping("/nosotros")
    public String informacion(Model model) {
        model.addAttribute("title", "information");
        return "informacion/informacion";
    }

}
