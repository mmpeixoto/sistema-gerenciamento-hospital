package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "medicos-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("medico", new Medico());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "medicos-form";
    }

    @PostMapping("/form")
    public String cadastrarMedico(@ModelAttribute Medico medico) {
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
    public String editarMedico(@PathVariable String id, @ModelAttribute Medico medico) {
        medicoService.atualizarMedico(id, medico);
        return "redirect:/medicoView";
    }
}