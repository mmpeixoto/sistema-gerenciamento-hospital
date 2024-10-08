package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.EnfermeiroController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnfermeiroMapper extends RepresentationModelAssemblerSupport<Enfermeiro, EnfermeiroDto> {
    public EnfermeiroMapper() {
        super(EnfermeiroController.class, EnfermeiroDto.class);
    }

    @Override
    public EnfermeiroDto toModel(Enfermeiro entity) {
        EnfermeiroDto enfermeiroDto = instantiateModel(entity);

        enfermeiroDto.add(linkTo(
                methodOn(EnfermeiroController.class)
                        .buscarEnfermeiroPorId(entity.getId()))
                .withSelfRel());

        enfermeiroDto.setId(entity.getId());
        enfermeiroDto.setNome(entity.getNome());
        enfermeiroDto.setCpf(entity.getCpf());
        enfermeiroDto.setTelefone(entity.getTelefone());
        enfermeiroDto.setDataNascimento(entity.getDataNascimento());
        enfermeiroDto.setEndereco(entity.getEndereco());
        enfermeiroDto.setDepartamentoId(entity.getDepartamento().getId());
        return enfermeiroDto;
    }

    @Override
    public CollectionModel<EnfermeiroDto> toCollectionModel(Iterable<? extends Enfermeiro> entities) {
        CollectionModel<EnfermeiroDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(EnfermeiroController.class).listarEnfermeiros()).withSelfRel());

        return models;
    }
}