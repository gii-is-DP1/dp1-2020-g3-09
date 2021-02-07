package com.tempura17.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tempura17.model.Alarma;
import com.tempura17.model.Cita;
import com.tempura17.repository.AlarmaRepository;


@Service
public class AlarmaService {
	
	private AlarmaRepository alarmaRepository;

	private CitaService citaService;
	
	@Autowired
	public AlarmaService(AlarmaRepository alarmaRepository) {
		super();
		this.alarmaRepository = alarmaRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Alarma> findAll(){
		return alarmaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Alarma> findById(int id){
		return alarmaRepository.findById(id); 
	}
	
	@Transactional(readOnly = true)
	public Alarma findByCitaId(Integer citaId) {
		return alarmaRepository.findByCitaId(citaId);
	}
	
	@Transactional
	public void save(@Valid Alarma alarma) {
		alarmaRepository.save(alarma);
	}
	
	@Transactional
	public void delete(Alarma alarma) {
		alarmaRepository.delete(alarma);
	}

	/*
	public int getDiasDiff(int citaId){
		Cita cita = citaService.findById(citaId).get();
		Date fechainicio = cita.getFecha();
		Date fechaactual = new Date(System.currentTimeMillis());
		Long fechainicio2 = fechainicio.getTime();
		Long fechaactual2 = fechaactual.getTime();
		Long diferencia = fechainicio2 - fechaactual2;
		Double dias = Math.floor(diferencia/86400000);
		int diasint = (int)Math.round(dias);
		return diasint;
	}*/
}
