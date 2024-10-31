package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TriagemViewController.class)
public class TriagemViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TriagemService triagemService;

    @MockBean
    private PacienteService pacienteService;

    @MockBean
    private EnfermeiroService enfermeiroService;

    @Test
    public void deveRetornarPaginaListagemTriagens() throws Exception {
        when(triagemService.listarTriagens()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/triagemView"))
                .andExpect(status().isOk())
                .andExpect(view().name("triagens-lista"))
                .andExpect(model().attributeExists("triagens"))
                .andExpect(model().attribute("triagens", Matchers.empty()));
    }

    @Test
    public void postCriacao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var triagem = new Triagem();

        mockMvc.perform(post("/triagemView/form", triagem))
                .andExpect(status().isOk())
                .andExpect(view().name("triagens-form"))
                .andExpect(model().attributeExists("triagem"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postCriacao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var triagem = criarTriagem();
        when(triagemService.listarTriagens()).thenReturn(List.of(triagem));

        mockMvc.perform(post("/triagemView/form")
                        .flashAttr("triagem", triagem))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/triagemView"));
    }

    @Test
    void deletarTriagem_deveRedirecionarParaListagem() throws Exception {
        var triagem = criarTriagem();
        when(triagemService.getTriagem(triagem.getId())).thenReturn(triagem);

        mockMvc.perform(get("/triagemView/"+triagem.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/triagemView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComTriagem() throws Exception {
        var triagem = criarTriagem();
        when(triagemService.getTriagem(triagem.getId())).thenReturn(triagem);

        mockMvc.perform(get("/triagemView/editar/"+triagem.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("triagens-form"))
                .andExpect(model().attributeExists("triagem"))
                .andExpect(model().attribute("triagem", triagem));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var triagem = criarTriagem();

        mockMvc.perform(post("/triagemView/editar/"+triagem.getId(), triagem))
                .andExpect(status().isOk())
                .andExpect(view().name("triagens-form"))
                .andExpect(model().attributeExists("triagem"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var triagem = criarTriagem();
        when(triagemService.listarTriagens()).thenReturn(List.of(triagem));

        mockMvc.perform(post("/triagemView/editar/"+triagem.getId())
                        .flashAttr("triagem", triagem))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/triagemView"));
    }

    private Triagem criarTriagem() {
        var triagem = new Triagem();
        triagem.setId("1");
        triagem.setData(new Date());
        return triagem;
    }
}