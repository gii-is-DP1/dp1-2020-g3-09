package com.tempura17.web;

import org.assertj.core.util.Lists;
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

import java.util.Optional;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Paciente;
import com.tempura17.model.Poliza;
import com.tempura17.model.Tratamiento;
import com.tempura17.service.*;

@WebMvcTest(controllers=PolizaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class PolizaControllerTests {

    private static final int TEST_POLIZA_ID = 1;

    private static final int TEST_ASEGURADORA_ID = 1;

    private static final int TEST_PACIENTE_ID = 1;

    @Autowired
    private PolizaController polizaController;

    @MockBean
    private PolizaService polizaService;

    @MockBean
    private AseguradoraService aseguradoraService;

    @MockBean
    private TratamientoService tratamientoService;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(value="spring")
    @Test
    void testAll() throws Exception{
        mockMvc.perform(get("/polizas"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("polizas"))
        .andExpect(view().name("polizas/Polizas_list"));
    }

    @WithMockUser(value="spring")
    @Test
	void testNewPolizaGET() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
		mockMvc.perform(get("/polizas/new/{aseguradoraId}",TEST_ASEGURADORA_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("poliza"))
                .andExpect(model().attributeExists("aseguradora"))
				.andExpect(view().name("polizas/Polizas_form"));
	}

    @WithMockUser(value="spring")
    @Test
    @Disabled
	void testNewPolizaPOSTsuccess() throws Exception {
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
		mockMvc.perform(post("/polizas/new/{aseguradoraId}",TEST_ASEGURADORA_ID)
                .with(csrf())
                .param("name","Nombre de poliza")
                .param("precio", "1000.50")
                .param("cobertura", "TOTAL"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/aseguradoras/{aseguradoraId}/perfil"));
	}
    
    @WithMockUser(value = "spring")
    @Test
    @Disabled
    void testNewPolizaPOSTHasErrors() throws Exception{
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
        mockMvc.perform(post("/polizas/new/{aseguradoraId}",TEST_ASEGURADORA_ID)
		    .with(csrf())
            .param("precio",""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("poliza"))
            .andExpect(model().attributeHasFieldErrors("poliza", "precio"))
            .andExpect(view().name("redirect:/aseguradoras/{aseguradoraId}/perfil"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditPolizaGET() throws Exception{
        given(this.polizaService.findById(TEST_POLIZA_ID)).willReturn(Optional.of(mock(Poliza.class)));
        given(this.pacienteService.findAll()).willReturn(Lists.newArrayList(mock(Paciente.class)));
        given(this.tratamientoService.findAll()).willReturn(Lists.newArrayList(mock(Tratamiento.class)));

        mockMvc.perform(get("/polizas/edit/{aseguradoraId}/{polizaId}", TEST_ASEGURADORA_ID,TEST_POLIZA_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("poliza"))
            .andExpect(model().attributeExists("tratamientos"))
            .andExpect(model().attributeExists("pacientes"))
            .andExpect(view().name("polizas/Polizas_edit"));
    }

    @WithMockUser(value = "spring")
	@Test
    @Disabled
	void testEditPolizaPOSTSuccess() throws Exception {
        given(this.polizaService.findById(TEST_POLIZA_ID)).willReturn(Optional.of(mock(Poliza.class)));
        given(this.aseguradoraService.findById(TEST_ASEGURADORA_ID)).willReturn(Optional.of(mock(Aseguradora.class)));
		mockMvc.perform(post("/polizas/edit/{aseguradoraId}/{polizaId}", TEST_ASEGURADORA_ID,TEST_POLIZA_ID)
							.with(csrf())
							.param("precio", "500.50")
                            .param("cobertura", "PARCIAL"))
				            .andExpect(status().isOk())
				            .andExpect(view().name("redirect:/aseguradoras/{aseguradoraId}/perfil"));
    }
}
