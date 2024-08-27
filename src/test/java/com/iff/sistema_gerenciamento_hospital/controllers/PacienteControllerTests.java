package com.iff.sistema_gerenciamento_hospital.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.PacienteController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarPacientes() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId("1");
        paciente.setNome("Maria");

        given(pacienteService.listarPacientes()).willReturn(Arrays.asList(paciente));

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(paciente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(paciente.getNome()))
                .andDo(print());

    }

    @Test
    void deveCadastrarPaciente() throws Exception {
        Paciente paciente = new Paciente();

        given(pacienteService.inserirPaciente(paciente)).willReturn(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(paciente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(paciente.getNome()))
                .andDo(print());
    }

    @Test
    void naoDeveCadastrarPacienteComCpfExistente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setCpf("123456789");

        given(pacienteService.inserirPaciente(paciente)).willThrow(new BadRequestException("CPF do cliente ja cadastrado!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("CPF do cliente ja cadastrado!"))
                .andDo(print());
    }

    @Test
    void naoDeveCadastrarPacienteSemEndereco() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setEndereco(null);

        given(pacienteService.inserirPaciente(paciente)).willThrow(new BadRequestException("O endereço é necessário para o cadastro do cliente"));

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("O endereço é necessário para o cadastro do cliente"))
                .andDo(print());
    }

    @Test
    void deveRetornarPacientePorId() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId("1");

        given(pacienteService.buscarPacientePorId("1")).willReturn(Optional.of(paciente));

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andDo(print());
    }

    @Test
    void deveRetornarErroAoBuscarPacienteNaoExistente() throws Exception {
        given(pacienteService.buscarPacientePorId("1")).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}
