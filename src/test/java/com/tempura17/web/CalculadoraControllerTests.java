package com.tempura17.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.service.CalculadoraService;
import com.tempura17.service.PacienteService;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class CalculadoraControllerTests{

	private static final int TEST_CALC_ID = 1;

	private static final int TEST_PACIENTE_ID =1;


	@Autowired
	private CalculadoraService calculadoraService;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
    private MockMvc mockMvc;
    
	@BeforeEach
	void setup() {}

    @WithMockUser(value = "spring")
	@Test
	void testNewCalculadora() throws Exception {
        mockMvc.perform(get("/calculadoras/new/{pacienteId}",TEST_PACIENTE_ID))
        .andExpect(status().isOk()).andExpect(model().attributeExists("calculadora"))
		.andExpect(view().name("calculadoras/calcularIMC"));
    }
    
    @WithMockUser(value = "spring")
	@Test
	void testsaveNewCalculadoraSuccess() throws Exception {
        mockMvc.perform(post("/calculadoras/new/{pacienteId}",TEST_PACIENTE_ID)
		.with(csrf())
		.param("peso", "72.3")
		.param("altura", "1.80")
		.param("imc", "20.0004")
		.param("resultado", "Peso normal"))
		.andExpect(status().isOk())
		.andExpect(view().name("calculadoras/calculadoraListing"));
	}

	@WithMockUser(value="spring")
	@Test
	void testListCalculadoras() throws Exception {
        mockMvc.perform(get("/calculadoras")
		.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("calculadoras"))
		.andExpect(view().name("calculadoras/calculadoraListing"));
	}

    
    @WithMockUser(value = "spring")
	@Test
	void testsaveNewCalculadoraHasErrors() throws Exception {
		mockMvc.perform(post("/calculadoras/new/{pacienteId}",TEST_PACIENTE_ID)
		.with(csrf())
		.param("peso","72.3"))
		.andExpect(status().isOk())
		.andExpect(view().name("calculadoras/calcularIMC"));
	}


	
}