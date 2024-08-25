package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> consultas() {
        return consultaService.listarConsultas();
    }

    @GetMapping("paciente/{pacienteId}")
    public List<Consulta> consultasPorPaciente(@PathVariable String pacienteId) {
        return consultaService.listarConsultasPaciente(pacienteId);
    }

    @GetMapping("medico/{medicoId}")
    public List<Consulta> consultasPorMedico(@PathVariable String medicoId) {
        return consultaService.listarConsultasMedico(medicoId);
    }

    @PostMapping
    public Consulta cadastrar(@RequestBody Consulta consulta) {
        return consultaService.inserirConsulta(consulta);
    }
}
