package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Especialista;
import com.tempura17.model.Paciente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.mock;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class AseguradoraServiceTests {

    @Autowired
    private AseguradoraService aseguradoraService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EspecialistaService especialistaService;

    
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
        Integer rand =  numPacientes == 1 ? 0 : randomGenerator.nextInt(numPacientes);
        return aseguradora_pacientes.get(rand);

    }

    public Especialista findRandomEspecialistaFromAseguradora(Aseguradora aseguradora){
        Random randomGenerator = new Random();
        List<Especialista> aseguradora_especialistas = aseguradora.getEspecialistas().stream()
                                                .collect(Collectors.toList());
        Integer numEspecialistas = aseguradora_especialistas.size();
        Integer rand =  numEspecialistas == 1 ? 0 : randomGenerator.nextInt(numEspecialistas);
        return aseguradora_especialistas.get(rand);

    }

    // tests positivos 
    @Test
    @Transactional
    public void shouldBe() {
        Aseguradora aseguradora = findRandomAseguradora();
        Especialista especialista = findRandomEspecialistaFromAseguradora(aseguradora);        
        assertThat(aseguradora.getEspecialistas()).contains(especialista);

    }

    @Test
    @Disabled
    @Transactional
    void createEspecialista(){
        Aseguradora aseguradora = findRandomAseguradora();
        Integer aseguradora_id = aseguradora.getId();
        Integer numEspecialistasPrior = this.aseguradoraService.findById(aseguradora_id).get().getEspecialistas().size();
        Especialista especialista = createDummyEspecialista();
        Set<Aseguradora> aseguradoras = new HashSet<>();
        aseguradoras.add(aseguradora);
        especialista.setAseguradoras(aseguradoras);
        // Llamada a funcion a probar
        this.aseguradoraService.createEspecialista(especialista, aseguradora_id);
        Integer numEspecialistasPost = this.aseguradoraService.findById(aseguradora_id).get().getEspecialistas().size();
        assertThat(numEspecialistasPrior + 1).isEqualTo(numEspecialistasPost);
    }

    @Test
    @Transactional
	void deletePacienteFromAseguradora() {
        Aseguradora aseguradora = findRandomAseguradora();
        Paciente paciente = findRandomPacienteFromAseguradora(aseguradora);
        Integer aseguradora_id = aseguradora.getId();
        Integer paciente_id = paciente.getId();
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
        Aseguradora aseguradora = findRandomAseguradora();
        Especialista especialista = findRandomEspecialistaFromAseguradora(aseguradora);
        Integer aseguradora_id = aseguradora.getId();
        Integer especialista_id = especialista.getId();
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
        Aseguradora aseguradora = findRandomAseguradora();
        Especialista especialista = findRandomEspecialistaFromAseguradora(aseguradora);
        Integer aseguradora_id = aseguradora.getId();
        Integer especialista_id = especialista.getId();
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



}
