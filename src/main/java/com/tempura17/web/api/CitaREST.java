package com.tempura17.web.api;

import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Especialista;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;

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
@RequestMapping("/api/citas")
public class CitaREST {

    @Autowired
    private final CitaService citaService;

    @Autowired
    private final PacienteService pacienteService;

    @Autowired
    private final EspecialistaService especialistaService;

    private static final String PATH = "/api/citas";

    public CitaREST(CitaService citaService, PacienteService pacienteService, EspecialistaService especialistaService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.especialistaService = especialistaService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Cita>> all() {
        Collection<Cita> citas = new ArrayList<Cita>();
        citas.addAll(this.citaService.findAll());
        if (citas.isEmpty()) {
            return new ResponseEntity<Collection<Cita>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Cita>>(citas, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Cita> one(@PathVariable("id") Integer id) {
        Cita cita = this.citaService.findById(id).get();
        if (cita == null) {
            return new ResponseEntity<Cita>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cita>(cita, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id_paciente}/{id_especialista}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Cita> create(
                        @PathVariable("id_paciente") int id_paciente
                        , @PathVariable("id_especialista") int id_especialista
                        , @Valid @RequestBody Cita cita
                        , BindingResult bindingResult
                        , UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (cita == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Cita>(headers, HttpStatus.BAD_REQUEST);
        }
        // Interesante consideracion 
        Paciente paciente = this.pacienteService.findById(id_paciente).get();
        Especialista especialista = this.especialistaService.findById(id_especialista).get();
        cita.setPaciente(paciente);
        cita.setEspecialista(especialista);
        this.citaService.save(cita);
        this.especialistaService.saveCitaForEspecialista(id_especialista, cita);
        if(paciente.getCitas() == null){
            Set<Cita> citas = new HashSet<>();
            paciente.setCitas(citas);
            paciente.getCitas().add(cita);
            pacienteService.save(paciente);
        }else{
            paciente.addCita(cita);
            pacienteService.save(paciente);
        }
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(cita.getId()).toUri());
		return new ResponseEntity<Cita>(cita, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Cita> update(@PathVariable("id") Integer id, @RequestBody @Valid Cita newCita, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newCita == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Cita>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.citaService.findById(id).get() == null){
			return new ResponseEntity<Cita>(HttpStatus.NOT_FOUND);
		}
        // citas(id, paciente_id, especialista_id, formato, tipo, especialidad, fecha)
        //   1,3,1,'PRESENCIAL','ASEGURADO','MEDICINA_GENERAL','2019-01-27 22:00:00'); 
        Cita updatedCita = this.citaService.findById(id)
                    .map(cita -> {
                            Formato formato = newCita.getFormato() == null ? cita.getFormato() : newCita.getFormato();
                            cita.setFormato(formato);
                            Tipologia tipo = newCita.getTipo() == null ? cita.getTipo() : newCita.getTipo();
                            cita.setTipo(tipo);
                            Especialidad especialidad = newCita.getEspecialidad() == null ? cita.getEspecialidad() : newCita.getEspecialidad();
                            cita.setEspecialidad(especialidad);
                            Date fecha = newCita.getFecha() == null ? cita.getFecha() : newCita.getFecha();
                            cita.setFecha(fecha);
                            //Integer id_paciente = cita.getPaciente().getId();
                            Integer id_especialista = cita.getEspecialista().getId();
                            Paciente paciente = cita.getPaciente();
                            //Especialista especialista = cita.getEspecialista();
                            this.especialistaService.saveCitaForEspecialista(id_especialista, cita);
                            if(paciente.getCitas() == null){
                                Set<Cita> citas = new HashSet<>();
                                paciente.setCitas(citas);
                                paciente.getCitas().add(cita);
                                pacienteService.save(paciente);
                            }else{
                                paciente.addCita(cita);
                                pacienteService.save(paciente);
                            }
                            this.citaService.save(cita);
                            return cita;
                    }) 
                    .orElseGet(() -> {
                        newCita.setId(id);
                        Integer id_especialista = newCita.getEspecialista().getId();
                        Paciente paciente = newCita.getPaciente();
                        this.especialistaService.saveCitaForEspecialista(id_especialista, newCita);
                        if(paciente.getCitas() == null){
                            Set<Cita> citas = new HashSet<>();
                            paciente.setCitas(citas);
                            paciente.getCitas().add(newCita);
                            pacienteService.save(paciente);
                        }else{
                            paciente.addCita(newCita);
                            pacienteService.save(paciente);
                        }
                        this.citaService.save(newCita);
                        return newCita;
                    });

		return new ResponseEntity<Cita>(updatedCita, HttpStatus.NO_CONTENT);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable("id") int id){
		Cita cita = this.citaService.findById(id).get();
		if(cita == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.citaService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
}
