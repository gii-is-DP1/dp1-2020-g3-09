package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tempura17.model.Alarma;
import com.tempura17.repository.AlarmaRepository;


@Service
public class AlarmaService {
	
	private AlarmaRepository alarmaRepository;
	
	@Autowired
	public AlarmaService(AlarmaRepository alarmaRepository) {
		super();
		this.alarmaRepository = alarmaRepository;
	}
	
	public Collection<Alarma> findAll(){
		return alarmaRepository.findAll();
	}
	
	public Optional<Alarma> findById(int id){
		return alarmaRepository.findById(id); 
	}
	
	public Alarma findByCitaId(Integer citaId) {
		return alarmaRepository.findByCitaId(citaId);
	}
	
	public void save(@Valid Alarma alarma) {
		alarmaRepository.save(alarma);
	}
	
	public void delete(Alarma alarma) {
		alarmaRepository.delete(alarma);
	}
}
