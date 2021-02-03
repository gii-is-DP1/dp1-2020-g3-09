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

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "aseguradoras")
public class Aseguradora extends AuditableEntity {

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

   public Set<Especialista> getEspecialistas() {
       return especialistas;
   }

   public void setEspecialistas(Set<Especialista> especialistas) {
       this.especialistas = especialistas;
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
   
    
}
