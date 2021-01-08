package com.tempura17.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


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
    @JsonIgnore
    // @JoinTable(name = "aseguradoras_especialistas", joinColumns = @JoinColumn(name = "especialista_id"), 
    //inverseJoinColumns = @JoinColumn(name = "aseguradora_id"))

    private Set<Aseguradora> aseguradoras;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialista", fetch = FetchType.EAGER)
    private Set<Cita> citas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialista", fetch = FetchType.EAGER)
    private Set<Acta> actas;

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

    public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

    public void addCita(Cita cita){
        this.citas.add(cita);
    }

    public Set<Acta> getActas() {
        return actas;
    }

    public void setActas(Set<Acta> actas) {
        this.actas = actas;
    }

}
