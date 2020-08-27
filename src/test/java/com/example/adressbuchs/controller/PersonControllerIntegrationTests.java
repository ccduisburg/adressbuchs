package com.example.adressbuchs.controller;

import com.example.adressbuchs.AdressbuchsApplication;
import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.repo.PersonRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import com.example.adressbuchs.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sound.sampled.Port;
import java.util.*;


@SpringBootTest(classes = AdressbuchsApplication.class,
        webEnvironment = WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTests {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    PersonService personService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void add() {
        Person p=new Person("vorname","name","emaighg88l@");
            ResponseEntity<Person> responseEntity = this.restTemplate
                    .postForEntity("http://localhost:8082/person/add", p, Person.class);
            assertEquals(201, responseEntity.getStatusCodeValue());
        }



    @Test
    void delete() {
        final String uri = "http://localhost:8082/person/delete";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(8));
        restTemplate.delete(uri,String.class, params);
    }

    @Test
    void findByName() {
//        final String uri = "http://localhost:8082/person/findbyvorname";
//        Map<String, String> urlParameters = new HashMap<>();
//        urlParameters.put("vorname", "vorname");
////        ResponseEntity<Person[]> responseEntity =
//        assertTrue(Arrays.stream(restTemplate.getForEntity(uri,
//                Person[].class,
//                urlParameters).getBody()).count()>0);

    }

    @Test
    void findAllPerson() {
        final String uri = "http://localhost:8082/person/personen";

        Person p=new Person("vorname","name","emaighfhg88l@");
        ResponseEntity<Person> responseEntity = this.restTemplate
                .postForEntity("http://localhost:8082/person/add", p, Person.class);
         p=new Person("vorname","name","emaighg88gl@");
                              responseEntity = this.restTemplate
                .postForEntity("http://localhost:8082/person/add", p, Person.class);

        assertTrue(
                this.restTemplate.getForObject(uri,
                       Person[].class).length>1);

    }

    @Test
    void testGetPersonByName()
    {
        Person person = new Person();
        person.setId(101L);
        person.setName("Test");
        person.setVorname("TestVorname");

        List<Person> list = new ArrayList<Person>();
        list.add(person);

        Flux<Person> personFlux = Flux.fromIterable(list);

        Mockito
                .when(personService.findByName("Test"))
                .thenReturn(Collections.singletonList(personFlux.blockFirst()));

        webClient.get().uri("http://localhost:8082/person/name/{name}", "Test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class);


    }

    @Test
    void testGetPersonById()
    {
        Person person = new Person();
        person.setId(9L);
        person.setName("Test");
        person.setVorname("TestVorname");

        Mockito
                .when(personService.findById(9L))
                .thenReturn(person);

        webClient.get().uri("http://localhost:8082/person/{id}", 9)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(9)
                .jsonPath("$.name").isEqualTo("Test")
                .jsonPath("$.vorname").isEqualTo("TestVorname");

        Mockito.verify(personService, times(1)).findById(9L);
    }

}