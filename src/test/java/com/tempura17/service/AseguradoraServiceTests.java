package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

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

    @Test
	void deletePacienteAseguradora() {
        Paciente p1 = pacienteService.findById(1).get();
        //Integer i1 = p1.getAseguradora().getId();   escenario negativo
        this.aseguradoraService.deletePaciente(p1.getAseguradora().getId(), p1.getId());
        
        

        p1 = pacienteService.findById(1).get();
        assertThat(p1.getAseguradora()).isEqualTo(null);
        //assertThat(p1.getAseguradora()).isEqualTo(this.aseguradoraService.findById(i1)); escenario negativo
    
    }
}
