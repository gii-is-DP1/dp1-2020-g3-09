/*package com.tempura17.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.service.CalculadoraService;

import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CalculadoraControllerTests{

    @Autowired
	private CalculadoraController calculadoraController;

	@MockBean
	private CalculadoraService calculadoraServ;

	@Autowired
    private MockMvc mockMvc;
    
    @BeforeEach
	void setup() {
        CalculadoraSalud calculadora = new CalculadoraSalud();
		calculadora.setPeso(77.5);
		calculadora.setAltura(1.85);
        calculadora.setimc(28.75);
        calculadora.setResultado("Peso normal");
    }

    @WithMockUser(value = "spring")
    @Test
	void testCreacionCalc() throws Exception {
        mockMvc.perform(get("/calculadoras/new"))
        .andExpect(status().isOk()).andExpect(model().attributeExists("calculadora"))
		.andExpect(view().name("calculadoras/calcularIMC"));
    }
    
    @WithMockUser(value = "spring")
    @Test
	void testCreacionCalcSuccess() throws Exception {
        mockMvc.perform(post("/calculadoras/new")
        .param("peso", 75).param("altura", 1.83)
		.with(csrf())
		.param("imc", 22.39)
		.param("peso", "Peso normal")
		.andExpect(status().is3xxRedirection()));
    }
    
    @WithMockUser(value = "spring")
    @Test
	void testCreacionCalcHasErrors() throws Exception {
		mockMvc.perform(post("/calculadoras/new")
		.with(csrf())
		.param("peso", 75)
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("calculadora"))
		.andExpect(model().attributeHasFieldErrors("calculadora", "altura"))
		.andExpect(view().name("calculadoras/calcularIMC")));
	}
}*/