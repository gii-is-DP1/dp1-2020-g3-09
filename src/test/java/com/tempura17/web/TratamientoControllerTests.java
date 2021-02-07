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
import com.tempura17.model.Poliza;
import com.tempura17.model.Tratamiento;
import com.tempura17.service.*;

@WebMvcTest(controllers=TratamientoController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class TratamientoControllerTests {
    
    private static final int TEST_TRATAMIENTO_ID = 1;

    private static final int TEST_ACTA_ID = 1;

    private static final int TEST_POLIZA_ID = 2;

    @Autowired
    private TratamientoController tratamientoController;

    @MockBean
    private TratamientoService tratamientoService;

    @MockBean
    private ActaService actaService;

    @MockBean
    private PolizaService polizaService;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(value="spring")
    @Test
    void testAll() throws Exception{
        mockMvc.perform(get("/tratamientos"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("tratamientos"))
        .andExpect(view().name("tratamientos/listTratamientos"));
    }

    @WithMockUser(value="spring")
    @Test
    void testTratamientosForActa() throws Exception{
        mockMvc.perform(get("/tratamientos/{actaId}",TEST_ACTA_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("tratamientos"))
        .andExpect(view().name("tratamientos/listTratamientos"));
    }

    @WithMockUser(value="spring")
    @Test
	void testNewTratamientoIdGET() throws Exception {
		mockMvc.perform(get("/tratamientos/new/{actaId}/{polizaId}",TEST_ACTA_ID,TEST_POLIZA_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tratamiento"))
				.andExpect(view().name("tratamientos/tratamientosForm"));
	}

    @WithMockUser(value="spring")
    @Test
	void testNewTratamientoIdPOSTSuccess() throws Exception {
        given(this.actaService.findById(TEST_ACTA_ID)).willReturn(Optional.of(mock(Acta.class)));
        given(this.polizaService.findById(TEST_POLIZA_ID)).willReturn(Optional.of(mock(Poliza.class)));
		mockMvc.perform(post("/tratamientos/new/{actaId}/{polizaId}",TEST_ACTA_ID,TEST_POLIZA_ID)
                .with(csrf())
                .param("descripcion", "pruebadescripcion")
                .param("duracion", "4"))
                .andExpect(status().isOk())
				.andExpect(view().name("tratamientos/listTratamientos"));
	}

    @WithMockUser(value = "spring")
    @Test
    void testNewTratamientoIdPOSTHasErrors() throws Exception{
        given(this.actaService.findById(TEST_ACTA_ID)).willReturn(Optional.of(mock(Acta.class)));
        given(this.polizaService.findById(TEST_POLIZA_ID)).willReturn(Optional.of(mock(Poliza.class)));
        mockMvc.perform(post("/tratamientos/new/{actaId}/{polizaId}",TEST_ACTA_ID,TEST_POLIZA_ID)
		    .with(csrf())
            .param("descripcion", "prueba"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("tratamiento"))
            .andExpect(model().attributeHasFieldErrors("tratamiento", "descripcion"))
            .andExpect(view().name("tratamientos/tratamientosForm"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditTratamientoGET() throws Exception{
        given(this.tratamientoService.findById(TEST_TRATAMIENTO_ID)).willReturn(Optional.of(mock(Tratamiento.class)));
        mockMvc.perform(get("/tratamientos/edit/{tratamientoId}",TEST_TRATAMIENTO_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tratamiento"))
            .andExpect(view().name("tratamientos/tratamientosForm"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditTratamientoPOSTSuccess() throws Exception{
        given(this.tratamientoService.findById(TEST_TRATAMIENTO_ID)).willReturn(Optional.of(mock(Tratamiento.class)));
        mockMvc.perform(post("/tratamientos/edit/{tratamientoId}",TEST_TRATAMIENTO_ID)
            .with(csrf())
            .param("descripcion", "pruebadescripcion2")
            .param("duracion", "19"))
            .andExpect(status().isOk())
            .andExpect(view().name("tratamientos/listTratamientos"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testEditTratamientoPOSTHasErrors() throws Exception{
        given(this.tratamientoService.findById(TEST_TRATAMIENTO_ID)).willReturn(Optional.of(mock(Tratamiento.class)));
        mockMvc.perform(post("/tratamientos/edit/{tratamientoId}",TEST_TRATAMIENTO_ID)
		    .with(csrf())
            .param("descripcion", "prueba2"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("tratamiento"))
            .andExpect(model().attributeHasFieldErrors("tratamiento", "descripcion"))
            .andExpect(view().name("tratamientos/tratamientosForm"));
    }
}
