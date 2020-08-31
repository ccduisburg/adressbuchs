package com.example.adressbuchs.controller;

import com.example.adressbuchs.AdressbuchsApplication;
import com.example.adressbuchs.entity.Person;
import com.example.adressbuchs.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@SpringBootTest(classes = AdressbuchsApplication.class,
        webEnvironment = WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTests {
    @MockBean
    PersonService personService;
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PersonController personController;

    private Person createPerson() {
        Person person = new Person(102L, "Hans", "Musterman", "blabla@gmail.com");
        return person;
    }

    @Before
    public void setup() throws Exception {


    }

    @Test
    void add() throws Exception {



        Person person2 = createPerson();

        final String URL_ADDKurs = "http://localhost:8082/person/add";

        URI uri = null;
        try {
            final String baseUrl = URL_ADDKurs;
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpEntity<Person> entity = new HttpEntity<Person>(person2);
        String response = restTemplate.postForObject(uri, entity, String.class);


    }

    @Test
    void delete() {
        final String uri = "http://localhost:8082/person/delete";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(8));
        restTemplate.delete(uri, String.class, params);
    }




    @Test
    void findAllPerson() {
        final String uri = "http://localhost:8082/person/personen";
        Person person = new Person();
        person.setId(101L);
        person.setName("Test");
        person.setVorname("TestVorname");
        Person person2 = createPerson();
        List<Person> list = new ArrayList<Person>();
        list.add(person);
        list.add(person2);

        Flux<Person> personFlux = Flux.fromIterable(list);

        Mockito
                .when(personService.findAll())
                .thenReturn(personFlux.collectList().block());

        webClient.get().uri(uri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class);

        Mockito.verify(personService, times(1)).findAll();

    }

    @Test
    void testGetPersonByName() {
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

        Mockito.verify(personService, times(1)).findByName("Test");
    }

    @Test
    void testGetPersonById() {
        Person person = createPerson();

        Mockito
                .when(personService.findById(102L))
                .thenReturn(person);

        webClient.get().uri("http://localhost:8082/person/{id}", 102)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(102)
                .jsonPath("$.name").isEqualTo("Musterman")
                .jsonPath("$.vorname").isEqualTo("Hans");

        Mockito.verify(personService, times(1)).findById(102L);
    }

}