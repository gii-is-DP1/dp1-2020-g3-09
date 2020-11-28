package com.tempura17.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aseguradoras")
public class Aseguradora extends BaseEntity{


    
    //NOTA: Necesita discutir y aobrdar detalles de implementación severos.

    private String nombre;

    /*
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Especialista> especialistaes;
    */

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Paciente> pacientes;

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
    
   
    
}
