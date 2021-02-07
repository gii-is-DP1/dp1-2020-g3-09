package com.tempura17.web;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;

import java.util.Optional;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Especialista;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.PolizaService;

@WebMvcTest(controllers=AseguradoraController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

class AseguradoraControllerTests {

    private static final int TEST_ASEGURADORA_ID = 1;

    @Autowired
	private AseguradoraController aseguradoraController;

    @MockBean
    private AseguradoraService aseguradoraService;
    
    @MockBean
    private PacienteService pacienteService;
    
    @MockBean
    private PolizaService polizaService;
    
    @MockBean
	private EspecialistaService especialistaService;

	@Autowired
    private MockMvc mockMvc;


    @WithMockUser(value = "spring")
    @Test
    void testNewAseguradora() throws Exception{
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));
        mockMvc.perform(get("/aseguradoras/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("especialistas"))
        .andExpect(model().attributeExists("aseguradora"))
		.andExpect(view().name("aseguradoras/Aseguradoras_form"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testNewAseguradoraSuccess() throws Exception{
        mockMvc.perform(post("/aseguradoras/new")
		.with(csrf())
        .param("nombre", "Caser"))
		.andExpect(status().isOk())
        .andExpect(view().name("aseguradoras/Aseguradoras_list"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testNewAseguradoraHasErrors() throws Exception{
        mockMvc.perform(post("/aseguradoras/new")
		.with(csrf())
        .param("nombre", ""))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("aseguradora"))
		.andExpect(model().attributeHasFieldErrors("aseguradora", "nombre"))
		.andExpect(view().name("aseguradoras/Aseguradoras_form"));
    }
    

    @WithMockUser(value = "spring")
	@Test
	void testAseguradoraPerfil() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
        given(mock(Aseguradora.class).getEspecialistas()).willReturn(Sets.newHashSet());

        mockMvc.perform(get("/aseguradoras/{aseguradoraId}/perfil", TEST_ASEGURADORA_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("aseguradora"))
                .andExpect(model().attributeExists("especialistas"))
				.andExpect(view().name("aseguradoras/Aseguradoras_perfil"));
    }

    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateAseguradoraFormSuccess() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
        given(mock(Aseguradora.class).getEspecialistas()).willReturn(Sets.newHashSet());

        mockMvc.perform(get("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("aseguradora"))
                .andExpect(model().attributeExists("especialistas"))
				.andExpect(view().name("aseguradoras/Aseguradoras_edit"));
    }


    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAseguradoraFormSuccess() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
        given(mock(Aseguradora.class).getEspecialistas()).willReturn(Sets.newHashSet());

        mockMvc.perform(post("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)
                .with(csrf())
                .param("nombre", "Nuevo nombre"))
                .andExpect(status().isOk())
				.andExpect(view().name("aseguradoras/Aseguradoras_list"));
    }
                

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAseguradoraFormHasErrors() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));

		mockMvc.perform(post("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)
				.with(csrf())
                .param("nombre", ""))
				.andExpect(model().attributeHasErrors("aseguradora"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("aseguradora", "nombre"))
				.andExpect(view().name("aseguradoras/Aseguradoras_edit"));
	}
}
