package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ChefeDepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos/")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService service;

    @GetMapping
    public List<Departamento> listarDepartamentos() {
        return service.listarDepartamentos();
    }

    @PostMapping
    public Departamento inserirDepartamento(@Valid @RequestBody DepartamentoDto departamentoDto) {
        return service.inserirDepartamento(departamentoDto);
    }

    @PutMapping("/{departamentoId}/chefe")
    public Departamento editarChefeDepartamento(@PathParam("departamentoId") String departamentoId, @RequestBody @Valid ChefeDepartamentoDto chefeDepartamentoDto) {
        return service.editarChefeDepartamento(departamentoId, chefeDepartamentoDto);
    }
}
