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
        Integer numCitasPrior = this.pacienteService.findById(1).get().getCitas().size();
        Cita cita = new Cita();
        //cita.setEspecialista("YO");
        this.especialistaService.createCitaForPacienteId(cita, 1);
        Integer numCitasPost = this.pacienteService.findById(1).get().getCitas().size();
        assertThat(numCitasPrior + 1).isEqualTo(numCitasPost);
        //Cita newCita = this.pacienteService.findById(1).get().getCitas().stream()
                                //.collect(Collectors.toList())
                                //.get(1);

        //assertThat(newCita.getEspecialista()).isEqualTo("YO");
        }

}
