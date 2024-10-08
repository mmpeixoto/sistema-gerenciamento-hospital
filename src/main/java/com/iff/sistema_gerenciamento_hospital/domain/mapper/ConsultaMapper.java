package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.ConsultaController;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.MedicoController;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.TriagemController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.ConsultaDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.MedicoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConsultaMapper extends RepresentationModelAssemblerSupport<Consulta, ConsultaDto> {
    public ConsultaMapper() {
        super(ConsultaController.class, ConsultaDto.class);
    }

    @Override
    public ConsultaDto toModel(Consulta entity)
    {
        ConsultaDto consultaDto = instantiateModel(entity);

        consultaDto.add(linkTo(
                methodOn(ConsultaController.class)
                        .getConsulta(entity.getId()))
                .withSelfRel());

        consultaDto.setId(entity.getId());
        consultaDto.setDataConsulta(entity.getDataConsulta());
        consultaDto.setDiagnostico(entity.getDiagnostico());
        consultaDto.setTratamento(entity.getTratamento());
        consultaDto.setMedico(toMedico(entity.getMedico()));
        consultaDto.setTriagem(toTriagemDto(entity.getTriagem()));
        return consultaDto;
    }

    @Override
    public CollectionModel<ConsultaDto> toCollectionModel(Iterable<? extends Consulta> entities)
    {
        CollectionModel<ConsultaDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(ConsultaController.class).listarConsultas("", "")).withSelfRel());

        return models;
    }

    private TriagemDto toTriagemDto(Triagem entity) {
        var triagemDto = new TriagemDto();
        triagemDto.setId(entity.getId());
        triagemDto.add(linkTo(
                methodOn(TriagemController.class)
                        .getTriagem(entity.getId()))
                .withSelfRel());
        return triagemDto;
    }

    private MedicoDto toMedico(Medico entity) {
        var medicoDto = new MedicoDto();
        medicoDto.setId(entity.getId());
        medicoDto.setNome(entity.getNome());
        medicoDto.setLicenca(entity.getLicenca());
        medicoDto.setCpf(entity.getCpf());
        medicoDto.setTelefone(entity.getTelefone());
        medicoDto.setEspecialidade(entity.getEspecialidade());
        medicoDto.setEndereco(entity.getEndereco());
        medicoDto.setDataNascimento(entity.getDataNascimento());
        medicoDto.add(linkTo(
                methodOn(MedicoController.class)
                        .buscarMedicoPorId(entity.getId()))
                .withSelfRel());
        return medicoDto;
    }
}
