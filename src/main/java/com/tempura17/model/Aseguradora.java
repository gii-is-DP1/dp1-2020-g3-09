package com.tempura17.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "aseguradoras")
public class Aseguradora extends AuditableEntity {

    @NotEmpty(message="El nombre no puede estar vacio")
    private String nombre;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Paciente> pacientes;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Poliza> polizas;

   @ManyToMany
   @JoinTable(name = "aseguradoras_especialistas", joinColumns = @JoinColumn(name = "aseguradora_id"), 
                                                   inverseJoinColumns = @JoinColumn(name = "especialista_id"))
   private Set<Especialista> especialistas;

   public String getNombre() {
       return nombre;
   }

   public void setNombre(String nombre) {
       this.nombre = nombre;
   }

   public Set<Paciente> getPacientes() {
       return pacientes;
   }

   public void setPacientes(Set<Paciente> pacientes) {
       this.pacientes = pacientes;
   }

   public Set<Poliza> getPolizas() {
       return polizas;
   }

   public void setPolizas(Set<Poliza> polizas) {
       this.polizas = polizas;
   }

   public void addPoliza(Poliza poliza){
    if(this.polizas == null){
        this.polizas = new HashSet<>();
        this.polizas.add(poliza);
        setPolizas(polizas);
    }else{
        this.polizas.add(poliza);
    }
    }

    public void removePoliza(Poliza poliza){
        this.polizas.remove(poliza);
    }

    
    public void addEspecialista(Especialista especialista){
        if(this.especialistas == null){
            this.especialistas = new HashSet<>();
            this.especialistas.add(especialista);
            setEspecialistas(especialistas);
        }else{
            this.especialistas.add(especialista);
        }
        }

     public void removeEspecialista(Especialista especialista){
        this.especialistas.remove(especialista);
    }

   public Set<Especialista> getEspecialistas() {
       return especialistas;
   }

   public void setEspecialistas(Set<Especialista> especialistas) {
       this.especialistas = especialistas;
   }

   @Override
   public String toString() {
       return nombre;
   }   
    
}
