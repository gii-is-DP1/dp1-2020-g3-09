package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.Especialista;
import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.repository.CitaRepository;
import com.tempura17.repository.EspecialistaRepository;
import com.tempura17.repository.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialistaService {

    private EspecialistaRepository especialistaRepository;

    @Autowired
    private CitaService citaService;

    private PacienteService pacienteService;

    @Autowired
    public EspecialistaService(EspecialistaRepository especialistaRepository, CitaService citaService,
            PacienteService pacienteService){

        this.especialistaRepository = especialistaRepository;
        this.citaService = citaService;
        this.pacienteService = pacienteService;
                            
    }

    public Collection<Especialista> findAll(){
        return especialistaRepository.findAll();
    }


    public Optional<Especialista> findById(Integer id){
        return especialistaRepository.findById(id);
    }

    public void createCitaForPacienteId(Cita cita, Integer paciente_id){
        Optional<Paciente> paciente = this.pacienteService.findById(paciente_id);
        cita.setPaciente(paciente.get());
        this.citaService.save(cita);
        this.pacienteService.saveCitaForPaciente(paciente_id,cita);

    }
    
}
