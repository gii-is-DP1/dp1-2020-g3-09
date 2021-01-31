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
    private CalculadoraService calculadoraService;




    @Test
	@Transactional
	void shouldInsertCalculadora() {
		Collection<CalculadoraSalud> calculadoras = this.calculadoraService.findAll();
		int found = calculadoras.size();

		CalculadoraSalud calculadora = new CalculadoraSalud();
        calculadora.setPeso(75.3);
        calculadora.setAltura(1.84);
        calculadora.setimc(27.54);
        calculadora.setResultado("Peso normal");              
                
		this.calculadoraService.save(calculadora);
		assertThat(this.calculadoraService.findAll().size()).isEqualTo(found+1);
    }
    

    @Test
	void shouldFindCalculadoraByPacienteId() throws Exception {
        CalculadoraSalud calculadora = this.calculadoraService.findByPacienteId(1);
        assertThat(calculadora.getId()).isEqualTo(1);
		assertThat(calculadora.getPeso()).isNotNull();
		assertThat(calculadora.getAltura()).isNotNull();
    }    

    // @Test
	// public void shouldFindCalculadoraWithCorrectId() {
	// 	CalculadoraSalud calculadora = this.calculadoraService.findCalculadoraById(1);
	// 	assertThat(calculadora.getAltura()).isEqualTo(1.80);

    // }
}