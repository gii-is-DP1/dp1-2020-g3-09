package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.model.Especialista;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EspecialistaServiceTests {
    
    @Autowired
    private EspecialistaService especialistaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;


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

    @Test
    @Disabled
    @Transactional
    void saveCitaForEspecialista(){
        //cita cita, Integer paciente_id
        Random randomGenerator = new Random();
        Paciente paciente = findRandomPaciente();
        Especialista especialista = findRandomEspecialista();
        Integer especialista_id = especialista.getId();
        Integer numCitasPrior = especialista.getCitas().size();
        List<Cita> citas = this.citaService.findAll().stream()
                                        .collect(Collectors.toList());
        Integer rand = randomGenerator.nextInt(citas.size());
        Cita cita = citas.get(rand);
        cita.setId(citas.size());
        cita.setEspecialista(especialista);
        cita.setPaciente(paciente);
        // Llamada a la funcion a verificar
        this.especialistaService.save(especialista);
        Integer numCitasPost = this.especialistaService.findById(especialista_id).get().getCitas().size();
        assertThat(numCitasPrior + 1).isEqualTo(numCitasPost);


    }

}
