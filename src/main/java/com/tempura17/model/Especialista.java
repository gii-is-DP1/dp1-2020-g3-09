package com.tempura17.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity
@Audited
@Table(name = "especialistas")
public class Especialista extends Person {

    @NotEmpty(message = "El DNI no puede estar vacío")
    private String dni;

    @NotEmpty(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotEmpty(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotEmpty(message = "El correo no puede estar vacío")
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
        if(this.citas == null){
            this.citas = new HashSet<>();
            this.citas.add(cita);
            setCitas(citas);
        }else{
            this.citas.add(cita);
        }
        
    }

    public Set<Acta> getActas() {
        return actas;
    }

    public void setActas(Set<Acta> actas) {
        this.actas = actas;
    }

    public void addActa(Acta acta){
        if(this.actas == null){
            this.actas = new HashSet<>();
            this.actas.add(acta);
            setActas(actas);
        }else{
            this.actas.add(acta);
        } 
    }

    public void addAseguradora(Aseguradora aseguradora){
        if(this.aseguradoras == null){
            this.aseguradoras = new HashSet<>();
            this.aseguradoras.add(aseguradora);
            setAseguradoras(this.aseguradoras);
        }else{
            this.aseguradoras.add(aseguradora);
        } 
    }

    public void removeAseguradora(Aseguradora aseguradora){
        this.aseguradoras.remove(aseguradora);
    }

    public void removeCita(Cita cita){
        this.citas.remove(cita);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    

}
