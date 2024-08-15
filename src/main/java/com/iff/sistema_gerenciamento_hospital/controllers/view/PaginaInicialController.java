package com.iff.sistema_gerenciamento_hospital.controllers.view;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class PaginaInicialController {
    @GetMapping
    public ResponseEntity<String> paginaInicial() {
        return ResponseEntity.ok("Pagina inicial em construção");
    }
}
