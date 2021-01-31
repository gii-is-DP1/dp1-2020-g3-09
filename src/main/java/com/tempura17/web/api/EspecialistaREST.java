package com.tempura17.web.api;

import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.TratamientoService;
import com.tempura17.service.ActaService;
import com.tempura17.service.CitaService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import com.tempura17.model.Acta;
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

    @Autowired
    private final PacienteService pacienteService;

    @Autowired
    private final TratamientoService tratamientoService;

    @Autowired 
    private final CitaService citaService;

    @Autowired
    private final ActaService actaService;

    private static final String PATH = "/api/especialistas";

    private static final char[] abc = "abcdefghijklmnopqrstuvwxyz".toCharArray();


    public EspecialistaREST(EspecialistaService especialistaService, PacienteService pacienteService
                            , TratamientoService tratamientoService, CitaService citaService
                            , ActaService actaService) {
        this.especialistaService = especialistaService;
        this.pacienteService = pacienteService;
        this.tratamientoService = tratamientoService;
        this.citaService = citaService;
        this.actaService = actaService;
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

    @RequestMapping(value = "/historia/{id_paciente}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Cita>> historiaClinicaId(@PathVariable("id_paciente") Integer id_paciente) {
        Optional<Paciente> paciente = this.pacienteService.findById(id_paciente);
        Collection<Cita> citas = new ArrayList<Cita>();
        citas.addAll(paciente.get().getCitas());
        if (citas.isEmpty()) {
            return new ResponseEntity<Collection<Cita>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Cita>>(citas, HttpStatus.OK);
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
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(especialista.getId()).toUri());
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
                            // Para pringados pedantes
                            String firstName = newEspecialista.getFirstName() == null ? especialista.getFirstName() : newEspecialista.getFirstName();
                            especialista.setFirstName(firstName);
                            String lastName = newEspecialista.getLastName() == null ? especialista.getLastName() : newEspecialista.getLastName();
                            especialista.setLastName(lastName);
                            String dni = newEspecialista.getDni() == null ? especialista.getDni() : newEspecialista.getDni();
                            especialista.setDni(dni);
                            String direccion = newEspecialista.getDireccion() == null ? especialista.getDireccion() : newEspecialista.getDireccion();
                            especialista.setDireccion(direccion);
                            String telefono = newEspecialista.getTelefono() == null ? especialista.getTelefono() : newEspecialista.getTelefono();
                            especialista.setTelefono(telefono);
                            String correo = newEspecialista.getCorreo() == null ? especialista.getCorreo() : newEspecialista.getCorreo();
                            especialista.setCorreo(correo);
                            Especialidad especialidad = newEspecialista.getEspecialidad() == null ? especialista.getEspecialidad() : newEspecialista.getEspecialidad();
                            especialista.setEspecialidad(especialidad);
                            this.especialistaService.save(especialista);
                            return especialista;
                            /* Apto para flipados que desean metaprogramar
                            List<Field> fields = Arrays.asList(Especialista.class.getDeclaredFields());
                            List<Method> methods = Arrays.asList(Especialista.class.getDeclaredMethods());
                            
                            for(int i=0; i<fields.size(); i++){
                                Class<?> clazz = fields.get(i).getType(); // Class.forName("com.tempura17.model.Especialista");    
                                Constructor<?> constructor = clazz.getConstructors()[0];
                                Object field = constructor.newInstance();
                                Method m = methods.get(i);
                                field = m.invoke(newEspecialista) == null ? m.invoke(especialista) : m.invoke(newEspecialista);
                            };
                            */

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
    @RequestMapping(value = "/{id_paciente}/{id_especialista}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Cita>  createCitaForPacienteId(@PathVariable("id_paciente") int id_paciente
                                        , @PathVariable("id_especialista") int id_especialista
                                        , @RequestBody Cita cita
                                        , BindingResult bindingResult
                                        , UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        Paciente paciente = this.pacienteService.findById(id_paciente).get();
        Especialista especialista = this.especialistaService.findById(id_especialista).get();
        if(bindingResult.hasErrors() || (cita == null) || (paciente == null) || (especialista == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Cita>(headers, HttpStatus.BAD_REQUEST);
        }
        Acta acta = createRandomActa();
        acta.setEspecialista(especialista);
        if(especialista.getActas() == null){
            Set<Acta> actas = new HashSet<>();
            actas.add(acta);
            especialista.setActas(actas);
        }else{
            Set<Acta> actas = new HashSet<>(especialista.getActas());
            actas.add(acta);
            especialista.setActas(actas);
        }
        cita.setEspecialista(especialista);
        cita.setPaciente(paciente);
        acta.setCita(cita);
        paciente.addCita(cita);
        especialista.addCita(cita); 
        this.citaService.save(cita);
        this.actaService.save(acta);
        this.pacienteService.save(paciente);
        this.especialistaService.save(especialista);        
        headers.setLocation(ucBuilder.path(PATH).buildAndExpand(id_especialista).toUri());
        return new ResponseEntity<Cita>(cita, headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/tratamiento", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tratamiento> createTratamiento(
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
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand().toUri());
		return new ResponseEntity<Tratamiento>(tratamiento, headers, HttpStatus.CREATED);

    }

    public Acta createRandomActa(){
        Random randomGenerator = new Random();
        Acta acta = new Acta();
        int i;
        Integer letras = abc.length;
        acta.setEspecialista(null);
        String descripcion = "";
        Integer rand =  randomGenerator.nextInt(10);
        for(i=0; i<rand; i++){
            Random l = new Random();
            descripcion += abc[l.nextInt(letras)];
        }
        acta.setDescripcion(descripcion);
        String exploracion = "";
        rand = randomGenerator.nextInt(10);
        for(i=0; i<rand; i++){
            Random l = new Random();
            exploracion += abc[l.nextInt(letras)];
        }
        acta.setExploracion(exploracion);
        String diagnostico = "";
        rand = randomGenerator.nextInt(10);
        for(i=0; i<rand; i++){
            Random l = new Random();
            diagnostico += abc[l.nextInt(letras)];
        }
        acta.setDiagnostico(diagnostico);
        return acta;

    }
	

}
