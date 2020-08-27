package com.example.adressbuchs.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "adresse")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String anschrift;
    private String hausno;
    private String plz;
    private String stadt;
   @OneToMany
   private List<Person> peersonen;

    public List<Person> getPeersonen() {
        return peersonen;
    }

    public void setPeersonen(List<Person> peersonen) {
        this.peersonen = peersonen;
    }

    public Adresse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getHausno() {
        return hausno;
    }

    public void setHausno(String hausno) {
        this.hausno = hausno;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }
}
