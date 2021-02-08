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

import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;
import com.tempura17.model.Paciente;
import com.tempura17.service.*;

@WebMvcTest(controllers=CitaController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class CitaControllerTests {

    private static final int TEST_CITA_ID = 1;

    private static final int TEST_PACIENTE_ID = 3;

    private static final int TEST_ESPECIALISTA_ID = 1;

    @Autowired
    private CitaController citaController;
    
    @MockBean
    private	 CitaService citaService;

    @MockBean
	private  EspecialistaService especialistaService;

    @MockBean
	private  PacienteService pacienteService;

	@Autowired
    private MockMvc mockMvc;

    @WithMockUser(value="spring")
    @Test
    void testAll() throws Exception{
        given(this.citaService.findAll()).willReturn(Lists.newArrayList(mock(Cita.class)));
        
        mockMvc.perform(get("/citas"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("citas"))
        .andExpect(view().name( "citas/Citas_list"));
    }

    @WithMockUser(value="spring")
    @Test
    void testHistorial() throws Exception{
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
    
        mockMvc.perform(get("/citas/historial/{pacienteId}", TEST_PACIENTE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("citas"))
        .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(value="spring")
    @Test
	void testSaveNewCitaGET() throws Exception {
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));

		mockMvc.perform(get("/citas/new/{pacienteId}", TEST_PACIENTE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("especialistas"))
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("cita"))
				.andExpect(view().name("citas/Citas_form"));
	}

    @WithMockUser(value="spring")
    @Test
	void testSaveNewCitaPOSTsuccess() throws Exception {
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
        given(mock(Paciente.class).getCitas()).willReturn(Sets.newHashSet());
		mockMvc.perform(post("/citas/new/{pacienteId}", TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", "PRESENCIAL")
                .param("tipo", "PRIVADO")
                .param("especialidad", "ALERGOLOGIA"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/pacientes/{pacienteId}/perfil"));
	}

    @WithMockUser(value = "spring")
    @Test
    void testSaveNewCitaPOSTHasErrors() throws Exception{
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));
        mockMvc.perform(post("/citas/new/{pacienteId}",TEST_PACIENTE_ID)
		    .with(csrf())
            .param("formato", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("cita"))
            .andExpect(model().attributeHasFieldErrors("cita", "formato"))
            .andExpect(model().attributeExists("especialistas"))
            .andExpect(model().attributeExists("especialidad"))
            .andExpect(model().attributeExists("cita"))
            .andExpect(view().name("citas/Citas_form"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditCitaGET() throws Exception{
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));

        mockMvc.perform(get("/citas/{citaId}/edit", TEST_CITA_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("cita"))
            .andExpect(model().attributeExists("especialistas"))
            .andExpect(model().attributeExists("especialidad"))
            .andExpect(view().name("citas/Citas_edit"));
    }

    @WithMockUser(value="spring")
    @Test
    void testEditCitaPOSTSuccess() throws Exception{
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
        
        mockMvc.perform(post("/citas/{citaId}/edit", TEST_CITA_ID)
            .with(csrf())
            .param("formato", "PRESENCIAL")
            .param("tipo", "PRIVADO")
            .param("especialidad", "ONCOLOGIA"))
            .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "spring")
    @Test
    void testEditCitaPOSTHasErrors() throws Exception{
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
        given(this.especialistaService.findAll()).willReturn(Lists.newArrayList(mock(Especialista.class)));

        mockMvc.perform(post("/citas/{citaId}/edit", TEST_CITA_ID)
		    .with(csrf())
            .param("formato", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("cita"))
            .andExpect(model().attributeHasFieldErrors("cita", "formato"))
            .andExpect(model().attributeExists("cita"))
            .andExpect(model().attributeExists("especialistas"))
            .andExpect(model().attributeExists("especialidad"))
            .andExpect(view().name("citas/Citas_edit"));
    }

    @WithMockUser(value="string")
    @Test
    void testFilterBy() throws Exception{
        given(this.citaService.findAll()).willReturn(Lists.newArrayList(mock(Cita.class)));
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
        mockMvc.perform(get("/citas/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("citas"))
            .andExpect(model().attributeExists("especialista"))
            .andExpect(model().attributeExists("paciente"))
            .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(value="string")
    @Test
    void testFindById() throws Exception{
        given(this.citaService.findById(TEST_CITA_ID)).willReturn(Optional.of(mock(Cita.class)));
        mockMvc.perform(get("/citas/{citaId}", TEST_CITA_ID))
            .andExpect(model().attributeExists("cita"))
            .andExpect(status().isOk())
            .andExpect(view().name("citas/Citas_detalles"));
    }

    @WithMockUser(value="string")
    @Test
	void testSaveNewCitaForPacienteGET() throws Exception {
		mockMvc.perform(get("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("cita"))
				.andExpect(view().name("citas/Citas_especialista"));
	}

    @WithMockUser(value="spring")
    @Test
	void testSaveNewCitaForPacientePOSTSuccess() throws Exception {
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
		mockMvc.perform(post("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", "PRESENCIAL")
                .param("tipo", "PRIVADO")
                .param("especialidad", "ONCOLOGIA"))        
                .andExpect(status().isOk())
				.andExpect(view().name("citas/Citas_list"));
	}

    @WithMockUser(value="spring")
    @Test
	void testSaveNewCitaForPacientePOSTHasErrors() throws Exception {
        given(this.especialistaService.findById(TEST_ESPECIALISTA_ID)).willReturn(Optional.of(mock(Especialista.class)));
        given(this.pacienteService.findById(TEST_PACIENTE_ID)).willReturn(Optional.of(mock(Paciente.class)));
		mockMvc.perform(post("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("cita"))   
                .andExpect(model().attributeHasFieldErrors("cita", "formato"))    
				.andExpect(view().name("citas/Citas_list"));
	}



    
}
