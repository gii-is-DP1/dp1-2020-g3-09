package com.tempura17.model.modelAssembler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.tempura17.web.api.PacienteREST;
import com.tempura17.model.Paciente;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PacienteModelAssembler implements RepresentationModelAssembler<Paciente, EntityModel<Paciente>> {

  @Override
  public EntityModel<Paciente> toModel(Paciente paciente) {

    // Links incondicionales hacia single-item resource y aggregate root

    EntityModel<Paciente> pacienteModel = new EntityModel(paciente,
        linkTo(methodOn(PacienteREST.class).one(paciente.getId())).withSelfRel(),
        linkTo(methodOn(PacienteREST.class).all()).withRel("/api/pacientes"));

    return pacienteModel;
  }
}