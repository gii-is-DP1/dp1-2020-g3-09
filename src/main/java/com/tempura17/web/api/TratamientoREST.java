package com.tempura17.web.api;

import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.TratamientoService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tratamiento;

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
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoREST {

    @Autowired
    private final TratamientoService tratamientoService;

    private static final String PATH = "/api/tratamientos";


    public TratamientoREST(TratamientoService tratamientoService){
        this.tratamientoService = tratamientoService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Tratamiento>> all() {
        Collection<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        tratamientos.addAll(this.tratamientoService.findAll());
        if (tratamientos.isEmpty()) {
            return new ResponseEntity<Collection<Tratamiento>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Tratamiento>>(tratamientos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Tratamiento> one(@PathVariable("id") Integer id) {
        Tratamiento tratamiento = this.tratamientoService.findById(id).get();
        if (tratamiento == null) {
            return new ResponseEntity<Tratamiento>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Tratamiento>(tratamiento, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tratamiento> create(
            @RequestBody @Valid Tratamiento tratamiento
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (tratamiento == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Tratamiento>(headers, HttpStatus.BAD_REQUEST);
        }
        this.tratamientoService.save(tratamiento);
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(tratamiento.getId()).toUri());
		return new ResponseEntity<Tratamiento>(tratamiento, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Tratamiento> update(@PathVariable("id") Integer id, @RequestBody @Valid Tratamiento newTratamiento, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newTratamiento == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Tratamiento>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.tratamientoService.findById(id).get() == null){
			return new ResponseEntity<Tratamiento>(HttpStatus.NOT_FOUND);
		}
        // tratamientos (id, poliza_id, descripcion, duracion, acta_id)
        Tratamiento updatedTratamiento = this.tratamientoService.findById(id)
                    .map(tratamiento -> {
                            String descripcion = newTratamiento.getDescripcion() == null ? tratamiento.getDescripcion() : newTratamiento.getDescripcion();
                            tratamiento.setDescripcion(descripcion);
                            Integer duracion = newTratamiento.getDuracion() == null ? tratamiento.getDuracion() : newTratamiento.getDuracion();
                            tratamiento.setDuracion(duracion);
                            this.tratamientoService.save(tratamiento);
                            return tratamiento;
                    }) 
                    .orElseGet(() -> {
                        newTratamiento.setId(id);
                        this.tratamientoService.save(newTratamiento);
                        return newTratamiento;
                    });

		return new ResponseEntity<Tratamiento>(updatedTratamiento, HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		Tratamiento tratamiento = this.tratamientoService.findById(id).get();
		if(tratamiento == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.tratamientoService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    

    
}
