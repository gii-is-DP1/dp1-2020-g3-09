package com.tempura17.web.api;

import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.TratamientoService;
import com.tempura17.service.ActaService;


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
import com.tempura17.model.Acta;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
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
@RequestMapping("/api/actas")
public class ActaREST {

    @Autowired
    private final ActaService actaService;

    private static final String PATH = "/api/actas";


    public ActaREST(ActaService actaService){
        this.actaService = actaService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Acta>> all() {
        Collection<Acta> actas = new ArrayList<Acta>();
        actas.addAll(this.actaService.findAll());
        if (actas.isEmpty()) {
            return new ResponseEntity<Collection<Acta>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Acta>>(actas, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Acta> one(@PathVariable("id") Integer id) {
        Acta acta = this.actaService.findById(id).get();
        if (acta == null) {
            return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Acta>(acta, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Acta> create(
            @RequestBody @Valid Acta acta
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (acta == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Acta>(headers, HttpStatus.BAD_REQUEST);
        }
        this.actaService.save(acta);
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(acta.getId()).toUri());
		return new ResponseEntity<Acta>(acta, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Acta> update(@PathVariable("id") Integer id, @RequestBody @Valid Acta newActa, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newActa == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Acta>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.actaService.findById(id).get() == null){
			return new ResponseEntity<Acta>(HttpStatus.NOT_FOUND);
		}
        // actas(id, especialista_id, descripcion, exploracion, diagnostico, cita_id)
        Acta updatedActa = this.actaService.findById(id)
                    .map(acta -> {
                            String descripcion = newActa.getDescripcion() == null ? acta.getDescripcion() : newActa.getDescripcion();
                            acta.setDescripcion(descripcion);;
                            String exploracion = newActa.getExploracion() == null ? acta.getExploracion() : newActa.getExploracion();
                            acta.setExploracion(exploracion);
                            String diagnostico = newActa.getDiagnostico() == null ? acta.getDiagnostico() : newActa.getDiagnostico();
                            acta.setDiagnostico(diagnostico);
                            this.actaService.save(acta);
                            return acta;
                    }) 
                    .orElseGet(() -> {
                        newActa.setId(id);
                        this.actaService.save(newActa);
                        return newActa;
                    });

		return new ResponseEntity<Acta>(updatedActa, HttpStatus.NO_CONTENT);
    }

}
