package com.example.adressbuchs.serviceImpl;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.payload.PersonNotFoundException;
import com.example.adressbuchs.repo.PersonRepository;
import com.example.adressbuchs.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final Logger LOG = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Feedback save(Person person) {
           Person result =personRepository.save(person);
           LOG.info(Feedback.DONE.toString());
           return result!=null?Feedback.DONE:Feedback.FEHLER;
    }

    @Override
    public Feedback delete(Long id) {
       Optional<Person> person= Optional.ofNullable(Optional.ofNullable(findById(id)).orElseThrow(NoSuchElementException::new));
       personRepository.delete(person.get());
        return person!=null?Feedback.DONE:Feedback.FEHLER;
    }

    @Override
    public Feedback update(Long id, Person person) {
        Optional<Person> updatePerson= Optional.ofNullable(Optional.ofNullable(findById(id)).orElseThrow(NoSuchElementException::new));
        updatePerson.get().setName(person.getName());
        updatePerson.get().setAdresse(person.getAdresse());
        updatePerson.get().setEmail(person.getEmail());
        updatePerson.get().setGeschlecht(person.getGeschlecht());
        updatePerson.get().setTitel(person.getTitel());
        updatePerson.get().setVorname(person.getVorname());
        updatePerson.get().setGeburstdatum(person.getGeburstdatum());
        Person result= personRepository.save(updatePerson.get());
        return result!=null?Feedback.DONE:Feedback.FEHLER;
    }

    @Override
    public Person findById(Long id) {
           Optional<Person> person= Optional.ofNullable(personRepository.findById(id).orElseThrow(() -> {
                       return new PersonNotFoundException(id);
                   }));

           return person.get();
    }

    @Override
    public List<Person> findAll() {
        List<Person> personen=  personRepository.findAll();
        //return personen!=null?personen:Collections.EMPTY_LIST;
        return personen;
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> personen=personRepository.findAllByNameIgnoreCaseContaining(name);
        return personen!=null?personen: Collections.EMPTY_LIST;
    }

    @Override
    public List<Person> findByVorname(String vorname) {
        List<Person> personen =personRepository.findAllByVornameIgnoreCaseContaining(vorname);
        return personen!=null?personen: Collections.EMPTY_LIST;
    }

    @Override
    public List<Person> findByEmail(String email) {
        List<Person> personen =personRepository.findAllByEmailIgnoreCaseContaining(email);
        return personen!=null?personen: Collections.EMPTY_LIST;
    }

//    public Person getPersonWithEmail(String email) {
//        Person  person =personRepository.findByEmail(email);
//        return person!=null?person:null;
//    }
}
