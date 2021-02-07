/*package com.tempura17.web;

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
import com.tempura17.model.Tipologia;
import com.tempura17.service.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

class CitaControllerTests {

    private static final int TEST_CITA_ID = 1;

    private static final int TEST_PACIENTE_ID = 3;

    private static final int TEST_ESPECIALISTA_ID = 1;

    @Autowired
    private	 CitaService citaService;

    @Autowired
	private  EspecialistaService especialistaService;

    @Autowired
	private  PacienteService pacienteService;

	@Autowired
    private MockMvc mockMvc;


    @BeforeEach
	void setup() { }

	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void all() throws Exception{
        mockMvc.perform(get("/citas"))
        .andExpect(status().isOk())
        .andExpect(status().isOk()).andExpect(model().attributeExists("citas"))
        .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void historial() throws Exception{
        mockMvc.perform(get("/citas/historial/{pacienteId}", TEST_PACIENTE_ID)).andExpect(status().isOk())
        .andExpect(status().isOk()).andExpect(model().attributeExists("citas"))
        .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void saveNewCitaGET() throws Exception {
		mockMvc.perform(get("/citas/new/{pacienteId}", TEST_PACIENTE_ID)).andExpect(status().isOk())
                .andExpect(model().attributeExists("especialistas"))
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("cita"))
				.andExpect(view().name("citas/Citas_form"));
	}

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void saveNewCitaPOSTsuccess() throws Exception {
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
    void saveNewCitaPOSTHasErrors() throws Exception{
        mockMvc.perform(post("/citas/new/{pacienteId}",TEST_PACIENTE_ID)
		    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("cita"))
            .andExpect(model().attributeHasFieldErrors("cita", "formato"))
            .andExpect(view().name("citas/Citas_form"));
    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void editCitaGET() throws Exception{
        mockMvc.perform(get("/citas/{citaId}/edit", TEST_CITA_ID))
            .andExpect(model().attributeExists("cita"))
            .andExpect(model().attribute("cita", hasProperty("especialidad", is(Especialidad.MEDICINA_GENERAL))))
            .andExpect(model().attribute("cita", hasProperty("formato", is(Formato.PRESENCIAL))))
            .andExpect(model().attribute("cita", hasProperty("tipo", is(Tipologia.ASEGURADO))))
            .andExpect(model().attributeExists("especialistas"))
            .andExpect(model().attributeExists("especialidad"))
            .andExpect(view().name("citas/Citas_edit"));

    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    @Disabled
    void editCitaPOST() throws Exception{
        mockMvc.perform(post("/citas/{citaId}/edit", TEST_CITA_ID)
            .with(csrf())
            .param("formato", "PRESENCIAL")
            .param("tipo", "PRIVADO")
            .param("especialidad", "ALERGOLOGIA"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/especialistas/" + TEST_ESPECIALISTA_ID + "/perfil"));

    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void filterBy() throws Exception{
        mockMvc.perform(get("/citas/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID))
            .andExpect(model().attributeExists("citas"))
            .andExpect(model().attributeExists("especialista"))
            .andExpect(model().attributeExists("paciente"))
            .andExpect(status().isOk())
            .andExpect(view().name("citas/Citas_list"));
    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void findById() throws Exception{
        mockMvc.perform(get("/citas/{citaId}", TEST_CITA_ID))
            .andExpect(model().attributeExists("cita"))
            .andExpect(model().attribute("cita", hasProperty("especialidad", is(Especialidad.MEDICINA_GENERAL))))
            .andExpect(model().attribute("cita", hasProperty("formato", is(Formato.PRESENCIAL))))
            .andExpect(model().attribute("cita", hasProperty("tipo", is(Tipologia.ASEGURADO))))
            .andExpect(status().isOk())
            .andExpect(view().name("citas/Citas_detalles"));
    }

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void saveNewCitaForPacienteGET() throws Exception {
		mockMvc.perform(get("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("especialidad"))
                .andExpect(model().attributeExists("cita"))
				.andExpect(view().name("citas/Citas_especialista"));
	}

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void saveNewCitaForPacientePOSTSuccess() throws Exception {
		mockMvc.perform(post("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", "PRESENCIAL")
                .param("tipo", "PRIVADO")
                .param("especialidad", "ONCOLOGIA"))        
                .andExpect(status().isOk())
				.andExpect(view().name("citas/Citas_list"));
	}

    @WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void saveNewCitaForPacientePOSTHasErrors() throws Exception {
		mockMvc.perform(post("/citas/new/{especialistaId}/{pacienteId}", TEST_ESPECIALISTA_ID,TEST_PACIENTE_ID)
                .with(csrf())
                .param("formato", "ONLINE")
                .param("tipo", "PRIVADO")
                .param("especialidad", "ONCOLOGIA"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("cita"))       
				.andExpect(view().name("citas/Citas_list"));
	}


    
}*/