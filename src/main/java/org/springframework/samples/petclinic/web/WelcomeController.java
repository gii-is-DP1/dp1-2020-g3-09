package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.samples.petclinic.model.Person;
import java.util.List;
import java.util.ArrayList;


@Controller
public class WelcomeController {
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
      
      List<Person> persons = new ArrayList<Person>();
      Person person1 = new Person();
      person1.setFirstName("Antonio Javier");
      person1.setLastName("Dominguez");
      persons.add(person1);

      Person person2 = new Person();
      person2.setFirstName("Rodrigo");
      person2.setLastName("Garcia Casasola");
      persons.add(person2);
      model.put("persons",persons);
      model.put("title","My project");
      model.put("group", "G3-09");

	    return "welcome";
	  }
}
