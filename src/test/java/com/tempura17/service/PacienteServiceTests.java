package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import com.tempura17.model.Especialista;
import com.tempura17.model.Formato;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PacienteServiceTests {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private EspecialistaService especialistaService;

    @Test
    @Transactional
    void saveCitaforPaciente(){
        Paciente p1 = new Paciente();
        p1.setDni("25000000A");
        p1.setFirstName("firstName");
        p1.setLastName("lastName");
        pacienteService.save(p1);

        Especialista e1 = new Especialista();
        e1.setFirstName("firstName");
        e1.setLastName("lastName");
        especialistaService.save(e1);
        
        Cita c1 = new Cita();
        c1.setFormato(Formato.ONLINE);
        c1.setTipo(Tipologia.ASEGURADO);
        c1.setEspecialidad(Especialidad.PEDIATRIA);
        c1.setEspecialista(e1);
        c1.setFecha(null); 
        c1.setPaciente(p1);
        citaService.save(c1);

        pacienteService.saveCitaForPaciente(p1.getId(),c1);

        p1 = pacienteService.findById(p1.getId()).get();
        assertThat(p1.getCitas().size()).isEqualTo(1);
    }
    
}
