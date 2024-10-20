package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enfermeiroView")
public class EnfermeiroViewController {

    private final EnfermeiroService enfermeiroService;
    private final DepartamentoService departamentoService;

    @GetMapping
    public String listarEnfermeiros(Model model) {
        var enfermeiros = enfermeiroService.listarEnfermeiros();
        model.addAttribute("enfermeiros", enfermeiros);
        return "enfermeiros-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("enfermeiro", new Enfermeiro());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "enfermeiros-form";
    }

    @PostMapping("/form")
    public String cadastrarEnfermeiro(Enfermeiro enfermeiro) {
        enfermeiroService.inserirEnfermeiro(enfermeiro);
        return "redirect:/enfermeiroView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarEnfermeiro(@PathVariable String id) {
        enfermeiroService.deletarEnfermeiro(id);
        return "redirect:/enfermeiroView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Enfermeiro enfermeiro = enfermeiroService.buscarEnfermeiroPorId(id);
        model.addAttribute("enfermeiro", new Enfermeiro());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        model.addAttribute("enfermeiro", enfermeiro);
        return "enfermeiros-form";
    }

    @PostMapping("/editar/{id}")
    public String editarEnfermeiro(@PathVariable String id, @ModelAttribute Enfermeiro enfermeiro) {
        enfermeiroService.atualizarEnfermeiro(id, enfermeiro);
        return "redirect:/enfermeiroView";
    }
}