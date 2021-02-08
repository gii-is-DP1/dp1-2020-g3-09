package com.tempura17.web.api;

import com.tempura17.service.ActaService;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Especialista;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import com.tempura17.model.Acta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private final ActaService actaService;

    private static final String PATH = "/api/citas";
    private static final char[] abc = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public CitaREST(CitaService citaService, PacienteService pacienteService, EspecialistaService especialistaService,
            ActaService actaService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.especialistaService = especialistaService;
        this.actaService = actaService;
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

    // Funcion verificada
    @RequestMapping(value = "/{id_paciente}/{id_especialista}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Cita> create(@PathVariable("id_paciente") int id_paciente,
            @PathVariable("id_especialista") int id_especialista, @Valid @RequestBody Cita cita,
            BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (cita == null)) {
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
        paciente.addCita(cita);
        especialista.addCita(cita);
        this.especialistaService.save(especialista);
        this.pacienteService.save(paciente);
        headers.setLocation(ucBuilder.path(PATH).buildAndExpand(cita.getId()).toUri());
        return new ResponseEntity<Cita>(cita, headers, HttpStatus.CREATED);

    }

    // Funcion verificada
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Cita> update(@PathVariable("id") Integer id, @RequestBody @Valid Cita newCita,
            BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (newCita == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Cita>(headers, HttpStatus.BAD_REQUEST);
        }
        if (this.citaService.findById(id).get() == null) {
            return new ResponseEntity<Cita>(HttpStatus.NOT_FOUND);
        }
        // citas(id, paciente_id, especialista_id, formato, tipo, especialidad, fecha)
        // 1,3,1,'PRESENCIAL','ASEGURADO','MEDICINA_GENERAL','2019-01-27 22:00:00');
        Cita updatedCita = this.citaService.findById(id).map(cita -> {
            Formato formato = newCita.getFormato() == null ? cita.getFormato() : newCita.getFormato();
            cita.setFormato(formato);
            Tipologia tipo = newCita.getTipo() == null ? cita.getTipo() : newCita.getTipo();
            cita.setTipo(tipo);
            Especialidad especialidad = newCita.getEspecialidad() == null ? cita.getEspecialidad()
                    : newCita.getEspecialidad();
            cita.setEspecialidad(especialidad);
            Date fecha = newCita.getFecha() == null ? cita.getFecha() : newCita.getFecha();
            cita.setFecha(fecha);
            // Integer id_paciente = cita.getPaciente().getId();
            Especialista especialista = cita.getEspecialista();
            Paciente paciente = cita.getPaciente();
            // Especialista especialista = cita.getEspecialista();
            especialista.addCita(cita);
            paciente.addCita(cita);
            this.citaService.save(cita);
            this.especialistaService.save(especialista);
            this.pacienteService.save(paciente);
            return cita;
        }).orElseGet(() -> {
            newCita.setId(id);
            Especialista especialista = newCita.getEspecialista();
            Paciente paciente = newCita.getPaciente();
            especialista.addCita(newCita);
            paciente.addCita(newCita);
            this.citaService.save(newCita);
            this.especialistaService.save(especialista);
            this.pacienteService.save(paciente);
            return newCita;
        });

        return new ResponseEntity<Cita>(updatedCita, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Cita cita = this.citaService.findById(id).get();
        Paciente paciente = cita.getPaciente();
        Especialista especialista = cita.getEspecialista();
        // Acta acta = new ArrayList<>(especialista.getActas()).get(cita.getId());
        if (cita == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.citaService.deleteById(cita.getId());
        this.pacienteService.deleteById(paciente.getId());
        // this.especialistaService.deleteById(especialista.getId());
        // this.actaService.deleteById(acta.getId());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/random/new/{size}", method = RequestMethod.GET)
    public void createRandomHuge(@PathVariable("size") Integer size) {

        for(int i=0; i<size; i++){
            createRandomCita();

        }        
    }
    // Funcion verificada
    @RequestMapping(value = "/random/new", method = RequestMethod.GET)
    public Cita createRandomCita() {
        Random randomGenerator = new Random();
        Cita cita = new Cita();
        List<Tipologia> tipos = Arrays.asList(Tipologia.values());
        Integer rand = randomGenerator.nextInt(tipos.size());
        Tipologia tipo = tipos.get(rand);
        cita.setTipo(tipo);

        List<Formato> formatos = Arrays.asList(Formato.values());
        rand = randomGenerator.nextInt(formatos.size());
        Formato formato = formatos.get(rand);
        cita.setFormato(formato);

        //Zona horaria
	    ZoneId defaultZoneId = ZoneId.systemDefault();
	    //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date fecha = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        cita.setFecha(fecha);

        List<Especialidad> especialidades = Arrays.asList(Especialidad.values());
        rand = randomGenerator.nextInt(especialidades.size());
        Especialidad especialidad;
        if(formato==Formato.PRESENCIAL){
            especialidad = especialidades.get(rand);
        }else{
            especialidad = Especialidad.PSIQUIATRIA;
        }
        cita.setEspecialidad(especialidad);

        Especialista especialista = findRandomEspecialista();
        Acta acta = createRandomActa();
        acta.setEspecialista(especialista);
        especialista.addActa(acta);
        cita.setEspecialista(especialista);
        Paciente paciente = findRandomPaciente();
        cita.setPaciente(paciente);
        acta.setCita(cita);
        paciente.addCita(cita);
        especialista.addCita(cita); 
        this.citaService.save(cita);
        this.actaService.save(acta);
        this.pacienteService.save(paciente);
        this.especialistaService.save(especialista);        
        return cita;

    }

    // Funcion : no verificada
    @RequestMapping(value = "/random/modify", method = RequestMethod.GET)
    public Cita modifyRandomCita() {	
        Random randomGenerator = new Random();
        List<Cita> citas = new ArrayList<>(this.citaService.findAll());
		Integer rand = randomGenerator.nextInt(citas.size());
        Cita cita = citas.get(rand);

        List<Formato> formatos = Arrays.asList(Formato.values());
        rand = randomGenerator.nextInt(formatos.size());
        Formato formato = formatos.get(rand);
        cita.setFormato(formato);
        //Zona horaria
	    ZoneId defaultZoneId = ZoneId.systemDefault();
	    //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date fecha = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        cita.setFecha(fecha);

        List<Especialidad> especialidades_online = new ArrayList<>();
        especialidades_online.add(Especialidad.PSIQUIATRIA);
		especialidades_online.add(Especialidad.MEDICINA_GENERAL);
		especialidades_online.add(Especialidad.PEDIATRIA);
        List<Especialidad> especialidades = Arrays.asList(Especialidad.values());
        rand = randomGenerator.nextInt(especialidades.size());
        Especialidad especialidad = cita.getEspecialidad();
        if(formato==Formato.PRESENCIAL){
            especialidad = especialidades.get(rand);
            cita.setEspecialidad(especialidad);
        }else{
            rand = randomGenerator.nextInt(especialidades_online.size());
            especialidad = especialidades_online.get(rand);
            cita.setEspecialidad(especialidad);
        }
        
        Especialista especialista = cita.getEspecialista();
        Acta acta = createRandomActa();
        acta.setEspecialista(especialista);
        especialista.addActa(acta);
        cita.setEspecialista(especialista);
        Paciente paciente = cita.getPaciente();
        cita.setPaciente(paciente);
        acta.setCita(cita);
        paciente.addCita(cita);
        especialista.addCita(cita); 
        this.citaService.save(cita);
        this.actaService.save(acta);
        this.pacienteService.save(paciente);
        this.especialistaService.save(especialista);        
        return cita;       

    }
    

    public Especialista findRandomEspecialista(){
        Random randomGenerator = new Random();
        List<Especialista> especialistas = this.especialistaService.findAll().stream()
                                                .collect(Collectors.toList());
        Integer rand =  randomGenerator.nextInt(especialistas.size());
        return especialistas.get(rand);

    }

    public Paciente findRandomPaciente(){
        Random randomGenerator = new Random();
        List<Paciente> pacientes = this.pacienteService.findAll().stream()
                                                .collect(Collectors.toList());
        Integer rand =  randomGenerator.nextInt(pacientes.size());
        return pacientes.get(rand);

    }

    public Acta createRandomActa(){
        Random randomGenerator = new Random();
        Acta acta = new Acta();
        int i;
        Integer letras = abc.length;
        acta.setEspecialista(null);
        String descripcion = "";
        for(i=0; i<50; i++){
            Random l = new Random();
            descripcion += abc[l.nextInt(letras)];
        }
        acta.setDescripcion(descripcion);
        String exploracion = "";
        for(i=0; i<50; i++){
            Random l = new Random();
            exploracion += abc[l.nextInt(letras)];
        }
        acta.setExploracion(exploracion);
        String diagnostico = "";
        for(i=0; i<50; i++){
            Random l = new Random();
            diagnostico += abc[l.nextInt(letras)];
        }
        acta.setDiagnostico(diagnostico);
        return acta;

    }

}
