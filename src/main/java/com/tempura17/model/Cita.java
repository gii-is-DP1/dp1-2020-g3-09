package com.tempura17.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.Data;



@Entity
@Data
@Table(name = "citas")
public class Cita extends BaseEntity /*implements java.io.Serializable*/ {

    @Enumerated(EnumType.STRING)
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)
    private Formato formato;

    private String especialidad;
    private String especialista; //futura clase doctor

    @Temporal(TemporalType.TIMESTAMP)
    @Enumerated(EnumType.STRING)
    private Date fecha;

	@ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
	private Paciente paciente;
    
}
