package com.tempura17.web;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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
import com.tempura17.model.Especialista;
import com.tempura17.model.Paciente;
import com.tempura17.service.*;

@WebMvcTest(controllers=EspecialistaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class EspecialistaControllerTests {

    private static final int TEST_ESPECIALISTA_ID = 1;

    private static final int TEST_PACIENTE_ID = 1;

    private static final int TEST_CITA_ID = 1;

    @Autowired
    private EspecialistaController especialistaController;

    @MockBean
    private EspecialistaService especialistaService;

    @MockBean
    private AseguradoraService aseguradoraService;

    @MockBean
    private CitaService citaService;

    @MockBean
    private ActaService actaService;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(value="spring")
    @Test
    void testAll() throws Exception{
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));

        mockMvc.perform(get("/especialistas"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("especialistas"))
        .andExpect(view().name("especialistas/Especialistas_list"));
    }

    @WithMockUser(value="spring")
    @Test
	void testNewEspecialistaGET() throws Exception {
        given(this.aseguradoraService.findAll()).willReturn(Lists.newArrayList(mock(Aseguradora.class)));
        
		mockMvc.perform(get("/especialistas/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("especialista"))
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("aseguradoras"))
				.andExpect(view().name("especialistas/Especialista_form"));
	}

    @WithMockUser(value="spring")
    @Test
	void testNewEspecialistaPOSTsuccess() throws Exception {
		mockMvc.perform(post("/especialistas/new")
                .with(csrf())
                .param("firstName", "Nombre")
                .param("lastName", "Apellidos")
                .param("dni", "00000000E")
                .param("direccion", "casoplon")
                .param("telefono", "159645152")
                .param("correo", "correo@gmail.com")
                .param("especialidad", "MEDICINA_GENERAL"))
                .andExpect(status().isOk())
				.andExpect(view().name("especialistas/Especialistas_list"));
	}

    @WithMockUser(value = "spring")
    @Test
    void testNewEspecialistaPOSTHasErrors() throws Exception{
        mockMvc.perform(post("/especialistas/new")
		    .with(csrf())
            .param("dni", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("especialista"))
            .andExpect(model().attributeHasFieldErrors("especialista", "dni"))
            .andExpect(view().name("especialistas/Especialista_form"));
    }

    @WithMockUser(value = "spring")
	@Test
	void testEspecialistaPerfil() throws Exception {
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(mock(Especialista.class).getCitas()).willReturn(Sets.newHashSet());

        mockMvc.perform(get("/especialistas/{especialistaId}/perfil", TEST_ESPECIALISTA_ID))
                .andExpect(status().isOk())
				.andExpect(model().attributeExists("citas"))
                .andExpect(model().attributeExists("especialista"))
				.andExpect(view().name("especialistas/Especialistas_perfil"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditEspecialistaGET() throws Exception{
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.aseguradoraService.findAll()).willReturn(Lists.newArrayList(mock(Aseguradora.class)));

        mockMvc.perform(get("/especialistas/{especialistaId}/edit", TEST_ESPECIALISTA_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("aseguradoras"))
            .andExpect(model().attributeExists("especialista"))
            .andExpect(model().attributeExists("especialidad"))
            .andExpect(view().name("especialistas/Especialistas_edit"));
    }

    @WithMockUser(value = "spring")
	@Test
	void testEditEspecialistaPOSTSuccess() throws Exception {
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(mock(Especialista.class).getAseguradoras()).willReturn(Sets.newHashSet());

		mockMvc.perform(post("/especialistas/{especialistaId}/edit", TEST_ESPECIALISTA_ID)
							.with(csrf())
                            .param("firstName", "Apellidos")
                            .param("lastName", "Nombre")
							.param("dni", "00000000E")
                            .param("telefono", "159645152")
                            .param("correo", "correo@gmail.com")
                            .param("direccion", "CASA")
                            .param("especialidad", "ONCOLOGIA"))
				            .andExpect(status().isOk())
				            .andExpect(view().name("especialistas/Especialistas_list"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testEditEspecialistaPOSTHasErrors() throws Exception{
        mockMvc.perform(post("/especialistas/{especialistaId}/edit", TEST_ESPECIALISTA_ID)
		    .with(csrf())
            .param("dni", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("especialista"))
            .andExpect(model().attributeHasFieldErrors("especialista", "dni"))
            .andExpect(view().name("especialistas/Especialistas_list"));
    }

    @WithMockUser(value="spring")
    @Test
	void testNewCitaGET() throws Exception {
		mockMvc.perform(get("/especialistas/cita/{especialistaId}/{pacienteId}",TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("cita"))
				.andExpect(view().name("citas/Citas_form"));
	}

    @WithMockUser(value="spring")
    @Test
	void testNewCitaPOSTsuccess() throws Exception {
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
		mockMvc.perform(post("/especialistas/cita/{especialistaId}/{pacienteId}",TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", "PRESENCIAL")
                .param("tipo", "PRIVADO")
                .param("especialidad", "ALERGOLOGIA"))
                .andExpect(status().isOk())
				.andExpect(view().name("especialistas/Especialistas_list"));
	}

    @WithMockUser(value = "spring")
    @Test
    void testNewCitaPOSTHasErrors() throws Exception{
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
        mockMvc.perform(post("/especialistas/cita/{especialistaId}/{pacienteId}",TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
		    .with(csrf())
            .param("formato", "ONLINE")
            .param("tipo", "PRIVADO")
            .param("especialidad", "ALERGOLOGIA"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("cita"))
            .andExpect(view().name("especialistas/Especialistas_list"));
    }


    
}
