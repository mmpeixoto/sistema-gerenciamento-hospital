package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
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
@RequestMapping("/medicoView")
public class MedicoViewController {

    private final MedicoService medicoService;
    private final DepartamentoService departamentoService;

    @GetMapping
    public String listarMedicos(Model model) {
        model.addAttribute("medicos", medicoService.listarMedicos());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "medicos-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("medico", new Medico());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "medicos-form";
    }

    @PostMapping("/form")
    public String cadastrarMedico(@Valid @ModelAttribute Medico medico, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medico", medico);
            model.addAttribute("departamentos", departamentoService.listarDepartamentos());
            return "medicos-form";
        }
        medicoService.inserirMedico(medico);
        return "redirect:/medicoView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarMedico(@PathVariable String id) {
        medicoService.deletarMedico(id);
        return "redirect:/medicoView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Medico medico = medicoService.buscarMedicoPorId(id);
        model.addAttribute("medico", medico);
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "medicos-form";
    }

    @PostMapping("/editar/{id}")
    public String editarMedico(@PathVariable String id, @Valid @ModelAttribute Medico medico, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medico", medico);
            model.addAttribute("departamentos", departamentoService.listarDepartamentos());
            return "medicos-form";
        }
        medicoService.atualizarMedico(id, medico);
        return "redirect:/medicoView";
    }
}