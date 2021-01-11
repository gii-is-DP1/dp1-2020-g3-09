package com.tempura17.web.api;


import com.tempura17.service.AseguradoraService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;


import com.tempura17.model.Aseguradora;
import com.tempura17.model.Especialista;
import com.tempura17.model.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/aseguradoras")
public class AseguradoraREST {
    
    @Autowired
    private final AseguradoraService aseguradoraService;

    @Autowired
    private final EspecialistaService especialistaService;

    @Autowired
    private final PacienteService pacienteService;

    private static final String PATH = "/api/aseguradoras";


    public AseguradoraREST(AseguradoraService aseguradoraService, EspecialistaService especialistaService, PacienteService pacienteService){
        this.aseguradoraService = aseguradoraService;
        this.especialistaService = especialistaService;
        this.pacienteService = pacienteService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Aseguradora>> all() {
        Collection<Aseguradora> aseguradoras = new ArrayList<Aseguradora>();
        aseguradoras.addAll(this.aseguradoraService.findAll());
        if (aseguradoras.isEmpty()) {
            return new ResponseEntity<Collection<Aseguradora>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Aseguradora>>(aseguradoras, HttpStatus.OK);

    }

    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Aseguradora> one(@PathVariable("id") Integer id) {
        Aseguradora aseguradora = this.aseguradoraService.findById(id).get();
        if (aseguradora == null) {
            return new ResponseEntity<Aseguradora>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Aseguradora>(aseguradora, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Aseguradora> create(
            @RequestBody @Valid Aseguradora aseguradora
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (aseguradora == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Aseguradora>(headers, HttpStatus.BAD_REQUEST);
        }
        this.aseguradoraService.save(aseguradora);
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(aseguradora.getId()).toUri());
		return new ResponseEntity<Aseguradora>(aseguradora, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Aseguradora> createEspecialista(
            @PathVariable("id") Integer id
            , @RequestBody @Valid Especialista especialista
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        Aseguradora aseguradora = this.aseguradoraService.findById(id).get();
        if(bindingResult.hasErrors() || (especialista == null) || (aseguradora == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Aseguradora>(headers, HttpStatus.BAD_REQUEST);
        }
        this.aseguradoraService.createEspecialista(especialista,id);
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(aseguradora.getId()).toUri());
		return new ResponseEntity<Aseguradora>(aseguradora, headers, HttpStatus.CREATED);

    }



    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Aseguradora> update(@PathVariable("id") Integer id, @RequestBody @Valid Aseguradora newAseguradora, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newAseguradora == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Aseguradora>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.aseguradoraService.findById(id).get() == null){
			return new ResponseEntity<Aseguradora>(HttpStatus.NOT_FOUND);
		}
        // aseguradoras(id,nombre)
        Aseguradora updatedAseguradora = this.aseguradoraService.findById(id)
                    .map(aseguradora -> {
                            String nombre = newAseguradora.getNombre() == null ? aseguradora.getNombre() : newAseguradora.getNombre();
                            aseguradora.setNombre(nombre);
                            this.aseguradoraService.save(aseguradora);
                            return aseguradora;
                    }) 
                    .orElseGet(() -> {
                        newAseguradora.setId(id);
                        this.aseguradoraService.save(newAseguradora);
                        return newAseguradora;
                    });

		return new ResponseEntity<Aseguradora>(updatedAseguradora, HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/{id_aseguradora}/{id_especialista}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> deleteEspecialista(@PathVariable("id_aseguradora") Integer id_aseguradora
                                        , @PathVariable("id_especialista") Integer id_especialista){
        Aseguradora aseguradora = this.aseguradoraService.findById(id_aseguradora).get();
        Especialista especialista = this.especialistaService.findById(id_especialista).get();
		if(aseguradora == null || especialista == null ){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.aseguradoraService.deleteEspecialista(id_aseguradora, id_especialista);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id_aseguradora}/{id_paciente}/paciente", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> deletePaciente(@PathVariable("id_aseguradora") Integer id_aseguradora
                                        , @PathVariable("id_paciente") Integer id_paciente){
        Aseguradora aseguradora = this.aseguradoraService.findById(id_aseguradora).get();
        Paciente paciente = this.pacienteService.findById(id_paciente).get();
		if(aseguradora == null || paciente == null ){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.aseguradoraService.deletePaciente(id_aseguradora, id_paciente);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
}
