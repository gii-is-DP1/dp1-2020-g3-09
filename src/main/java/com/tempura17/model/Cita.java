package com.tempura17.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import lombok.Data;



@Entity
@Data
@Table(name = "citas")
public class Cita extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)
    private Formato formato;

    private String especialidad;

    private String especialista;

    //private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name ="paciente_id")
    private Paciente paciente;


    
}
