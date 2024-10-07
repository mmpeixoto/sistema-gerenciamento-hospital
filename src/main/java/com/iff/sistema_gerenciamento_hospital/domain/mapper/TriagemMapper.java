package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.TriagemController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TriagemMapper extends RepresentationModelAssemblerSupport<Triagem, TriagemDto> {

    private final PacienteMapper pacienteMapper;
    private final EnfermeiroMapper enfermeiroMapper;

    public TriagemMapper(PacienteMapper pacienteMapper, EnfermeiroMapper enfermeiroMapper) {
        super(TriagemController.class, TriagemDto.class);
        this.pacienteMapper = pacienteMapper;
        this.enfermeiroMapper = enfermeiroMapper;
    }

    @Override
    public TriagemDto toModel(Triagem entity) {
        TriagemDto triagemDto = instantiateModel(entity);

        triagemDto.add(linkTo(
                methodOn(TriagemController.class)
                        .getTriagem(entity.getId()))
                .withSelfRel());

        triagemDto.setId(entity.getId());
        triagemDto.setPaciente(pacienteMapper.toModel(entity.getPaciente()));
        triagemDto.setEnfermeiro(enfermeiroMapper.toModel(entity.getEnfermeiro()));
        triagemDto.setData(entity.getData());
        return triagemDto;
    }

    @Override
    public CollectionModel<TriagemDto> toCollectionModel(Iterable<? extends Triagem> entities) {
        CollectionModel<TriagemDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(TriagemController.class).listarTriagens()).withSelfRel());

        return models;
    }
}