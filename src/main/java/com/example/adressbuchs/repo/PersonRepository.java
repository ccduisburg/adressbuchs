package com.example.adressbuchs.repo;

import com.example.adressbuchs.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findAllByNameIgnoreCaseContaining(String name);
    List<Person> findAllByVornameIgnoreCaseContaining(String vorname);
    List<Person> findAllByEmailIgnoreCaseContaining(String email);
    Person findByEmail(String email);
    List<Person> findAll();
}
