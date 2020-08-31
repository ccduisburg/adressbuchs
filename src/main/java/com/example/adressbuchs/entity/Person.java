package com.example.adressbuchs.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titel;
    @Enumerated(EnumType.STRING)
    private Geschlecht geschlecht;
    private String name;
    private String vorname;
    private String email;
    private String adresse;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date geburstdatum;

    public Person( String vorname, String name, String email) {
        this.name =name;
        this.vorname = vorname;
        this.email = email;
    }
    public Person( Long id,String vorname, String name, String email) {
        this.id = id;
        this.name =name;
        this.vorname = vorname;
        this.email = email;
    }


    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGeburstdatum() {
        return geburstdatum;
    }

    public void setGeburstdatum(Date geburstdatum) {
        this.geburstdatum = geburstdatum;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
