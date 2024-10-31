package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ConsultaViewController.class)
public class ConsultaViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaService consultaService;

    @MockBean
    private TriagemService triagemService;

    @MockBean
    private MedicoService medicoService;

    @Test
    public void deveRetornarPaginaListagemConsultas() throws Exception {
        when(consultaService.listarConsultas("", "")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/consultaView"))
                .andExpect(status().isOk())
                .andExpect(view().name("consultas-lista"))
                .andExpect(model().attributeExists("consultas"))
                .andExpect(model().attribute("consultas", Matchers.empty()));
    }

    @Test
    public void postCriacao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var consulta = new Consulta();

        mockMvc.perform(post("/consultaView/form", consulta))
                .andExpect(status().isOk())
                .andExpect(view().name("consultas-form"))
                .andExpect(model().attributeExists("consulta"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postCriacao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var consulta = criarConsulta();
        when(consultaService.listarConsultas("", "")).thenReturn(List.of(consulta));

        mockMvc.perform(post("/consultaView/form")
                        .flashAttr("consulta", consulta))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/consultaView"));
    }

    @Test
    void deletarConsulta_deveRedirecionarParaListagem() throws Exception {
        var consulta = criarConsulta();
        when(consultaService.getConsulta(consulta.getId())).thenReturn(consulta);

        mockMvc.perform(get("/consultaView/"+consulta.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/consultaView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComConsulta() throws Exception {
        var consulta = criarConsulta();
        when(consultaService.getConsulta(consulta.getId())).thenReturn(consulta);

        mockMvc.perform(get("/consultaView/editar/"+consulta.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("consultas-form"))
                .andExpect(model().attributeExists("consulta"))
                .andExpect(model().attribute("consulta", consulta));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var consulta = criarConsulta();

        mockMvc.perform(post("/consultaView/editar/"+consulta.getId(), consulta))
                .andExpect(status().isOk())
                .andExpect(view().name("consultas-form"))
                .andExpect(model().attributeExists("consulta"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var consulta = criarConsulta();
        when(consultaService.listarConsultas("", "")).thenReturn(List.of(consulta));

        mockMvc.perform(post("/consultaView/editar/"+consulta.getId())
                        .flashAttr("consulta", consulta))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/consultaView"));
    }

    private Consulta criarConsulta() {
        var consulta = new Consulta();
        consulta.setId("1");
        consulta.setDiagnostico("diagnostico");
        consulta.setDataConsulta(Date.from(Instant.now()));
        // Adicione outros atributos necessários
        return consulta;
    }
}