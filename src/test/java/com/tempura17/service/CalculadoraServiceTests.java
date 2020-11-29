package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import com.tempura17.model.CalculadoraSalud;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CalculadoraServiceTests {

    @Autowired
    private CalculadoraService calculadoraServ;

    @Autowired
    private PacienteService pacienteServ;



    @Test

	@Transactional
	void shouldInsertCalculadora() {
		Collection<CalculadoraSalud> calculadoras = this.calculadoraServ.findAll();
		int found = calculadoras.size();

		CalculadoraSalud calculadora = new CalculadoraSalud();
        calculadora.setPeso("75.3");
        calculadora.setAltura("1.84");
        calculadora.setimc(27.54);
        calculadora.setResultado("Peso normal");              
                
		this.calculadoraServ.save(calculadora);
		assertThat(this.calculadoraServ.findAll().size()).isEqualTo(found+1);
    }
    

    @Test
	void shouldFindCalculadoraByPacienteId() throws Exception {
        CalculadoraSalud calculadora = this.pacienteServ.findCalculadoraByPacienteId(1);
        assertThat(calculadora.getId()).isEqualTo(1);
		assertThat(calculadora.getPeso()).isNotNull();
		assertThat(calculadora.getAltura()).isNotNull();
    }
    

    
    
}