package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import com.tempura17.model.Alarma;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AlarmaServiceTests {

    @Autowired
    private AlarmaService alarmaService;

    @Test
	@Transactional
	void shouldInsertAlarma() {
		Collection<Alarma> alarmas = this.alarmaService.findAll();
		int found = alarmas.size();

		Alarma alarma = new Alarma();
        alarma.setDias(7);             
                
		this.alarmaService.save(alarma);
		assertThat(this.alarmaService.findAll().size()).isEqualTo(found+1);
    }

    @Test
	public void shouldFindAlarmaWithCorrectId() {
		Alarma alarma = this.alarmaService.findById(1).get();
        assertThat(alarma.getDias()).isEqualTo(13);
    }
}
