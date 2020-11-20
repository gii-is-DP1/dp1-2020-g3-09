package com.tempura17.repository;

import java.util.Collection;

import com.tempura17.model.Doctor;

import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer>{
    

    public Collection<Doctor> findAll();
}
