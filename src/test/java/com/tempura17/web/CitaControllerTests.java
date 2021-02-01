/*package com.tempura17.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.core.encoder.EchoEncoder;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;
import com.tempura17.model.Tipologia;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.PolizaService;

@WebMvcTest(controllers=CitaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

class CitaControllerTests {

    private static final int TEST_CITA_ID = 1;

    private static final int TEST_PACIENTE_ID = 1;

    @MockBean
    private CitaService citaService;
    
    @MockBean
    private PacienteService pacienteService;
    
    
    @MockBean
	private EspecialistaService especialistaService;

	@Autowired
    private MockMvc mockMvc;


    @BeforeEach
	void setup() {
        Cita cita = new Cita();
        cita.setId(TEST_CITA_ID);
        cita.setEspecialidad(Especialidad.ALERGOLOGIA);
        cita.setFormato(Formato.ONLINE);
        //cita.setFecha(new Date("2021-01-27 22:00:00"));
        cita.setTipo(Tipologia.ASEGURADO);
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(cita));
    }

    @WithMockUser(value = "spring")
    @Test
    void testNewCita() throws Exception{
        mockMvc.perform(get("/citas/new/{pacienteId}",TEST_PACIENTE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("cita"))
		.andExpect(view().name("citas/Citas_form"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testNewCitaSuccess() throws Exception{
        mockMvc.perform(post("/citas/new/{pacienteId}",TEST_PACIENTE_ID)
		.with(csrf())
        .param("especialidad", "ALERGOLOGIA")
        .param("formato", "ONLINE")
        .param("tipo", "ASEGURADO")
        .param("fecha", "2021-01-27 22:00:00"))
		.andExpect(status().isOk())
        .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testNewCitaHasErrors() throws Exception{
        mockMvc.perform(post("/citas/new/{pacienteId}",TEST_PACIENTE_ID)
		.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("cita"))
		.andExpect(model().attributeHasFieldErrors("cita", "formato"))
		.andExpect(view().name("citas/Citas_form"));
    }
    
}*/
