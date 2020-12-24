package com.tempura17.web.api;

import java.util.List;
import java.util.stream.Collectors;

import com.tempura17.service.PacienteService;
import com.tempura17.model.*;
import com.tempura17.model.modelAssembler.PacienteModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteREST{

	@Autowired
	private final PacienteService pacienteService;

	@Autowired
	private final PacienteModelAssembler assembler;
    
    public PacienteREST(PacienteService pacienteService, PacienteModelAssembler assembler){
		this.pacienteService = pacienteService;
		this.assembler = assembler;
    }

	
	@GetMapping
	public CollectionModel<EntityModel<Paciente>> all() {
		List<EntityModel<Paciente>>  pacientes = this.pacienteService
						.findAll().stream()
						.map(assembler::toModel)
						.collect(Collectors.toList());
			
		return new CollectionModel(pacientes,   linkTo(methodOn(PacienteREST.class).all()).withSelfRel());
	}

	@GetMapping("/{id}")
    public EntityModel<Paciente> one(@PathVariable Integer id) {
        Paciente paciente = pacienteService.findById((id))
            				.orElseThrow(null);

        return assembler.toModel(paciente);
	  }

	  
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Paciente paciente) {
		pacienteService.save(paciente);
		EntityModel<Paciente> entityModel = assembler.toModel(paciente);
 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel);  
	}

	@PutMapping("/{id}")
	ResponseEntity<?> update(@RequestBody Paciente newPaciente, @PathVariable Integer id) {
		Paciente updatedPaciente = pacienteService.findById(id)
					.map(paciente -> {
							paciente.setEmail(newPaciente.getEmail());
							paciente.setDireccion(newPaciente.getDireccion());
							paciente.setSexo(newPaciente.getSexo());
							paciente.setEdad(newPaciente.getEdad());
							paciente.setCitas(newPaciente.getCitas());
							this.pacienteService.save(paciente);
							return paciente;
				 		}) 
				  .orElseGet(() -> {
					  newPaciente.setId(id);
					  this.pacienteService.save(newPaciente);
					  return newPaciente;
				  });
  
		EntityModel<Paciente> entityModel = assembler.toModel(updatedPaciente);
  
		return ResponseEntity 
				  .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				  .body(entityModel);
	}
  
	@DeleteMapping("/{id}")
	ResponseEntity<?> delete(@PathVariable Integer id) {
		pacienteService.deleteById(id);
	
		return ResponseEntity.noContent().build();
	}
  


}