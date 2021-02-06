package com.tempura17.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.configuration.SecurityConfiguration;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.encoder.EchoEncoder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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



import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;
import com.tempura17.model.Paciente;
import com.tempura17.model.Sexo;
import com.tempura17.model.Tipologia;
import com.tempura17.service.*;

@WebMvcTest(controllers=PacienteController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class PacienteControllerTests {

    private static final int TEST_PACIENTE_ID = 1;

    @Autowired
    private PacienteController pacienteController;

    @MockBean
    private PacienteService pacienteService;
    
    @MockBean
    private	 AseguradoraService aseguradoraService;
    
    @MockBean
    private	 CitaService citaService;

    @MockBean
	private  CalculadoraService calculadoraService;

    @MockBean
	private  PolizaService polizaService;

    @MockBean
    private	 UserService userService;

	@Autowired
    private MockMvc mockMvc;


	@WithMockUser(value="spring")
    @Test
    void testAll() throws Exception{
        mockMvc.perform(get("/pacientes"))
        .andExpect(status().isOk())
        .andExpect(status().isOk()).andExpect(model().attributeExists("pacientes"))
        .andExpect(view().name("pacientes/Pacientes_list"));
    }

    @WithMockUser(value="spring")
    @Test
    void testPacientePerfil() throws Exception{
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
        mockMvc.perform(get("/pacientes/{pacienteId}/perfil", TEST_PACIENTE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("paciente"))
        .andExpect(model().attributeExists("citas"))
        .andExpect(view().name("pacientes/Pacientes_perfil"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditPacienteGET() throws Exception{
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
        mockMvc.perform(get("/pacientes/{pacienteId}/edit", TEST_PACIENTE_ID))
            .andExpect(model().attributeExists("paciente"))
            .andExpect(model().attribute("paciente", hasProperty("dni", is("00000000A"))))
            .andExpect(model().attribute("paciente", hasProperty("email", is("cosas@gmail.com"))))
            .andExpect(model().attribute("paciente", hasProperty("sexo", is(Sexo.MASCULINO))))
            .andExpect(model().attribute("paciente", hasProperty("direccion", is("micasa"))))
            .andExpect(view().name("pacientes/Pacientes_edit"));

    }

    @WithMockUser(value="spring")
    @Test
    void testEditPacientePOSTSucces() throws Exception{
        mockMvc.perform(post("/pacientes/{pacienteId}/edit", TEST_PACIENTE_ID)
            .with(csrf())
            .param("firstName", "Rodri")
            .param("dni", "00000000Z")
            .param("email", "pruebas@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(view().name("pacientes/Pacientes_list"));
    }

    @WithMockUser(value = "spring")
	@Test
	void testEditPacientePOSTHasErrors() throws Exception {
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
		mockMvc.perform(post("/pacientes/{pacienteId}/edit", TEST_PACIENTE_ID)
							.with(csrf())
							.param("firstName", "Rodri")
							.param("dni", "00000000Z"))
				.andExpect(model().attributeHasErrors("paciente"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("paciente", "email"))
				.andExpect(view().name("pacientes/Pacientes_list"));
	}
    
}
