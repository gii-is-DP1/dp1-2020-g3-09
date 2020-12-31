package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

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
public class PacienteServiceTests {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;

    @Test
    @Transactional
    void saveCitaforPaciente(){
        Paciente p1 = new Paciente();
        p1.setFirstName("firstName");
        p1.setLastName("lastName");
        pacienteService.save(p1);

        Cita c1 = new Cita();
        citaService.save(c1);

        pacienteService.saveCitaForPaciente(p1.getId(),c1);

        p1 = pacienteService.findById(p1.getId()).get();
        assertThat(p1.getCitas().size()).isEqualTo(1);
    }
    
}
