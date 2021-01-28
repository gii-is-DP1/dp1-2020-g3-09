package com.tempura17.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.tempura17.configuration.SecurityConfiguration;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.service.CalculadoraService;

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

@WebMvcTest(controllers=CalculadoraController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class CalculadoraControllerTests{

	private static final int TEST_CALC_ID = 1;

    @Autowired
	private CalculadoraController calculadoraController;

	@MockBean
	private CalculadoraService calculadoraService;

	@Autowired
    private MockMvc mockMvc;
    
	@BeforeEach
	void setup() {
	 	CalculadoraSalud calculadora = new CalculadoraSalud();
	 	calculadora.setId(TEST_CALC_ID);
	 	calculadora.setPeso(72.3);
	 	calculadora.setAltura(1.80);
        calculadora.setimc(20.0004);
	 	calculadora.setResultado("Peso normal");
	 	//given(this.calculadoraService.findById(TEST_CALC_ID)).willReturn(calculadora);
    }

    @WithMockUser(value = "spring")
    @Test
	void testNewCalculadora() throws Exception {
        mockMvc.perform(get("/calculadoras/new"))
        .andExpect(status().isOk()).andExpect(model().attributeExists("calculadora"))
		.andExpect(view().name("calculadoras/calcularIMC"));
    }
    
    @WithMockUser(value = "spring")
    @Test
	void testsaveNewCalculadoraSuccess() throws Exception {
        mockMvc.perform(post("/calculadoras/new")
		.with(csrf())
		.param("peso", "75")
		.param("altura", "1.83")
		.param("imc", "22.39")
		.param("resultado", "Peso normal"))
		.andExpect(status().isOk());
	}

    
    @WithMockUser(value = "spring")
    @Test
	void testsaveNewCalculadoraHasErrors() throws Exception {
		mockMvc.perform(post("/calculadoras/new")
		.with(csrf())
		.param("peso", "75"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("calculadora"))
		.andExpect(model().attributeHasFieldErrors("calculadora", "altura"))
		.andExpect(view().name("calculadoras/calcularIMC"));
	}
}