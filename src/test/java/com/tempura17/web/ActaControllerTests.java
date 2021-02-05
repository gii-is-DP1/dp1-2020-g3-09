package com.tempura17.web;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.tempura17.service.ActaService;
import com.tempura17.service.AuthoritiesService;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

class ActaControllerTests {

    private static final int TEST_ACTA_ID = 1;

    private static final int TEST_CITA_ID = 1;

    private static final int TEST_ESPECIALISTA_ID = 1;

    @Autowired
    private ActaService actaService;

    @Autowired
    private EspecialistaService especialistaService;
    
    @Autowired
    private CitaService citaService;

	@Autowired
    private MockMvc mockMvc;

	

    @BeforeEach
	void setup() {}
    

    @WithMockUser(value = "spring")
    @Test
	void testNewActa() throws Exception {
        mockMvc.perform(get("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID))
        .andExpect(status().isOk()).andExpect(model().attributeExists("acta"))
		.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testsaveNewActaSuccess() throws Exception {
        mockMvc.perform(post("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID)
		.with(csrf())
        .param("descripcion", "esufsiufensoif")
        .param("exploracion", "esufsiufensoif")
		.param("diagnostico", "esufsiufensoif"))
		.andExpect(status().isOk())
        .andExpect(view().name("actas/listActas"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testsaveNewActaHasErrors() throws Exception {
		mockMvc.perform(post("/actas/new/{citaId}/{especialistaId}",TEST_CITA_ID,TEST_ESPECIALISTA_ID)
		.with(csrf())
		.param("descripcion","esufsiufensoif"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("acta","exploracion"))
		.andExpect(model().attributeHasFieldErrors("acta","diagnostico"))
		.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testInitEditActa() throws Exception {
		mockMvc.perform(get("/actas/{actaId}/edit", TEST_ACTA_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("acta"))
				.andExpect(model().attribute("acta", hasProperty("descripcion", is("esufsiufensoif"))))
				.andExpect(model().attribute("acta", hasProperty("exploracion", is("esufsiufensoif"))))
				.andExpect(model().attribute("acta", hasProperty("diagnostico", is("esufsiufensoif"))))
				.andExpect(view().name("actas/actasForm"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateActaFormSuccess() throws Exception {
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
		mockMvc.perform(post("/actas/{actaId}/edit", TEST_ACTA_ID)
							.with(csrf())
							.param("diagnostico", "pruebadiagnostico3")
							.param("descripcion", "pruebadescripcion1"))
				.andExpect(model().attributeHasErrors("acta"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("acta", "exploracion"))
				.andExpect(view().name("actas/actasForm"));
	}
    
}
