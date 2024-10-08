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
    private final MedicoMapper medicoMapper;
    private final EnfermeiroMapper enfermeiroMapper;

    public DepartamentoMapper(MedicoMapper medicoMapper, EnfermeiroMapper enfermeiroMapper) {
        super(DepartamentoController.class, DepartamentoDto.class);
        this.medicoMapper = medicoMapper;
        this.enfermeiroMapper = enfermeiroMapper;
    }

    @Override
    public DepartamentoDto toModel(Departamento entity) {
        DepartamentoDto departamentoDto = instantiateModel(entity);

        departamentoDto.add(linkTo(
                methodOn(DepartamentoController.class)
                        .getDepartamento(entity.getId()))
                .withSelfRel());

        departamentoDto.setId(entity.getId());
        if (entity.getEnfermeiros() != null) {
            departamentoDto.setEnfermeiros(enfermeiroMapper.toCollectionModel(entity.getEnfermeiros()));

        }
        departamentoDto.setLocalizacao(entity.getLocalizacao());
        if(entity.getMedicos() != null){
            departamentoDto.setMedicos(medicoMapper.toCollectionModel(entity.getMedicos()));
        }
        return departamentoDto;
    }

    @Override
    public CollectionModel<DepartamentoDto> toCollectionModel(Iterable<? extends Departamento> entities) {
        CollectionModel<DepartamentoDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(DepartamentoController.class).listarDepartamentos()).withSelfRel());

        return models;
    }
}
