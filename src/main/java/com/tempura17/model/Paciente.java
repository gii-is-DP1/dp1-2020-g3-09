package com.tempura17.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;

import javax.persistence.*;
import javax.persistence.JoinColumn;
import lombok.Data;




@Entity
@Data
@Table(name = "pacientes")
public class Paciente extends Person /*implements java.io.Serializable*/{

    private String dni;
    
    private String email;

    private String direccion;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private Integer edad;

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
    @JsonManagedReference
    private Set<Cita> citas;
    */

}