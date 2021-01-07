package com.tempura17.web.api;

import com.tempura17.service.EspecialistaService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaREST {

    @Autowired
    private final EspecialistaService especialistaService;

    public EspecialistaREST(EspecialistaService especialistaService) {
        this.especialistaService = especialistaService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Especialista>> all() {
        Collection<Especialista> especialistas = new ArrayList<Especialista>();
        especialistas.addAll(this.especialistaService.findAll());
        if (especialistas.isEmpty()) {
            return new ResponseEntity<Collection<Especialista>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Especialista>>(especialistas, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Especialista> one(@PathVariable("id") Integer id) {
        Optional<Especialista> especialista = this.especialistaService.findById(id);
        if (especialista.get() == null) {
            return new ResponseEntity<Especialista>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Especialista>(especialista.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Especialista> create(
            @RequestBody @Valid Especialista especialista
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (especialista == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Especialista>(headers, HttpStatus.BAD_REQUEST);
        }
        this.especialistaService.save(especialista);
		headers.setLocation(ucBuilder.path("/api/especialistas").buildAndExpand(especialista.getId()).toUri());
		return new ResponseEntity<Especialista>(especialista, headers, HttpStatus.CREATED);

    }
    
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Especialista> update(@PathVariable("id") Integer id, @RequestBody @Valid Especialista newEspecialista, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newEspecialista == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Especialista>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.especialistaService.findById(id).get() == null){
			return new ResponseEntity<Especialista>(HttpStatus.NOT_FOUND);
		}
        // especialistas(id, first_name,last_name,dni,direccion,telefono,correo,especialidad)
        Especialista updatedEspecialista = this.especialistaService.findById(id)
                    .map(especialista -> {
                            especialista.setFirstName(newEspecialista.getFirstName());
                            especialista.setLastName(newEspecialista.getLastName());
                            especialista.setDni(newEspecialista.getDni());
                            especialista.setDireccion(newEspecialista.getDireccion());
                            especialista.setTelefono(newEspecialista.getTelefono());
                            especialista.setCorreo(newEspecialista.getTelefono());
                            especialista.setEspecialidad(newEspecialista.getEspecialidad());
                            this.especialistaService.save(especialista);
                            return especialista;
                    }) 
                    .orElseGet(() -> {
                        newEspecialista.setId(id);
                        this.especialistaService.save(newEspecialista);
                        return newEspecialista;
                    });

		return new ResponseEntity<Especialista>(updatedEspecialista, HttpStatus.NO_CONTENT);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		Especialista especialista = this.especialistaService.findById(id).get();
		if(especialista == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.especialistaService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

    // Adicion : errors
	@PostMapping("/{id_paciente}/{id_especialista}")
    public Cita createCitaForPacienteId(@PathVariable("id_paciente") int id_paciente
                                        ,@PathVariable("id_especialista") int id_especialista
                                        ,@RequestBody Cita cita) {
        this.especialistaService.createCitaForPacienteId(cita, id_paciente, id_especialista);
        return cita;
    }
    

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Especialista> saveCitaForEspecialista(@PathVariable("id") Integer id
                                                                , @RequestBody @Valid Cita cita
                                                                , BindingResult bindingResult
                                                                , UriComponentsBuilder ucBuilder){

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        Optional<Especialista> especialista = this.especialistaService.findById(id);
        if (especialista.get() == null) {
            return new ResponseEntity<Especialista>(HttpStatus.NOT_FOUND);
        }

        if(bindingResult.hasErrors() || (cita == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Especialista>(headers, HttpStatus.BAD_REQUEST);
        }
        this.especialistaService.saveCitaForEspecialista(id, cita);
		headers.setLocation(ucBuilder.path("/api/especialistas/" + id).buildAndExpand(especialista.get().getId()).toUri());
		return new ResponseEntity<Especialista>(especialista.get(), headers, HttpStatus.CREATED);

    }
	

}
