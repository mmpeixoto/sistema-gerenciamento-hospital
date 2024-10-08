package com.iff.sistema_gerenciamento_hospital.domain.mapper;

import com.iff.sistema_gerenciamento_hospital.controllers.apiRest.PacienteController;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.PacienteDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PacienteMapper extends RepresentationModelAssemblerSupport<Paciente, PacienteDto> {
    public PacienteMapper() {
        super(PacienteController.class, PacienteDto.class);
    }

    @Override
    public PacienteDto toModel(Paciente entity) {
        PacienteDto pacienteDto = instantiateModel(entity);

        pacienteDto.add(linkTo(
                methodOn(PacienteController.class)
                        .acharPacientePorId(entity.getId()))
                .withSelfRel());

        pacienteDto.setId(entity.getId());
        pacienteDto.setNome(entity.getNome());
        pacienteDto.setCpf(entity.getCpf());
        pacienteDto.setTelefone(entity.getTelefone());
        pacienteDto.setEndereco(entity.getEndereco());
        pacienteDto.setDataNascimento(entity.getDataNascimento());
        pacienteDto.setPeso(entity.getPeso());
        pacienteDto.setAltura(entity.getAltura());
        pacienteDto.setTipoSanguineo(entity.getTipoSanguineo());
        return pacienteDto;
    }

    @Override
    public CollectionModel<PacienteDto> toCollectionModel(Iterable<? extends Paciente> entities) {
        CollectionModel<PacienteDto> models = super.toCollectionModel(entities);

        models.add(linkTo(methodOn(PacienteController.class).listarPacientes()).withSelfRel());

        return models;
    }
}