package com.example.adressbuchs.controller;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.service.PersonService;
import com.example.adressbuchs.serviceImpl.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@EnableAutoConfiguration
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Person> add(@Validated @RequestBody Person person){
        if(personService.emailCheck(person.getEmail())){
            throw new IllegalArgumentException("same email was found");
        }
       Person result= personService.save(person);
       return result!=null?new ResponseEntity<Person>(person,HttpStatus.CREATED):new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> update(@Validated @RequestBody Person person,@RequestParam Long id){
        Feedback result= personService.update(id,person);
        return result.equals(Feedback.DONE)?new ResponseEntity<Person>(HttpStatus.OK):new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> delete(@Validated @PathVariable Long id){
        Feedback result= personService.delete(id);
        return result.equals(Feedback.DONE)?new ResponseEntity<Person>(HttpStatus.OK):new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
    }
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Person>> findByName(@Validated @PathVariable String name){
        List<Person> persons= personService.findByName(name);
        return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
    }
    @RequestMapping(value = "/vorname/{vorname}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Person>> findByVorname(@Validated @PathVariable String vorname){
        List<Person> persons= personService.findByVorname(vorname);
        return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
    }
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Person>> findByEmail(@Validated @PathVariable String email){
        List<Person> result= personService.findByEmail(email);;
        return new ResponseEntity<Collection<Person>>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/personen", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAllPerson(){
        List<Person> result= personService.findAll();;
        return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> findById(@Validated @PathVariable Long id){
        Person result= personService.findById(id);
        return new ResponseEntity<Person>(result, HttpStatus.OK);
    }

}
