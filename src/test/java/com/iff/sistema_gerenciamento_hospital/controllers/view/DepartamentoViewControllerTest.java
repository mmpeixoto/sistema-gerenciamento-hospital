package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.controllers.view.DepartamentoViewController;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
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

@WebMvcTest(DepartamentoViewController.class)
public class DepartamentoViewControllerTest {
    @MockBean
    private DepartamentoService departamentoService;

    @MockBean
    private MedicoService medicoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarPaginaDeListagemDeDepartamentos() throws Exception{
        when(departamentoService.listarDepartamentos()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/departamentoView"))
                .andExpect(status().isOk())
                .andExpect(view().name("departamentos-lista"))
                .andExpect(model().attributeExists("departamentos"))
                .andExpect(model().attribute("departamentos", Matchers.empty()));
    }

    @Test
    void formularioDeCriacaoDeveConterOsMedicos() throws Exception{
        var medico = criarMedico();
        when(medicoService.listarMedicos()).thenReturn(List.of(medico));

        mockMvc.perform(get("/departamentoView/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("departamentos-form"))
                .andExpect(model().attributeExists("medicos"))
                .andExpect(model().attributeExists("departamento"))
                .andExpect(model().attribute("medicos", Matchers.contains(medico)));
    }

    @Test
    void postCriacao_aoEnviarErro_deveVoltarParaFormulario() throws Exception{
        var departamento = new Departamento();

        mockMvc.perform(post("/departamentoView/form", departamento))
                .andExpect(status().isOk())
                .andExpect(view().name("departamentos-form"))
                .andExpect(model().attributeExists("departamento"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postCriacao_aoEnviarSucesso_deveVoltarParaLista() throws Exception{
        var departamento = criarDepartamento();
        when(departamentoService.listarDepartamentos()).thenReturn(List.of(departamento));

        mockMvc.perform(post("/departamentoView/form")
                        .flashAttr("departamento", departamento))
                .andExpect(status().isFound()) //status para redirecionamento
                .andExpect(model().hasNoErrors());
    }

    @Test
    void deletarDepartamento_deveRedirecionarParaLista() throws Exception{
        var departamento = criarDepartamento();
        when(departamentoService.getDepartamento(departamento.getId())).thenReturn(departamento);

        mockMvc.perform(get("/departamentoView/"+departamento.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/departamentoView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComDepartamento() throws Exception{
        var departamento = criarDepartamento();
        when(departamentoService.getDepartamento(departamento.getId())).thenReturn(departamento);

        mockMvc.perform(get("/departamentoView/editar/"+departamento.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("departamentos-form"))
                .andExpect(model().attributeExists("departamento"))
                .andExpect(model().attribute("departamento", departamento));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception{
        var departamento = criarDepartamento();

        mockMvc.perform(post("/departamentoView/editar/"+departamento.getId(), departamento))
                .andExpect(status().isOk())
                .andExpect(view().name("departamentos-form"))
                .andExpect(model().attributeExists("departamento"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveVoltarParaLista() throws Exception{
        var departamento = criarDepartamento();
        when(departamentoService.listarDepartamentos()).thenReturn(List.of(departamento));

        mockMvc.perform(post("/departamentoView/editar/"+departamento.getId())
                        .flashAttr("departamento", departamento))
                .andExpect(status().isFound()) //status para redirecionamento
                .andExpect(model().hasNoErrors());
    }

    private Medico criarMedico(){
        var medico = new Medico();
        medico.setId("1");
        medico.setNome("Medico 1");
        medico.setLicenca("123");
        return medico;
    }

    private Departamento criarDepartamento(){
        var departamento = new Departamento();
        departamento.setId("123");
        departamento.setLocalizacao("local");
        return departamento;
    }
}
