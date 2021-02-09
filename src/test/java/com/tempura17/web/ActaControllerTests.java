package com.tempura17.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

import com.tempura17.model.Acta;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;
import com.tempura17.service.ActaService;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;

@WebMvcTest(controllers=ActaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

class ActaControllerTests {

    private static final int TEST_ACTA_ID = 1;

    private static final int TEST_CITA_ID = 1;

    private static final int TEST_ESPECIALISTA_ID = 1;

    @Autowired
	private ActaController actaController;

	@MockBean
    private ActaService actaService;

    @MockBean
    private EspecialistaService especialistaService;
    
    @MockBean
    private CitaService citaService;

	@Autowired
    private MockMvc mockMvc;

	private Acta acta;



	@WithMockUser(value = "spring")
    @Test
	void all() throws Exception {
        mockMvc.perform(get("/actas"))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("actas"))
		.andExpect(view().name("actas/listActas"));
    }

    @WithMockUser(value = "spring")
    @Test
	void testNewActa() throws Exception {
        mockMvc.perform(get("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("acta"))
		.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testsaveNewActaSuccess() throws Exception {
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
		given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
	
		mockMvc.perform(post("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID)
		.with(csrf())
        .param("descripcion", "descripcion")
        .param("exploracion", "exploracion cualesquiera")
		.param("diagnostico", "diagnostico indeterminado"))
		.andExpect(status().isOk())
        .andExpect(view().name("actas/listActas"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testsaveNewActaHasErrors() throws Exception {
		given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
		given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
		acta = mock(Acta.class);

		mockMvc.perform(post("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID)
		.with(csrf())
		.param("descripcion", "")
        .param("exploracion", "")
		.param("diagnostico", ""))
		.andExpect(model().attributeHasErrors("acta"))
		.andExpect(model().attributeHasFieldErrors("acta", "descripcion"))
		.andExpect(model().attributeHasFieldErrors("acta", "exploracion"))
		.andExpect(model().attributeHasFieldErrors("acta", "diagnostico"))
		.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testInitEditActa() throws Exception {
		given(this.actaService.findById(TEST_ACTA_ID)).willReturn(Optional.of(mock(Acta.class)));

		mockMvc.perform(get("/actas/{actaId}/edit", TEST_ACTA_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("acta"))
				.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateActaFormSuccess() throws Exception {
		given(this.actaService.findById(TEST_ACTA_ID)).willReturn(Optional.of(mock(Acta.class)));
		given(mock(Acta.class).getEspecialista()).willReturn(mock(Especialista.class));
		mockMvc.perform(post("/actas/{actaId}/edit", TEST_ACTA_ID)
							.with(csrf())
							.param("descripcion", "pruebadescripcion1")
							.param("exploracion", "pruebaexploracion2")
							.param("diagnostico", "pruebadiagnostico3"))
				.andExpect(status().isOk())
				.andExpect(view().name("actas/listActas"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateActaFormHasErrors() throws Exception {
		given(this.actaService.findById(TEST_ACTA_ID)).willReturn(Optional.of(mock(Acta.class)));
		mockMvc.perform(post("/actas/{actaId}/edit", TEST_ACTA_ID)
				.with(csrf())
				.param("descripcion", "pruebadescripcion1")
				.param("exploracion", "")
				.param("diagnostico", ""))
				.andExpect(model().attributeHasErrors("acta"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("acta", "exploracion"))
				.andExpect(model().attributeHasFieldErrors("acta", "diagnostico"))
				.andExpect(view().name("actas/actasForm"));
	}
    
}
