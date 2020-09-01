package com.example.adressbuchs.service;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.serviceImpl.Feedback;

import java.util.List;
import java.util.Optional;

public interface PersonService<T> {
   Person save(Person t);
   Feedback delete(Long id);
   Feedback update(Long id, Person t);
   Person findById(Long id);
   List<T> findAll();
   List<T>findByName(String name);
   List<T> findByVorname(String vorname);
   List<T>findByEmail(String email);
   boolean emailCheck(String email);
}
