package com.example.adressbuchs.serviceImpl;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.repo.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    PersonRepository personRepository;


    @BeforeEach
    void setUp() {
            MockitoAnnotations.initMocks(this);


    }

    private Person createPerson() {
        Person person = new Person( 102L,"Hans", "Musterman", "blabla@gmail.com");
        return person;
    }

    @Test
    void save() {
        Person person = createPerson();
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person feedback = personService.save(person);

        assertThat(feedback.getName()).isEqualTo(person.getName());
    }

    @Test
    void delete() {
//       Long personId=1002L;
//        personService.delete(personId);
//        verify(personRepository, times(1)).deleteById(eq(personId));

    }

    @Test
    void update() {
    }

    @Test
    void getPersonByEmail() {
        Person person=createPerson();

        List<Person> personen=  Arrays.asList(person);

        when(personRepository.findAllByEmailIgnoreCaseContaining(anyString())).thenReturn(personen);

        // when
        List<Person> result = personService.findByEmail("blabla@");

        // then
        assertThat(result.size()).isEqualTo(1);

        assertThat(result.get(0).getVorname())
                .isEqualTo(person.getVorname());

        assertThat(result.get(0).getName())
                .isEqualTo(person.getName());
    }



    @Test
    void findAll() {
        // given
        Person person1 = createPerson();
        Person person2 = new Person(200L, "Suu", "daa", "suudaa@gmail.com");

        List<Person> personen=  Arrays.asList(person1, person2);

        when(personService.findAll()).thenReturn(personen);

        // when
        List<Person> result = personService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getVorname())
                .isEqualTo(person1.getVorname());

        assertThat(result.get(1).getName())
                .isEqualTo(person2.getName());
    }

    @Test
    void findByName() {
        Person person=createPerson();

        List<Person> personen=  Arrays.asList(person);

        when(personService.findByName(anyString())).thenReturn(personen);

        // when
        List<Person> result = personService.findByName("Muster");

        // then
        assertThat(result.size()).isEqualTo(1);

        assertThat(result.get(0).getVorname())
                .isEqualTo(person.getVorname());

        assertThat(result.get(0).getEmail())
                .isEqualTo(person.getEmail());
    }

    @Test
    void findByVorname() {
        Person person=createPerson();

        List<Person> personen=  Arrays.asList(person);

        when(personRepository.findAllByVornameIgnoreCaseContaining(anyString())).thenReturn(personen);

        // when
        List<Person> result = personService.findByVorname("Hans");

        // then
        assertThat(result.size()).isEqualTo(1);

        assertThat(result.get(0).getName())
                .isEqualTo(person.getName());

        assertThat(result.get(0).getEmail())
                .isEqualTo(person.getEmail());
    }


}