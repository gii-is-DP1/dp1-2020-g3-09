package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PolizaServiceTests {

    /*
    @Autowired
    private AseguradoraService aseguradoraService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PolizaService polizaService;

    //@Test
    //@Transactional
    /*void deletePolizaAseguradora() {
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

        Paciente pa1 = this.pacienteService.findById(1).get();
        Poliza pNull = pa1.getPoliza();

        //Comprobamos que ambos tamaños son iguales 
        assertThat(sizeAnt).isEqualTo(sizePost);

        //Comprobamos que la poliza se borra de los pacientes
        assertThat(pNull).isNull();

    }*/

}
