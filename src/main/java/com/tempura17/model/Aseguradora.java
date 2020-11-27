/*package com.tempura17.model;

import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "aseguradoras")
public class Aseguradora extends BaseEntity{

    private String nombre;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Doctor> doctores;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseguradora", fetch = FetchType.EAGER)
   private Set<Paciente> pacientes; 




    
}*/
