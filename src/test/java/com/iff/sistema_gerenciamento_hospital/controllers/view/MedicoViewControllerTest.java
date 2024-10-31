package com.iff.sistema_gerenciamento_hospital.controllers.view;

import com.iff.sistema_gerenciamento_hospital.controllers.view.MedicoViewController;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@WebMvcTest(MedicoViewController.class)
public class MedicoViewControllerTest {

    @MockBean
    private MedicoService medicoService;

    @MockBean
    private DepartamentoService departamentoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarPaginaDeListagemDeMedicos() throws Exception{
        when(medicoService.listarMedicos()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/medicoView"))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-lista"))
                .andExpect(model().attributeExists("medicos"))
                .andExpect(model().attribute("medicos", Matchers.empty()));
    }

    @Test
    public void postCriação_aoEnviarErro_deveVoltarParaFormulario() throws Exception {
        var medico = new Medico();

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void postCriacao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception {
        var medico = criarMedico();
        when(medicoService.listarMedicos()).thenReturn(List.of(medico));

        mockMvc.perform(post("/medicoView/form")
                        .flashAttr("medico", medico))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/medicoView"));
    }

    @Test
    void deletarMedico_deveRedirecionarParaListagem() throws Exception{
        var medico = criarMedico();
        when(medicoService.buscarMedicoPorId(medico.getId())).thenReturn(medico);

        mockMvc.perform(get("/medicoView/"+medico.getId()+"/deletar"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/medicoView"));
    }

    @Test
    void mostrarFormularioEdicao_deveRetornarFormularioComMedicos() throws Exception{
        var medico = criarMedico();
        when(medicoService.buscarMedicoPorId(medico.getId())).thenReturn(medico);

        mockMvc.perform(get("/medicoView/editar/"+medico.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().attribute("medico", medico));
    }

    @Test
    void postEdicao_aoEnviarErro_deveVoltarParaFormulario() throws Exception{
        var medico = criarMedico();

        mockMvc.perform(post("/medicoView/editar/"+medico.getId(), medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors());
    }

    @Test
    void postEdicao_aoEnviarSucesso_deveRedirecionarParaListagem() throws Exception{
        var medico = criarMedico();
        when(medicoService.listarMedicos()).thenReturn(List.of(medico));

        mockMvc.perform(post("/medicoView/editar/"+medico.getId())
                        .flashAttr("medico", medico))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/medicoView"));
    }

    @Test
    void postCriacao_SemCpf_deveRetornarErro() throws Exception{
        var medico = criarMedico();
        medico.setCpf(null);

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("medico", "cpf"));
    }

    @Test
    void postCriacao_SemDataNascimento_deveRetornarErro() throws Exception{
        var medico = criarMedico();
        medico.setDataNascimento(null);

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("medico", "dataNascimento"));
    }

    @Test
    void postCriacao_SemNome_deveRetornarErro() throws Exception{
        var medico = criarMedico();
        medico.setNome(null);

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("medico", "nome"));
    }

    @Test
    void postCriacao_SemEspecialidade_deveRetornarErro() throws Exception{
        var medico = criarMedico();
        medico.setEspecialidade(null);

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("medico", "especialidade"));
    }

    @Test
    void postCriacao_SemLicenca_deveRetornarErro() throws Exception{
        var medico = criarMedico();
        medico.setLicenca(null);

        mockMvc.perform(post("/medicoView/form", medico))
                .andExpect(status().isOk())
                .andExpect(view().name("medicos-form"))
                .andExpect(model().attributeExists("medico"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("medico", "licenca"));
    }

    private Departamento criarDepartamento(){
        var departamento = new Departamento();
        departamento.setId("123");
        departamento.setLocalizacao("local");
        return departamento;
    }

    private Medico criarMedico(){
        var medico = new Medico();
        medico.setId("1");
        medico.setNome("Medico 1");
        medico.setCpf("12312323423");
        medico.setLicenca("123");
        medico.setDataNascimento(new Date());
        medico.setEspecialidade("especialidade1");
        medico.setDepartamento(criarDepartamento());
        return medico;
    }
}
