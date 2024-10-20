package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/triagemView")
public class TriagemViewController {

    private final TriagemService triagemService;
    private final PacienteService pacienteService;
    private final EnfermeiroService enfermeiroService;

    @GetMapping
    public String listarTriagens(Model model) {
        var triagens = triagemService.listarTriagens();
        model.addAttribute("triagens", triagens);
        return "triagens-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("triagem", new Triagem());
        model.addAttribute("pacientes", pacienteService.listarPacientes());
        model.addAttribute("enfermeiros", enfermeiroService.listarEnfermeiros());
        return "triagens-form";
    }

    @PostMapping("/form")
    public String cadastrarTriagem(Triagem triagem) {
        triagemService.inserirTriagem(triagem);
        return "redirect:/triagemView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarTriagem(@PathVariable String id) {
        triagemService.deleteTriagem(id);
        return "redirect:/triagemView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Triagem triagem = triagemService.getTriagem(id);
        model.addAttribute("pacientes", pacienteService.listarPacientes());
        model.addAttribute("enfermeiros", enfermeiroService.listarEnfermeiros());
        model.addAttribute("triagem", triagem);
        return "triagens-form";
    }

    @PostMapping("/editar/{id}")
    public String editarTriagem(@PathVariable String id, @ModelAttribute Triagem triagem) {
        triagemService.atualizarTriagem(id, triagem);
        return "redirect:/triagemView";
    }
}