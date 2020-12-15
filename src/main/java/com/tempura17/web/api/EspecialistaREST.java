package com.tempura17.web.api;

import com.tempura17.service.EspecialistaService;
import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaREST {
    
    @Autowired
    private final EspecialistaService especialistaService;

    public EspecialistaREST(EspecialistaService especialistaService){
        this.especialistaService = especialistaService;
    }

	@PostMapping("/{id_paciente}/{id_especialista}")
    public Cita createCitaForPacienteId(@PathVariable("id_paciente") int id_paciente
                                        ,@PathVariable("id_especialista") int id_especialista
                                        ,@RequestBody Cita cita) {
        this.especialistaService.createCitaForPacienteId(cita, id_paciente, id_especialista);
        return cita;
	}
	

}
