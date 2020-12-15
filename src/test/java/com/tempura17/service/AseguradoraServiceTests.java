package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Especialista;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import com.tempura17.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class AseguradoraServiceTests {

    @Autowired
    private AseguradoraService aseguradoraService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EspecialistaService especialistaService;

    private  Paciente paciente;
    private  Aseguradora aseguradora;
    private  Especialista especialista;
    private  Integer aseguradora_id;
    private  Integer paciente_id;
    private  Integer especialista_id;


    
    public Especialista createDummyEspecialista() {
        Especialista especialista = new Especialista();
        especialista.setFirstName("Dr");
        especialista.setLastName("lastName_4");
        return especialista;
    }

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

    
    public Aseguradora findRandomAseguradora(){
        Random randomGenerator = new Random();
        List<Aseguradora> aseguradoras = this.aseguradoraService.findAll().stream()
                                                        .filter(x -> x.getEspecialistas().size() > 0 
                                                        && x.getPacientes().size() > 0)
                                                        .collect(Collectors.toList());
        Integer rand =  randomGenerator.nextInt(aseguradoras.size());
        return aseguradoras.get(rand);

    }
    
    
    public Paciente findRandomPacienteFromAseguradora(Aseguradora aseguradora){
        Random randomGenerator = new Random();
        List<Paciente> aseguradora_pacientes = aseguradora.getPacientes().stream()
                                                .collect(Collectors.toList());
        Integer numPacientes = aseguradora_pacientes.size();
        Integer rand =  randomGenerator.nextInt(numPacientes);
        return aseguradora_pacientes.get(rand);

    }

    public Especialista findRandomEspecialistaFromAseguradora(Aseguradora aseguradora){
        Random randomGenerator = new Random();
        List<Especialista> aseguradora_especialistas = aseguradora.getEspecialistas().stream()
                                                .collect(Collectors.toList());
        Integer numEspecialistas = aseguradora_especialistas.size();
        Integer rand =  randomGenerator.nextInt(numEspecialistas);
        return aseguradora_especialistas.get(rand);

    }

    // tests positivos 
    @Test
    @Transactional
    public void shouldBe() {
        aseguradora = findRandomAseguradora();
        especialista = findRandomEspecialistaFromAseguradora(aseguradora);        
        assertThat(aseguradora.getEspecialistas()).contains(especialista);

    }

    @Test
    @Transactional
	void deletePacienteFromAseguradora() {
        aseguradora = findRandomAseguradora();
        paciente = findRandomPacienteFromAseguradora(aseguradora);
        aseguradora_id = aseguradora.getId();
        paciente_id = paciente.getId();
        Set<Paciente> aseguradora_pacientes = new HashSet<>(aseguradora.getPacientes());
        // Llamada a funcion a probar
        this.aseguradoraService.deletePaciente(aseguradora_id, paciente_id);
        //Se comprueba que el paciente no tiene la aseguradora anterior
        paciente = this.pacienteService.findById(paciente_id).get();
        assertThat(paciente.getAseguradora()).isEqualTo(null);
        //Se compueba que la aseguradora no tiene al paciente anterior asociado
        aseguradora_pacientes.remove(paciente);
        assertThat(aseguradora.getPacientes()).containsAll(aseguradora_pacientes);
    
    }

    @Test
    @Transactional
    void deleteEspecialistaFromAseguradora() {
        aseguradora = findRandomAseguradora();
        especialista = findRandomEspecialistaFromAseguradora(aseguradora);
        aseguradora_id = aseguradora.getId();
        especialista_id = especialista.getId();
        Integer numAsePrior = especialista.getAseguradoras().size();
        Integer numEspPrior = aseguradora.getEspecialistas().size();
        // Llamada a funcion a probar
        this.aseguradoraService.deleteEspecialista(aseguradora_id, especialista_id);
        //Comprobamos que el especialista ha eliminado a la aseguradora en cuestion
        especialista = this.especialistaService.findById(especialista_id).get();
        Integer numAsePost = especialista.getAseguradoras().size();
        assertThat(numAsePrior).isEqualTo(numAsePost+1);
        //Comprobamos que la aseguradora ha eliminado al especialista
        aseguradora = this.aseguradoraService.findById(aseguradora_id).get();
        Integer numEspPost = aseguradora.getEspecialistas().size();
        assertThat(numEspPrior).isEqualTo(numEspPost+1);
    }

    @Test
    @Transactional
    void editEspecialidad() {
        aseguradora = findRandomAseguradora();
        especialista = findRandomEspecialistaFromAseguradora(aseguradora);
        aseguradora_id = aseguradora.getId();
        especialista_id = especialista.getId();
        Especialidad especialidad = Especialidad.NEUMOLOGIA;
        // Llamada a funcion a probar
        this.aseguradoraService.editEspecialidad(especialista.getId(), especialidad.toString());
        especialista = this.especialistaService.findById(especialista_id).get();
        // Comprobamos que la especialidad asociada al especialista el cual ha sido modificado, refleja la especialidad esperada
        assertThat(especialidad).isEqualTo(especialista.getEspecialidad());
        // Comprobamos que la aseguradora que contiene ese especialista modificado tambien deveuelve la misma nueva especialidad asignada
        aseguradora = this.aseguradoraService.findById(aseguradora_id).get();
        especialista = aseguradora.getEspecialistas().stream()
                                   .filter(x -> x.getId().equals(especialista_id))
                                   .collect(Collectors.toList())
                                   .get(0);
        //Se comprueba que el especialista modificado también está modificado en su aseguradora
        assertThat(especialidad).isEqualTo(especialista.getEspecialidad());
    }


    @Test
    @Transactional
    void createEspecialista(){
        aseguradora = findRandomAseguradora();
        aseguradora_id = aseguradora.getId();
        Integer numEspecialistasPrior = this.aseguradoraService.findById(aseguradora_id).get().getEspecialistas().size();
        Especialista especialista = createDummyEspecialista();
        Set<Aseguradora> aseguradoras = new HashSet<>(aseguradora_id);
        especialista.setAseguradoras(aseguradoras);
        // Llamada a funcion a probar
        this.aseguradoraService.createEspecialista(especialista, aseguradora_id);
        Integer numEspecialistasPost = this.aseguradoraService.findById(aseguradora_id).get().getEspecialistas().size();
        assertThat(numEspecialistasPrior + 1).isEqualTo(numEspecialistasPost);
    }


}
