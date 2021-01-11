package com.tempura17.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.tempura17.model.Especialista;
import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.repository.EspecialistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EspecialistaService {

    private EspecialistaRepository especialistaRepository;

    private CitaService citaService;

    private PacienteService pacienteService;

    @Autowired
    public EspecialistaService(EspecialistaRepository especialistaRepository, CitaService citaService,
            PacienteService pacienteService){

        this.especialistaRepository = especialistaRepository;
        this.citaService = citaService;
        this.pacienteService = pacienteService;
                            
    }

    @Transactional(readOnly = true)
    public Collection<Especialista> findAll(){
        return especialistaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Especialista> findById(Integer id){
        return especialistaRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Especialista especialista){
        this.especialistaRepository.save(especialista);
    }

    @Transactional
    public void saveCitaForEspecialista(@Valid Integer id, Cita cita){
        Especialista especialista = especialistaRepository.findById(id).get();
        if(especialista.getCitas() == null){
            Set<Cita> citas = new HashSet<>();
            especialista.setCitas(citas);
            especialista.getCitas().add(cita);
            especialistaRepository.save(especialista);

        }else{
            especialistaRepository.findById(id).get().getCitas().add(cita);
        }
 
    }

    @Transactional
    public void createCitaForPacienteId(Cita cita, Integer paciente_id, Integer especialista_id){
        Optional<Paciente> paciente = this.pacienteService.findById(paciente_id);
        Especialista especialista = findById(especialista_id).get();
        cita.setPaciente(paciente.get());
        cita.setEspecialista(especialista);
        this.citaService.save(cita);
        this.pacienteService.saveCitaForPaciente(paciente_id,cita);
        saveCitaForEspecialista(especialista.getId(), cita);

    }

    @Transactional
    public void deleteById(Integer id){
        this.especialistaRepository.deleteById(id);
    }

}
