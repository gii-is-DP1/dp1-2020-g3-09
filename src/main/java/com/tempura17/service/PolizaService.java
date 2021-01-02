package com.tempura17.service;

import java.util.Collection;

import com.tempura17.model.Poliza;
import com.tempura17.repository.PolizaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolizaService {

    private PolizaRepository polizaRepository;

    @Autowired
    public PolizaService(PolizaRepository polizaRepository){
        this.polizaRepository = polizaRepository;
    }

    public Collection<Poliza> findAll(){
        return polizaRepository.findAll();
    }
    
}
