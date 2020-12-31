package com.tempura17.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tipologia;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CitaServiceTests {
    
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private PacienteService pacienteService;


    @Test
	void citaExistente() {
        // Objeto cita_n, donde n es el ID que se conoce una cita existente tiene asociado
        Cita cita_1 = this.citaService.findAll().stream()
                                      .collect(Collectors.toList())
                                      .get(0);
		System.out.println(cita_1);
    }
    
    @Test
	void shouldFindCitaWithCorrectId() {
		Cita cita_1 = this.citaService.findById(1).get();
		assertThat(cita_1.getEspecialidad()).isEqualTo(Especialidad.MEDICINA_GENERAL);

    }

    @Test
	@Transactional
	public void shouldInsert() {
		Collection<Cita> citas = this.citaService.findAll();
        int found = citas.size();
        Paciente paciente = pacienteService.findAll().stream()
                                           .collect(Collectors.toList())
                                           .get(0);

        Cita cita = new Cita();
        cita.setFormato(Formato.ONLINE);
        cita.setTipo(Tipologia.ASEGURADO);
        cita.setEspecialidad(Especialidad.PEDIATRIA);
        cita.setEspecialista(null);
        cita.setFecha(null); 
        cita.setPaciente(paciente);
        
        this.citaService.save(cita);
        assertThat(this.citaService.findAll().size()).isEqualTo(found+1);

    }
    

    @Test
	@Transactional
	public void shouldUpdate() {
		Cita cita = this.citaService.findById(1).get();
		Especialidad oldEspecialidad = cita.getEspecialidad();

		Especialidad newEspecialidad = Especialidad.CARDIOLOGIA;
		cita.setEspecialidad(newEspecialidad);
        this.citaService.save(cita);
        assertThat(newEspecialidad).isNotEqualTo(oldEspecialidad);

		cita = this.citaService.findById(1).get();
		assertThat(cita.getEspecialidad()).isEqualTo(newEspecialidad);
	}
    
    /*          CUSTOM QUERY
    @Test
	void shouldFindPacienteByCitaId() throws Exception {
        Paciente paciente = this.citaService.findPacienteByCitaId(1);
        assertThat(paciente.getId()).isEqualTo(1);
		assertThat(paciente.getDni()).isNotNull();
		assertThat(paciente.getEdad()).isEqualTo(20);
    }
    */

}
