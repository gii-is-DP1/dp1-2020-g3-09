package com.tempura17.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.tempura17.model.Alarma;
import com.tempura17.service.AlarmaService;
import com.tempura17.service.CitaService;

@WebMvcTest(controllers=AlarmaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

class AlarmaControllerTests {

    private static final int TEST_ALARMA_ID = 1;

    private static final int TEST_CITA_ID = 1;

    @MockBean
    private AlarmaService alarmaService;
    
    @MockBean
	private CitaService citaService;

	@Autowired
    private MockMvc mockMvc;


    @BeforeEach
	void setup() {
		Alarma alarma = new Alarma();
		alarma.setId(TEST_ALARMA_ID);
        alarma.setDias(13);
		given(this.alarmaService.findById(TEST_ALARMA_ID)).willReturn(Optional.of(alarma));
    }


    @WithMockUser(value = "spring")
    @Test
	void testNewAlarma() throws Exception {
        mockMvc.perform(get("/alarmas/new/{citaId}",TEST_CITA_ID))
        .andExpect(status().isOk()).andExpect(model().attributeExists("alarma"))
		.andExpect(view().name("alarmas/crearAlarma"));
    }


    @WithMockUser(value = "spring")
	@Test
	void testsaveNewAlarmaSuccess() throws Exception {
        mockMvc.perform(post("/alarmas/new/{citaId}",TEST_CITA_ID)
		.with(csrf())
        .param("dias", "13"))
		.andExpect(status().isOk());
		//.andExpect(view().name("alarmas/misAlarmas"));
    }


    @WithMockUser(value = "spring")
    @Test
	void testsaveNewAlarmaHasErrors() throws Exception {
		mockMvc.perform(post("/alarmas/new/{citaId}",TEST_CITA_ID)
        .with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("alarma"))
		.andExpect(model().attributeHasFieldErrors("alarma", "dias"))
		.andExpect(view().name("alarmas/crearAlarma"));
    }


    @WithMockUser(value = "spring")
    @Test
    void testdeleteAlarma() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .delete("alarmas/{alarmaId}/delete",TEST_ALARMA_ID)
        .with(csrf()))
        .andExpect(status().isOk());
    }
    
}
