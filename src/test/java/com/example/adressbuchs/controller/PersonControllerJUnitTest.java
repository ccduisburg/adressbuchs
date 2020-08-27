package com.example.adressbuchs.controller;

import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.service.PersonService;
import com.example.adressbuchs.serviceImpl.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class PersonControllerJUnitTest {

    @InjectMocks
    PersonController personController;

    @Mock
    PersonService personService;
    @Test
    public void testAddPerson()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(personService.save(any(Person.class))).thenReturn(Feedback.DONE);

        Person person = new Person( "foo", "bar", "blabla@gmail.com");
        ResponseEntity<Person> responseEntity = personController.add(person);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

    }
    @Test
    void findByName() {

    }

    @Test
    void findByVorname() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findAllPerson() {
        // given
        Person person1 = new Person(100L, "buu", "baa", "buubaa@gmail.com");
        Person person2 = new Person(200L, "Suu", "daa", "suudaa@gmail.com");
        Person person3 = new Person();
        List<Person> personen=  Arrays.asList(person1, person2);

        when(personService.findAll()).thenReturn(personen);

        // when
        List<Person> result = personController.findAllPerson().getBody();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getVorname())
                .isEqualTo(person1.getVorname());

        assertThat(result.get(1).getName())
                .isEqualTo(person2.getName());
    }
}