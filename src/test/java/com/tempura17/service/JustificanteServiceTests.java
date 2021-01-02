package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Cita;
import com.tempura17.model.Justificante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class JustificanteServiceTests {

    @Autowired
    private JustificanteService justificanteService;

    @Autowired
    private CitaService citaService;
    
    @Test
	@Transactional
    public void shouldInsertJustificante(){
        Collection<Justificante> justificantes=this.justificanteService.findAll();
        int found=justificantes.size();
        Cita cita = citaService.findAll().stream()
                                           .collect(Collectors.toList())
                                           .get(0);
        Justificante justificante = new Justificante();
        justificante.setMotivo("Trabajo");
        justificante.setCita(cita);

        this.justificanteService.save(justificante);
		assertThat(this.justificanteService.findAll().size()).isEqualTo(found+1);
    }


    @Test
    @Transactional
    public void shouldFindJustificanteByCitaId() throws Exception{
        Justificante justificante=this.justificanteService.findJustificanteByCitaId(1);
        assertThat(justificante.getId()).isEqualTo(1);
		assertThat(justificante.getMotivo()).isNotNull();
    }

    @Test
	public void shouldFindJustificanteWithCorrectId() {
		Justificante justificante = this.justificanteService.findById(1).get();
		assertThat(justificante.getMotivo()).isEqualTo("Trabajo");

    }
}
