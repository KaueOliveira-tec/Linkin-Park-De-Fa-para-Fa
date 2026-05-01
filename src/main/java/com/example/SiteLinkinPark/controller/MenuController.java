package com.example.SiteLinkinPark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String paginaPrincipal() {
        return "index";
    }

    @GetMapping("/integrantes_originais")
    public String integranteOriginal() {
        return "integrantes_originais";
    }

    @GetMapping("/integrantes_atuais")
    public String integranteAtual() {
        return "integrantes_atuais";
    }
}