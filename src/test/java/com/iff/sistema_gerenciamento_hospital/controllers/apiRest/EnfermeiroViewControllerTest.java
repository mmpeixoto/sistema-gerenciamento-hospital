package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.controllers.view.EnfermeiroViewController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(EnfermeiroViewController.class)
public class EnfermeiroViewControllerTest {

    @MockBean
    private EnfermeiroService enfermeiroService;

    @MockBean
    private DepartamentoService departamentoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarPaginaListagemEnfermeiros() throws Exception {
        when(enfermeiroService.listarEnfermeiros()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/enfermeiroView"))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-lista"))
                .andExpect(model().attributeExists("enfermeiros"))
                .andExpect(model().attribute("enfermeiros", Matchers.empty()));
    }

    @Test
    public void postCriacao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var enfermeiro = new Enfermeiro();

        mockMvc.perform(post("/enfermeiroView/form", enfermeiro))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postCriacao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var enfermeiro = criarEnfermeiro();
        when(enfermeiroService.listarEnfermeiros()).thenReturn(List.of(enfermeiro));

        mockMvc.perform(post("/enfermeiroView/form")
                        .flashAttr("enfermeiro", enfermeiro))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/enfermeiroView"));
    }

    @Test
    void deletarEnfermeiro_deveRedirecionarParaListagem() throws Exception {
        var enfermeiro = criarEnfermeiro();
        when(enfermeiroService.listarEnfermeiros()).thenReturn(List.of(enfermeiro));

        mockMvc.perform(get("/enfermeiroView/"+enfermeiro.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/enfermeiroView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComEnfermeiro() throws Exception {
        var enfermeiro = criarEnfermeiro();
        when(enfermeiroService.buscarEnfermeiroPorId(enfermeiro.getId())).thenReturn(enfermeiro);

        mockMvc.perform(get("/enfermeiroView/editar/"+enfermeiro.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().attribute("enfermeiro", enfermeiro));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var enfermeiro = criarEnfermeiro();

        mockMvc.perform(post("/enfermeiroView/editar/"+enfermeiro.getId(), enfermeiro))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var enfermeiro = criarEnfermeiro();
        when(enfermeiroService.listarEnfermeiros()).thenReturn(List.of(enfermeiro));

        mockMvc.perform(post("/enfermeiroView/editar/"+enfermeiro.getId())
                        .flashAttr("enfermeiro", enfermeiro))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/enfermeiroView"));
    }

    @Test
    void postCriacao_semCpf_deveRetornarErro() throws Exception {
        var enfermeiro = criarEnfermeiro();
        enfermeiro.setDepartamento(null);

        mockMvc.perform(post("/enfermeiroView/form", enfermeiro))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("enfermeiro", "cpf"));
    }

    @Test
    void postCriacao_semNome_deveRetornarErro() throws Exception {
        var enfermeiro = criarEnfermeiro();
        enfermeiro.setNome(null);

        mockMvc.perform(post("/enfermeiroView/form", enfermeiro))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("enfermeiro", "nome"));
    }

    @Test
    void postCriacao_semDataNascimento_deveRetornarErro() throws Exception {
        var enfermeiro = criarEnfermeiro();
        enfermeiro.setDataNascimento(null);

        mockMvc.perform(post("/enfermeiroView/form", enfermeiro))
                .andExpect(status().isOk())
                .andExpect(view().name("enfermeiros-form"))
                .andExpect(model().attributeExists("enfermeiro"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("enfermeiro", "dataNascimento"));
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

    private Departamento criarDepartamento(){
        var departamento = new Departamento();
        departamento.setId("123");
        departamento.setLocalizacao("local");
        return departamento;
    }

    private Enfermeiro criarEnfermeiro() {
        var enfermeiro = new Enfermeiro();
        enfermeiro.setId("1");
        enfermeiro.setNome("Enfermeiro 1");
        enfermeiro.setCpf("12345678901");
        enfermeiro.setTelefone("22999999999");
        enfermeiro.setDataNascimento(new Date());
        enfermeiro.setDepartamento(criarDepartamento());
        enfermeiro.setEndereco(criarEndereco());
        return enfermeiro;
    }

}
