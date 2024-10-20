package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departamentoView")
public class DepartamentoViewController {

    private final DepartamentoService departamentoService;
    private final MedicoService medicoService;

    @GetMapping
    public String listarDepartamentos(Model model) {
        var departamentos = departamentoService.listarDepartamentos();
        model.addAttribute("departamentos", departamentos);
        return "departamentos-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("medicos", medicoService.listarMedicos());
        return "departamentos-form";
    }

    @PostMapping("/form")
    public String cadastrarDepartamento(@ModelAttribute Departamento departamento) {
        departamentoService.inserirDepartamento(departamento);
        return "redirect:/departamentoView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarDepartamento(@PathVariable String id) {
        departamentoService.deletarDepartamento(id);
        return "redirect:/departamentoView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Departamento departamento = departamentoService.getDepartamento(id);
        model.addAttribute("medicos", medicoService.listarMedicos());
        model.addAttribute("departamento", departamento);
        return "departamentos-form";
    }

    @PostMapping("/editar/{id}")
    public String editarDepartamento(@PathVariable String id, @ModelAttribute Departamento departamento) {
        departamentoService.editarDepartamento(id, departamento);
        return "redirect:/departamentoView";
    }
}