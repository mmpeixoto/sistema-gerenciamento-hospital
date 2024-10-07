package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.DepartamentoController;
import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.MedicoController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.MedicoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MedicoMapper extends RepresentationModelAssemblerSupport<Medico, MedicoDto> {
    public MedicoMapper() {
        super(MedicoController.class, MedicoDto.class);
    }

    @Override
    public MedicoDto toModel(Medico entity) {
        MedicoDto medicoDto = instantiateModel(entity);

        medicoDto.add(linkTo(
                methodOn(MedicoController.class)
                        .buscarMedicoPorId(entity.getId()))
                .withSelfRel());

        medicoDto.setId(entity.getId());
        medicoDto.setNome(entity.getNome());
        medicoDto.setCpf(entity.getCpf());
        medicoDto.setTelefone(entity.getTelefone());
        medicoDto.setEndereco(entity.getEndereco());
        medicoDto.setDataNascimento(entity.getDataNascimento());
        medicoDto.setEspecialidade(entity.getEspecialidade());
        medicoDto.setLicenca(entity.getLicenca());
        return medicoDto;
    }

    @Override
    public CollectionModel<MedicoDto> toCollectionModel(Iterable<? extends Medico> entities) {
        CollectionModel<MedicoDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(MedicoController.class).listarMedicos()).withSelfRel());

        return models;
    }

    private DepartamentoDto toDepartamentoDto(Departamento departamento) {
        var dto = new DepartamentoDto();
        dto.setId(departamento.getId());
        dto.setLocalizacao(departamento.getLocalizacao());
        dto.add(linkTo(
                methodOn(DepartamentoController.class)
                        .getDepartamento(departamento.getId()))
                .withSelfRel());
        return dto;
    }
}