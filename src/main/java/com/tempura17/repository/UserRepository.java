package com.tempura17.repository;

import org.springframework.data.repository.CrudRepository;
import com.tempura17.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
