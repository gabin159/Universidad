package com.usta.Universidad.controllers;

import com.usta.Universidad.entities.ConvocatoriaEntity;
import com.usta.Universidad.entities.StartupEntity;
import com.usta.Universidad.models.services.ConvocatoriaService;
import com.usta.Universidad.models.services.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class monitorController {

    @Autowired
    private StartupService startupService;

    @GetMapping("/monitor")
    public String verStartups(Model model) {
        List<StartupEntity> startups = startupService.findAll();
        model.addAttribute("title", "Listado de Startups");
        model.addAttribute("startups", startups);
        return "Monitor/ListarStartups"; // Asegúrate que este es el HTML correcto
    }

}