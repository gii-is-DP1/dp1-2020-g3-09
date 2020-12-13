package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

public class AseguradoraServiceTests {

    @Autowired
    private AseguradoraService aseguradoraService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EspecialistaService especialistaService;

    @Test
    @Transactional
	void deletePacienteAseguradora() {
        Paciente p1 = pacienteService.findById(1).get();
        Aseguradora a1 = pacienteService.findById(1).get().getAseguradora();
        Set<Paciente> sa = new HashSet<>(a1.getPacientes());
        //Integer i1 = p1.getAseguradora().getId();   escenario negativo
        this.aseguradoraService.deletePaciente(p1.getAseguradora().getId(), p1.getId());
        
        
        //Se comprueba que el paciente no tiene la aseguradora anterior
        Paciente p2 = this.pacienteService.findById(1).get();
        assertThat(p2.getAseguradora()).isEqualTo(null);
        

        //Se compueba que la aseguradora no tiene al paciente anterior asociado
        Aseguradora a2 = this.aseguradoraService.findById(a1.getId()).get();
        sa.remove(p1);
        assertThat(a2.getPacientes()).containsAll(sa);
        //assertThat(p1.getAseguradora()).isEqualTo(this.aseguradoraService.findById(i1)); escenario negativo
    
    }

    @Test
    @Transactional
    void deleteEspecialistaAseguradora() {
        Especialista e1 = this.especialistaService.findById(2).get();
        Integer es1 = e1.getAseguradoras().size();

        Aseguradora a1 = this.aseguradoraService.findById(1).get();
        Integer as1 = a1.getEspecialistas().size();

        this.aseguradoraService.deleteEspecialista(a1.getId(), e1.getId());

        //Comprobamos que el especialista ha eliminado a la aseguradora
        Especialista e2 = this.especialistaService.findById(2).get();
        assertThat(es1).isEqualTo(e2.getAseguradoras().size()+1);

        //Comprobamos que la aseguradora ha eliminado al especialista
        Aseguradora a2 = this.aseguradoraService.findById(2).get();
        assertThat(as1).isEqualTo(a2.getEspecialistas().size()+1);
    }

    @Test
    @Transactional
    void editEspecialidad() {
        Especialista e1 = this.especialistaService.findById(1).get();
        this.aseguradoraService.editEspecialidad(e1.getId(), "MEDICINA_INTERNA");
        
        Especialista em1 = this.especialistaService.findById(1).get();
        assertThat(em1.getEspecialidad()).isEqualTo(Especialidad.MEDICINA_INTERNA);


        Aseguradora a1 = this.aseguradoraService.findById(1).get();
        Especialista eee = a1.getEspecialistas().stream()
                                       .collect(Collectors.toList())
                                       .get(0);
        Especialidad esp1 = eee.getEspecialidad();

        //Se comprueba que el especialista modificado también está modificado en su aseguradora
        assertThat(esp1).isEqualTo(Especialidad.MEDICINA_INTERNA);
    }


}
