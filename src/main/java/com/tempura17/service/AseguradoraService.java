package com.tempura17.service;

import com.tempura17.repository.AseguradoraRepository;
import com.tempura17.model.Aseguradora;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Especialista;
import com.tempura17.model.Paciente;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AseguradoraService {
    
    private AseguradoraRepository aseguradoraRepository;
    private PacienteService pacienteService;
    private EspecialistaService especialistaService;

    @Autowired
    public AseguradoraService(AseguradoraRepository aseguradoraRepository, PacienteService pacienteService, EspecialistaService especialistaService){
        this.aseguradoraRepository = aseguradoraRepository;
        this.pacienteService = pacienteService;
        this.especialistaService = especialistaService;
    }

    @Transactional(readOnly = true)
    public Collection<Aseguradora> findAll(){
      return aseguradoraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Aseguradora> findById(Integer id){
      return aseguradoraRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Aseguradora findByNombrAseguradora(String nombre){
      return aseguradoraRepository.findByNombreAseguradora(nombre);
    }

    @Transactional
    public void save(@Valid Aseguradora aseguradora){
      aseguradoraRepository.save(aseguradora);
    }

    @Transactional
    public void deletePaciente(Integer idAseguradora, Integer idPaciente){
      Optional<Paciente> paciente= pacienteService.findById(idPaciente);

      if (paciente.get() == null){
        System.out.println("paciente nulo");
      }else{
        Paciente p1 = paciente.get();
        p1.setAseguradora(null);
        this.pacienteService.save(p1);

        Optional<Aseguradora> aseguradora= aseguradoraRepository.findById(idAseguradora);

        if (aseguradora.get() == null){
          System.out.println("aseguradora nula");
        }else{
          Aseguradora a1 = aseguradora.get();
          a1.getPacientes().remove(p1);
        }
      }  
    }

    @Transactional
    public void deleteEspecialista(Integer idAseguradora, Integer idEspecialista){
      Optional<Especialista> especialista = especialistaService.findById(idEspecialista);
      
      if(especialista.get() == null){
        System.out.println("Especialista nulo");
      }else{
        Optional<Aseguradora> aseguradora = aseguradoraRepository.findById(idAseguradora);
      
        if(aseguradora.get() == null){
          System.out.println("Aseguradora nula");
        }else{
          Aseguradora a1 = aseguradora.get();
          Especialista e1 = especialista.get();

          a1.getEspecialistas().remove(e1);
          e1.getAseguradoras().remove(a1);

          especialistaService.save(e1);
          aseguradoraRepository.save(a1);
        }
      }
    }

    @Transactional
    public void editEspecialidad(Integer idEspecialista, String especialidad){
      Optional<Especialista> especialista = especialistaService.findById(idEspecialista);

      if(especialista.get() == null){
        System.out.println("Especialista nulo");
      }else{
        Especialista e1 = especialista.get();
        e1.setEspecialidad(Especialidad.valueOf(especialidad));
        this.especialistaService.save(e1);
      }
    }

    @Transactional
    public void createEspecialista(Especialista especialista, Integer aseguradora_id){
      this.especialistaService.save(especialista);
      Aseguradora aseguradora = aseguradoraRepository.findById(aseguradora_id).get();
      aseguradora.getEspecialistas().add(especialista);
      aseguradoraRepository.save(aseguradora);
      
    }
}
