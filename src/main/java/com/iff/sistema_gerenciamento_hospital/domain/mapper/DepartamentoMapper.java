package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.DepartamentoController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartamentoMapper extends RepresentationModelAssemblerSupport<Departamento, DepartamentoDto> {

    public DepartamentoMapper() {
        super(DepartamentoController.class, DepartamentoDto.class);
    }

    @Override
    public DepartamentoDto toModel(Departamento entity) {
        DepartamentoDto departamentoDto = instantiateModel(entity);

        departamentoDto.add(linkTo(
                methodOn(DepartamentoController.class)
                        .getDepartamento(entity.getId()))
                .withSelfRel());

        departamentoDto.setId(entity.getId());
        departamentoDto.setLocalizacao(entity.getLocalizacao());
        return departamentoDto;
    }

    @Override
    public CollectionModel<DepartamentoDto> toCollectionModel(Iterable<? extends Departamento> entities) {
        CollectionModel<DepartamentoDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(DepartamentoController.class).listarDepartamentos()).withSelfRel());

        return models;
    }
}
