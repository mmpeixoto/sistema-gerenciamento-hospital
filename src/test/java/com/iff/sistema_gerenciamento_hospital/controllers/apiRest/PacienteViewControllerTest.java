package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.controllers.view.PacienteViewController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
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

    @Test
    void postCriacao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var paciente = criarPaciente();
        when(pacienteService.listarPacientes()).thenReturn(List.of(paciente));

        mockMvc.perform(post("/pacienteView/form")
                        .flashAttr("paciente", paciente))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/pacienteView"));
    }

    @Test
    void deletarPaciente_deveRedirecionarParaListagem() throws Exception {
        var paciente = criarPaciente();
        when(pacienteService.buscarPacientePorId(paciente.getId())).thenReturn(paciente);

        mockMvc.perform(get("/pacienteView/"+paciente.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/pacienteView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComPaciente() throws Exception {
        var paciente = criarPaciente();
        when(pacienteService.buscarPacientePorId(paciente.getId())).thenReturn(paciente);

        mockMvc.perform(get("/pacienteView/editar/"+paciente.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().attribute("paciente", paciente));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var paciente = criarPaciente();

        mockMvc.perform(post("/pacienteView/editar/"+paciente.getId(), paciente))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var paciente = criarPaciente();
        when(pacienteService.listarPacientes()).thenReturn(List.of(paciente));

        mockMvc.perform(post("/pacienteView/editar/"+paciente.getId())
                        .flashAttr("paciente", paciente))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/pacienteView"));
    }

    @Test
    void postCriacao_semCpf_deveVoltarParaFormularioComErro() throws Exception {
        var paciente = criarPaciente();
        paciente.setCpf(null);

        mockMvc.perform(post("/pacienteView/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("paciente", "cpf"));
    }

    @Test
    void postCriacao_semNome_deveVoltarParaFormularioComErro() throws Exception {
        var paciente = criarPaciente();
        paciente.setCpf(null);

        mockMvc.perform(post("/pacienteView/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("paciente", "nome"));
    }

    @Test
    void postCriacao_semDataNascimento_deveVoltarParaFormularioComErro() throws Exception {
        var paciente = criarPaciente();
        paciente.setCpf(null);

        mockMvc.perform(post("/pacienteView/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientes-form"))
                .andExpect(model().attributeExists("paciente"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("paciente", "dataNascimento"));
    }


    private Endereco criarEndereco() {
        var endereco = new Endereco();
        endereco.setId("1");
        endereco.setCep("77666512");
        endereco.setLogradouro("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade Numero 1");
        endereco.setEstado("RJ");
        endereco.setComplemento("Casa 1");
        return endereco;
    }

    private Paciente criarPaciente() {
        var paciente = new Paciente();
        paciente.setId("1");
        paciente.setNome("Paciente 1");
        paciente.setCpf("12345678901");
        paciente.setTelefone("22999999999");
        paciente.setDataNascimento(new Date());
        paciente.setPeso(70.5f);
        paciente.setAltura(1.75f);
        paciente.setTipoSanguineo("O+");
        paciente.setEndereco(criarEndereco());
        return paciente;
    }

}