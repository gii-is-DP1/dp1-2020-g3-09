package com.tempura17.service;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;


import com.tempura17.model.Cita;
import com.tempura17.model.Justificante;
import com.tempura17.repository.JustificanteRepository;



@Service
public class JustificanteService {

    private JustificanteRepository justificanteRepository;

    private CitaService citaService;

    @Autowired
    public JustificanteService(JustificanteRepository justificanteRepository, CitaService citaService){
        this.justificanteRepository = justificanteRepository;
        this.citaService=citaService;
    }


    public Collection<Justificante> findAll(){
        return justificanteRepository.findAll();
    }
    
    public Justificante findJustificanteByCitaId(int citaId){
        return justificanteRepository.findJustificanteByCitaId(citaId);
    }

    public Optional<Justificante> findById(Integer id){
        return justificanteRepository.findById(id);
    }

    public void save(@Valid Justificante justificante){
        justificanteRepository.save(justificante);
      }

  
    public void createJustificanteForCitaId(Justificante justificante, Integer cita_id){
       Optional<Cita> cita = this.citaService.findById(cita_id);
       justificante.setCita(cita.get());
       justificanteRepository.save(justificante);
    }
}
