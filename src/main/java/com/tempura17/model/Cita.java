package com.tempura17.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinTable;
import lombok.Data;



@Entity
@Table(name = "citas")
public class Cita extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Formato formato;

    @Enumerated(EnumType.STRING)
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)    
    private Especialidad especialidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Enumerated(EnumType.STRING)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name ="paciente_id")  
    @JsonIgnore
    private Paciente paciente;

    @ManyToOne 
    @JoinColumn(name = "especialista_id")
    @JsonIgnore
    private Especialista especialista;

    /*@ManyToOne
    @JoinColumn(name ="historial_id")  
    @JsonIgnore
    private Historial historial;*/

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Tipologia getTipo() {
        return tipo;
    }

    public void setTipo(Tipologia tipo) {
        this.tipo = tipo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /*public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }*/

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }


    
    
}
