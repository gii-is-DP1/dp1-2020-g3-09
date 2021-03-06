package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Poliza;
import com.tempura17.model.Sexo;
import com.tempura17.model.Paciente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PolizaServiceTests {

    
    @Autowired
    private AseguradoraService aseguradoraService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PolizaService polizaService;

    @Test
    @Transactional
    void deletePolizaAseguradora() {
        Poliza p1 = this.polizaService.findById(1).get();
        Aseguradora a1 = this.aseguradoraService.findById(1).get();
        Set<Poliza> polizasAnt = a1.getPolizas();
        //cogemos el tamaño inicial de las polizas de la aseguradora
        //que será igual 2
        Integer sizeAnt = polizasAnt.size();

        this.polizaService.deletePoliza(p1.getId());

        Aseguradora a2 = this.aseguradoraService.findById(1).get();
        //cogemos el tamaño actualizado de las polizas de la aseguradora
        //que será igual a 1
        Set<Poliza> polizasPost = a2.getPolizas();
        Integer sizePost = polizasPost.size()+1;

        Paciente paciente = new Paciente();
        paciente.setDni("dni");
        paciente.setSexo(Sexo.MASCULINO);
        paciente.setEmail("correo@correo.com");
        Poliza pNull = paciente.getPoliza();

        //Comprobamos que ambos tamaños son iguales 
        assertThat(sizeAnt).isEqualTo(sizePost);

        //Comprobamos que la poliza se borra de los pacientes
        assertThat(pNull).isNull();

    }

    @Test
    @Transactional
    void deletePacienteDePoliza(){
        Poliza p1 = this.polizaService.findById(1).get();
        Set<Paciente> pacientesAnt = p1.getPacientes();
        Integer sizeAnt = pacientesAnt.size();
        Paciente paciente = this.pacienteService.findById(1).get();
        p1.getPacientes().remove(paciente);
        Poliza p2 = this.polizaService.findById(1).get();
        Set<Paciente> pacientesPost = p2.getPacientes();
        Integer sizePost = pacientesPost.size()+1;
        assertThat(sizeAnt).isEqualTo(sizePost);

    }
}
