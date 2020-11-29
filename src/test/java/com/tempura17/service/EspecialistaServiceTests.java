package com.tempura17.service;

import java.security.Provider.Service;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

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
    public void shouldBe(){
        Especialista especialista = this.especialistaService.findById(1).get();
        assertThat(especialista.getFirstName()).isEqualTo("Ceballo");
        
    }

    @Test
    @Transactional
    public void shouldCreateCitaForPacienteId(){
        //cita cita, integer paciente_id
        Integer numCitasPrior = this.pacienteService.findById(3).get().getCitas().size();
        Cita cita = new Cita();
        //cita.setEspecialista(null);
        this.especialistaService.createCitaForPacienteId(cita, 1);
        Integer numCitasPost = this.pacienteService.findById(1).get().getCitas().size();
        assertThat(numCitasPrior + 1).isEqualTo(numCitasPost);
        /*Cita newCita = this.pacienteService.findById(1).get().getCitas().stream()
                                                                        .collect(Collectors.toList())
                                                                        .get(1);*/

        //assertThat(newCita.getEspecialista()).isEqualTo(null);
    }
    
}
