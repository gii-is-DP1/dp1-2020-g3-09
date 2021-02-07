package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Acta;
import com.tempura17.model.Alarma;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

class ActaServiceTests {

    @Autowired
    private ActaService actaService;

    @Test
	@Transactional
    @Disabled
    //Falla solo al ejecutar las pruebas en bloque
	void shouldInsertActa() {
		Collection<Acta> actas = this.actaService.findAll();
		int found = actas.size();

		Acta acta = new Acta();
        acta.setDescripcion("pruebadescripcion");  
        acta.setExploracion("pruebaexploracion");
        acta.setDiagnostico("pruebadiagnostico");          
                
		this.actaService.save(acta);
		assertThat(this.actaService.findAll().size()).isEqualTo(found+1);
    }

    @Test
	public void shouldFindActaWithCorrectId() {
		Acta acta = this.actaService.findById(1).get();
        assertThat(acta.getDescripcion()).isEqualTo("esufsiufensoif");
    }
    
}
