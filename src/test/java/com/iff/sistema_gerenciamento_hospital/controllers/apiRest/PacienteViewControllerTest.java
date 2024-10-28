package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.controllers.view.PacienteViewController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PacienteViewController.class)
public class PacienteViewControllerTest {

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarPaginaListagemPacientes() throws Exception {
        when(pacienteService.listarPacientes()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/pacienteView"))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-lista"))
                .andExpect(model().attributeExists("pacientes"))
                .andExpect(model().attribute("pacientes", Matchers.empty()));
    }

    @Test
    public void postCriacao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var paciente = new Paciente();

        mockMvc.perform(post("/pacienteView/form", paciente))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().hasErrors());
    }


}