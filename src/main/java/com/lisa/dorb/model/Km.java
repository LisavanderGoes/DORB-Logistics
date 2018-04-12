package com.lisa.dorb.model;

import com.vaadin.spring.annotation.SpringComponent;

import java.io.Serializable;


public class Km implements Serializable {

    String adres;
    Float km;

    public Km(String adres, Float km){
        this.adres = adres;
        this.km = km;
    }

    public String getAdres(){
        return adres;
    }

    public Float getKm() {
        return km;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setKm(Float km) {
        this.km = km;
    }
}
