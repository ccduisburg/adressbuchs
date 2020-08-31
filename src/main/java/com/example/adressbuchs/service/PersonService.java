package com.example.adressbuchs.service;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.serviceImpl.Feedback;

import java.util.List;
import java.util.Optional;

public interface PersonService {
   Person save(Person t);
   Feedback delete(Long id);
   Feedback update(Long id, Person t);
   Person findById(Long id);
   List<Person> findAll();
   List<Person>findByName(String name);
   List<Person> findByVorname(String vorname);
   List<Person>findByEmail(String email);
   List<Person> getPersonBy(Optional<String> name,  Optional<String> email);

}
