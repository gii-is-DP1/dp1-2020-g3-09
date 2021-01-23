package com.tempura17.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Audited
@Table(name = "pacientes")
public class Paciente extends Person {

    @NotEmpty
    private String dni;
    @Email 
    private String email;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private Integer edad;

    @ManyToOne(optional = true)
    @JoinColumn(name ="aseguradora_id")
    @JsonIgnore
    private Aseguradora aseguradora;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Cita> citas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    @NotAudited
    private Set<CalculadoraSalud> calculadora;

    @ManyToOne (optional = true)
    @JoinColumn(name ="poliza_id")  
    @JsonIgnore
    private Poliza poliza;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
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

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}   
