package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
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

    public Especialista findRandomEspecialista(){
        Random randomGenerator = new Random();
        List<Especialista> especialistas = this.especialistaService.findAll().stream()
                                                .collect(Collectors.toList());
        Integer rand =  especialistas.size() == 1 ? 0 : randomGenerator.nextInt(especialistas.size());
        return especialistas.get(rand);
    }

    public Paciente findRandomPaciente(){
        Random randomGenerator = new Random();
        List<Paciente> pacientes = this.pacienteService.findAll().stream()
                                                .collect(Collectors.toList());
        Integer rand = pacientes.size() == 1 ? 0 : randomGenerator.nextInt(pacientes.size());
        return pacientes.get(rand);
    }
    
    @Test
    @Disabled
    @Transactional
    void saveCitaforPaciente(){
        Integer numCitasPrior = this.citaService.findAll().size();
        Cita cita = new Cita();
        cita.setFormato(Formato.PRESENCIAL);
        cita.setTipo(Tipologia.ASEGURADO);
        cita.setEspecialidad(Especialidad.PEDIATRIA);
        Especialista especialista = findRandomEspecialista();
        cita.setEspecialista(especialista);
        Paciente paciente = findRandomPaciente();
        Integer id_paciente = paciente.getId();
        Integer numCitasPacPrior = paciente.getCitas().size();
        cita.setPaciente(paciente);
        //Zona horaria
	    ZoneId defaultZoneId = ZoneId.systemDefault();
	    //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date fecha = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        cita.setFecha(fecha);
        paciente.addCita(cita);
        this.citaService.save(cita);
        this.pacienteService.save(paciente);
        Integer numCitasPost = this.citaService.findAll().size();
        Integer numCitasPacPost = this.pacienteService.findById(id_paciente).get().getCitas().size();
        assertThat(numCitasPost).isEqualTo(numCitasPrior+1);
        assertThat(numCitasPacPost).isEqualTo(numCitasPacPrior+1);

        
    }
    
}
