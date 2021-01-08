package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import com.tempura17.model.Especialista;
import com.tempura17.model.Formato;

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
        Integer numCitasPrior1 = especialista.getCitas().size();
        Cita cita = new Cita();
        cita.setTipo(Tipologia.ASEGURADO);
        cita.setFormato(Formato.ONLINE);
        // Llamada a la funcion a verificar
        this.especialistaService.createCitaForPacienteId(cita, paciente_id, especialista_id);
        Integer numCitasPost = this.pacienteService.findById(paciente_id).get().getCitas().size();
        Integer numCitasPost1 = this.especialistaService.findById(especialista_id).get().getCitas().size();
        assertThat(numCitasPrior + 1).isEqualTo(numCitasPost);
        assertThat(numCitasPrior1 + 1).isEqualTo(numCitasPost1);


        }

    
    @Test
    @Transactional
    void saveCitaforEspecialista(){
        Especialista e1 = new Especialista();
        e1.setFirstName("firstName");
        e1.setLastName("lastName");
        especialistaService.save(e1);

        Cita c1 = new Cita();
        c1.setFormato(Formato.ONLINE);
        c1.setTipo(Tipologia.ASEGURADO);
        Paciente p1 = new Paciente();
        p1.setFirstName("firstName");
        p1.setLastName("lastName");
        p1.setDni("25000000A");
        pacienteService.save(p1);
        c1.setPaciente(p1);
        c1.setEspecialista(e1);
        citaService.save(c1);

        especialistaService.saveCitaForEspecialista(e1.getId(),c1);

        e1 = especialistaService.findById(e1.getId()).get();
        assertThat(e1.getCitas().size()).isEqualTo(1);
    }

    

}
