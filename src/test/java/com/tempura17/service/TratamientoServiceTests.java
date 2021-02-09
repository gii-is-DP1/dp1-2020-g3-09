package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import com.tempura17.model.Tratamiento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

class TratamientoServiceTests {

    @Autowired
    private TratamientoService tratamientoService;

    @Test
	@Transactional
	void shouldInsertTratamiento() {
		Collection<Tratamiento> tratamientos = this.tratamientoService.findAll();
		int found = tratamientos.size();

		Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion("esufsiufensoif");         
                
		this.tratamientoService.save(tratamiento);
		assertThat(this.tratamientoService.findAll().size()).isEqualTo(found+1);
    }

    @Test
	public void shouldFindTratamientoWithCorrectId() {
		Tratamiento tratamiento = this.tratamientoService.findById(1).get();
        assertThat(tratamiento.getDescripcion()).isEqualTo("esufsiufensoif");
    }   
}
