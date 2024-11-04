package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String cadastrarDepartamento(@Valid @ModelAttribute Departamento departamento, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departamento", departamento);
            return "departamentos-form";
        }
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
    public String editarDepartamento(@PathVariable String id, @Valid @ModelAttribute Departamento departamento, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departamento", departamento);
            return "departamentos-form";
        }
        departamentoService.editarDepartamento(id, departamento);
        return "redirect:/departamentoView";
    }
}