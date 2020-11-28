package com.tempura17.model;

import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "especialistas")
public class Especialista extends Person {

    private String dni;

    private String direccion;

    private String telefono;

    private String correo;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @ManyToMany(mappedBy = "especialistas")
    // @JoinTable(name = "aseguradoras_especialistas", joinColumns = @JoinColumn(name = "especialista_id"), 
    //inverseJoinColumns = @JoinColumn(name = "aseguradora_id"))
    private Set<Aseguradora> aseguradoras;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Set<Aseguradora> getAseguradoras() {
        return aseguradoras;
    }

    public void setAseguradoras(Set<Aseguradora> aseguradoras) {
        this.aseguradoras = aseguradoras;
    }


    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialista", fetch = FetchType.EAGER)
    private Set<Cita> citas;
    */
}
