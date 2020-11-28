/*package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import com.tempura17.model.CalculadoraSalud;
import com.tempura17.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CitaServiceTests {

    @Autowired
    private CalculadoraService calculadoraServ;


    @Test
	@Transactional
	public void shouldInsertCalculadora() {
		Collection<CalculadoraSalud> calculadoras = this.calculadoraServ.findAll();
		int found = calculadoras.size();

		CalculadoraSalud calculadora = new CalculadoraSalud();
        calculadora.setPeso(75.3);
        calculadora.setAltura(1.84);
        calculadora.setimc(27.54);
        calculadora.setResultado("Peso normal");              
                
		this.calculadoraServ.save(calculadora);
		assertThat(this.calculadoraServ.findAll().size()).isEqualTo(found+1);
    }
    

    //Me faltará hacer el test del findByPacienteId
    
}*/