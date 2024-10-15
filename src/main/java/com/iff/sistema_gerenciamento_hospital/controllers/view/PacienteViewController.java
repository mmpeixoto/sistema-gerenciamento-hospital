package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pacienteView")
public class PacienteViewController {

    private final PacienteService pacienteService;

    @GetMapping
    public String listarPacientes(Model model) {
        var pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pacientes-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes-form";
    }

    @PostMapping("/form")
    public String cadastrarPaciente(Paciente paciente) {
        pacienteService.inserirPaciente(paciente);
        return "redirect:/pacienteView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarPaciente(@PathVariable String id) {
        pacienteService.deletarPaciente(id);
        return "redirect:/pacienteView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Paciente paciente = pacienteService.buscarPacientePorId(id);
        model.addAttribute("paciente", paciente);
        return "paciente-form";  // Reaproveitando o formulário para edição
    }

    @PostMapping("/editar/{id}")
    public String editarPaciente(@PathVariable String id, @ModelAttribute Paciente paciente) {
        pacienteService.atualizarPaciente(id, paciente);
        return "redirect:/pacienteView";
    }
}
