package com.tempura17.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.web.servlet.MockMvc;

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

import com.tempura17.model.Aseguradora;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;
import com.tempura17.service.PolizaService;

@WebMvcTest(controllers=AseguradoraController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

class AseguradoraControllerTests {

    private static final int TEST_ASEGURADORA_ID = 1;

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


    @BeforeEach
	void setup() {
        Aseguradora aseguradora = new Aseguradora();
        aseguradora.setId(TEST_ASEGURADORA_ID);
        aseguradora.setNombre("Caser");
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(aseguradora));
    }

    @WithMockUser(value = "spring")
    @Test
    @Disabled
    void testNewAseguradora() throws Exception{
        mockMvc.perform(get("/aseguradoras/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("aseguradora"))
		.andExpect(view().name("aseguradoras/Aseguradora_form"));
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
		.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("aseguradora"))
		.andExpect(model().attributeHasFieldErrors("aseguradora", "nombre"))
		.andExpect(view().name("aseguradoras/Aseguradora_form"));
    }
    

    /*@WithMockUser(value = "spring")
	@Test
	void testInitEditAseguradora() throws Exception {
        mockMvc.perform(get("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("aseguradora"))
				.andExpect(model().attribute("aseguradora", hasProperty("nombre", is("Caser"))))
				.andExpect(view().name("aseguradoras/Aseguradoras_edit"));
    }*/

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAseguradoraFormSuccess() throws Exception {
		mockMvc.perform(post("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)
							.with(csrf())
							.param("nombre", "NoCaser"))
				.andExpect(status().isOk())
				.andExpect(view().name("aseguradoras/Aseguradoras_list"));
    }

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAseguradoraFormHasErrors() throws Exception {
		mockMvc.perform(post("/aseguradoras/{aseguradoraId}/edit", TEST_ASEGURADORA_ID)
							.with(csrf()))
				.andExpect(model().attributeHasErrors("aseguradora"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("aseguradora", "nombre"))
				.andExpect(view().name("aseguradoras/Aseguradoras_edit"));
	}
}
