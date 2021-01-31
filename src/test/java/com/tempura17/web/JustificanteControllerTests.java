package com.tempura17.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.tempura17.configuration.SecurityConfiguration;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Cita;
import com.tempura17.model.Justificante;
import com.tempura17.service.CalculadoraService;
import com.tempura17.service.CitaService;
import com.tempura17.service.JustificanteService;

import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
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

@WebMvcTest(controllers=JustificanteController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class JustificanteControllerTests{

    private static final int TEST_JUST_ID = 1;
    private static final int TEST_CITA_ID = 1;


	@MockBean
    private JustificanteService justificanteService;
    
    @MockBean
	private CitaService citaService;

	@Autowired
    private MockMvc mockMvc;
    
	@BeforeEach
	void setup() {
		Justificante justificante = new Justificante();
		justificante.setId(TEST_JUST_ID);
        justificante.setMotivo("TRABAJO");
		given(this.justificanteService.findById(TEST_JUST_ID)).willReturn(Optional.of(justificante));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testNewJustificante() throws Exception {
        mockMvc.perform(get("/justificantes/new/{citaId}",TEST_CITA_ID))
        .andExpect(status().isOk()).andExpect(model().attributeExists("justificante"))
		.andExpect(view().name("justificantes/crearJustificante"));
	}
	

	@WithMockUser(value = "spring")
	@Test
	void testsaveNewJustificanteSuccess() throws Exception {
        mockMvc.perform(post("/justificantes/new/{citaId}",TEST_CITA_ID)
		.with(csrf())
		.param("motivo", "TRABAJO"))
		.andExpect(status().isOk())
		.andExpect(view().name("justificantes/mostrarJustificantes"));
	}


	@WithMockUser(value = "spring")
    @Test
	void testsaveNewJustificanteHasErrors() throws Exception {
		mockMvc.perform(post("/justificantes/new/{citaId}",TEST_CITA_ID)
		.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("justificante"))
		.andExpect(model().attributeHasFieldErrors("justificante", "motivo"))
		.andExpect(view().name("justificantes/crearJustificante"));
	}
}
