package com.iff.sistema_gerenciamento_hospital.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.ConsultaController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaController.class)
public class ConsultaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaService consultaService;

    @InjectMocks
    private ConsultaController consultaController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarConsultas() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setId("1");

        given(consultaService.listarConsultas()).willReturn(Arrays.asList(consulta));

        mockMvc.perform(MockMvcRequestBuilders.get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(consulta.getId()));
    }

    @Test
    void deveListarConsultasPorPaciente() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setId("1");

        given(consultaService.listarConsultasPaciente("1")).willReturn(Arrays.asList(consulta));

        mockMvc.perform(MockMvcRequestBuilders.get("/consultas/paciente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(consulta.getId()));
    }

    @Test
    void deveListarConsultasPorMedico() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setId("1");

        given(consultaService.listarConsultasMedico("1")).willReturn(Arrays.asList(consulta));

        mockMvc.perform(MockMvcRequestBuilders.get("/consultas/medico/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(consulta.getId()));
    }

    @Test
    void deveCadastrarConsulta() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setId("1");

        given(consultaService.inserirConsulta(consulta)).willReturn(consulta);

        mockMvc.perform(MockMvcRequestBuilders.post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consulta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consulta.getId()));
    }

    @Test
    void deveAtualizarConsulta() throws Exception {
        Consulta consulta = new Consulta();
        consulta.setId("1");

        given(consultaService.atualizarConsulta("1", consulta)).willReturn(consulta);

        mockMvc.perform(MockMvcRequestBuilders.put("/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consulta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consulta.getId()));
    }

    @Test
    void deveDeletarConsulta() throws Exception {
        doNothing().when(consultaService).deletarConsulta("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/consultas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarErroAoTentarDeletarConsultaInexistente() throws Exception {
        doThrow(new NotFoundException("Consulta não encontrada")).when(consultaService).deletarConsulta("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/consultas/1"))
                .andExpect(status().isNotFound());
    }
}
