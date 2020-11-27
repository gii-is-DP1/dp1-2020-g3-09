package com.tempura17.model;

import java.util.Date;

import javax.persistence.CascadeType;
<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
=======
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
>>>>>>> master
import javax.persistence.JoinTable;
import lombok.Data;



@Entity
@Data
@Table(name = "citas")
public class Cita extends BaseEntity {

    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)
    private Formato formato;

    private String especialidad;

    private String especialista;

    //private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name ="paciente_id")
    private Paciente paciente;

=======
    private Formato formato;

    @Enumerated(EnumType.STRING)
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)    
    private Especialidad especialidad;

    private String especialista;

    @Temporal(TemporalType.TIMESTAMP)
    @Enumerated(EnumType.STRING)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name ="paciente_id")  
    private Paciente paciente;

    /* 
    NOTA: se teorizo que el posible origen del fallo reportado[Infinity loop recursion -> stackOverflow] 
    podría haber estado ligado a los metodos autogenerados por Lombok(hash,equals) dando que una entiedad podría estar
    llamando al hash de la otra y así sucesivamente.
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="especialista_id")  
    private Especialista especialista;
    

    public Cita(){}

    public Cita(Formato formato, Tipologia tipo, Especialidad especialidad, Date fecha, Paciente paciente,
                Especialista especialista) {
        this.formato = formato;
        this.tipo = tipo;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.paciente = paciente;
        this.especialista = especialista;
}


    public Formato getFormato() {
        return this.formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Tipologia getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipologia tipo) {
        this.tipo = tipo;
    }

    public Especialidad getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Especialista getEspecialista() {
        return this.especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    */

    
>>>>>>> master

    
}
