package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/consultaView")
public class ConsultaViewController {

    private final ConsultaService consultaService;
    private final TriagemService triagemService;
    private final MedicoService medicoService;

    @GetMapping
    public String listarConsultas(Model model) {
        var consultas = consultaService.listarConsultas("", "");
        model.addAttribute("consultas", consultas);
        return "consultas-lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("triagens", triagemService.listarTriagens());
        model.addAttribute("medicos", medicoService.listarMedicos());
        return "consultas-form";
    }

    @PostMapping("/form")
    public String cadastrarConsulta(@Valid @ModelAttribute Consulta consulta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("consulta", consulta);
            return "consultas-form";
        }
        consultaService.inserirConsulta(consulta);
        return "redirect:/consultaView";
    }

    @GetMapping("/{id}/deletar")
    public String deletarConsulta(@PathVariable String id) {
        consultaService.deletarConsulta(id);
        return "redirect:/consultaView";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Consulta consulta = consultaService.getConsulta(id);
        model.addAttribute("triagens", triagemService.listarTriagens());
        model.addAttribute("medicos", medicoService.listarMedicos());
        model.addAttribute("consulta", consulta);
        return "consultas-form";
    }

    @PostMapping("/editar/{id}")
    public String editarConsulta(@PathVariable String id, @Valid @ModelAttribute Consulta consulta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("consulta", consulta);
            return "consultas-form";
        }
        consultaService.atualizarConsulta(id, consulta);
        return "redirect:/consultaView";
    }
}