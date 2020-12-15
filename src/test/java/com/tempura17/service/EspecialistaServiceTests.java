package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.model.Especialista;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import com.tempura17.util.EntityUtils;
import java.util.List;
import java.util.Random;

import com.tempura17.service.*;
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

    private Especialista especialista;
    private Paciente paciente;
    private  Integer paciente_id;
    private  Integer especialista_id;
    

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
	@Transactional
	public void shoulBe() {
        Especialista especialista = this.especialistaService.findById(1).get();
        assertThat(especialista.getFirstName()).isEqualTo("Ceballo");

    }

    @Test
    @Transactional
    void shouldCreateCitaForPacienteId(){
        //cita cita, Integer paciente_id
        paciente = findRandomPaciente();
        especialista = findRandomEspecialista();
        paciente_id = paciente.getId();
        especialista_id = especialista.getId();
        Integer numCitasPrior = paciente.getCitas().size();
        Integer numCitasPrior1 = especialista.getCita().size();
        Cita cita = new Cita();
        // Llamada a la funcion a verificar
        this.especialistaService.createCitaForPacienteId(cita, paciente_id, especialista_id);
        Integer numCitasPost = this.pacienteService.findById(paciente_id).get().getCitas().size();
        Integer numCitasPost1 = this.especialistaService.findById(especialista_id).get().getCita().size();
        assertThat(numCitasPrior + 1).isEqualTo(numCitasPost);
        assertThat(numCitasPrior1 + 1).isEqualTo(numCitasPost1);


        }

}
