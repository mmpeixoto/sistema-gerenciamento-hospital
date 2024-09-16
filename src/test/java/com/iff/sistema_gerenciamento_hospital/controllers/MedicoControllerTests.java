package com.iff.sistema_gerenciamento_hospital.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.MedicoController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
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
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicoController.class)
public class MedicoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicoService medicoService;

    @InjectMocks
    private MedicoController medicoController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarMedicosMedicos() throws Exception {
        Medico medico = new Medico();
        medico.setId("1");
        medico.setNome("Dr. João");

        given(medicoService.listarMedicos()).willReturn(Arrays.asList(medico));

        mockMvc.perform(MockMvcRequestBuilders.get("/medicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(medico.getId()))
                .andExpect(jsonPath("$[0].nome").value(medico.getNome()));
    }

    @Test
    void deveAcharMedicoPorId() throws Exception {
        Medico medico = new Medico();
        medico.setId("1");
        medico.setNome("Dr. João");

        given(medicoService.buscarMedicoPorId("1")).willReturn(Optional.of(medico));

        mockMvc.perform(MockMvcRequestBuilders.get("/medicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medico.getId()))
                .andExpect(jsonPath("$.nome").value(medico.getNome()));
    }

    @Test
    void deveRetornarNotFoundSeMedicoNaoExistir() throws Exception {
        given(medicoService.buscarMedicoPorId("1")).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/medicos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveBuscarMedicoPorLicenca() throws Exception {
        Medico medico = new Medico();
        medico.setId("1");
        medico.setNome("Dr. João");
        medico.setLicenca("123456");

        given(medicoService.buscarMedicoPorLicenca("123456")).willReturn(Optional.of(medico));

        mockMvc.perform(MockMvcRequestBuilders.get("/medicos/licenca/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medico.getId()))
                .andExpect(jsonPath("$.nome").value(medico.getNome()));
    }

    @Test
    void deveCadastrarMedico() throws Exception {
        Medico medico = new Medico();
        medico.setId("1");
        medico.setNome("Dr. João");

        given(medicoService.inserirMedico(medico)).willReturn(medico);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medico.getId()))
                .andExpect(jsonPath("$.nome").value(medico.getNome()));
    }

    @Test
    void deveAtualizarMedico() throws Exception {
        Medico medico = new Medico();
        medico.setId("1");
        medico.setNome("Dr. João");

        given(medicoService.atualizarMedico("1", medico)).willReturn(medico);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medico.getId()))
                .andExpect(jsonPath("$.nome").value(medico.getNome()));
    }

    @Test
    void deveDeletarMedico() throws Exception {
        doNothing().when(medicoService).deletarMedico("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarErroAoTentarDeletarMedicoInexistente() throws Exception {
        doThrow(new NotFoundException("Medico não encontrado")).when(medicoService).deletarMedico("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicos/1"))
                .andExpect(status().isNotFound());
    }
}
