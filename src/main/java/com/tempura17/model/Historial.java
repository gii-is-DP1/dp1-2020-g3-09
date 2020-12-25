/*package com.tempura17.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "historial")
public class Historial extends BaseEntity{
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historial", fetch = FetchType.EAGER)
    private Set<Cita> citas;

    public Set<Cita> getCitas() {
        return citas;
    }

    public void setCita(Set<Cita> citas) {
        this.citas = citas;
    }

    



    
}*/
