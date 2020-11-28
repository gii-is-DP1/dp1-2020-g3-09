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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.tempura17.model.BaseEntity;
import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;

@Entity
@Table(name = "historial")
public class Historial extends BaseEntity{

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historial", fetch = FetchType.EAGER)
    private Set<Cita> cita;

    public Set<Cita> getCita() {
        return cita;
    }

    public void setCita(Set<Cita> cita) {
        this.cita = cita;
    }

    



    
}
