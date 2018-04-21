package com.lisa.dorb.model;

import java.io.Serializable;
import java.sql.Date;


public class Werkrooster implements Serializable {

    Date datum;
    String kenteken;
    String adres;
    long aantal;

    public Werkrooster(Date datum, String kenteken, String adres, long aantal){
        this.datum = datum;
        this.kenteken = kenteken;
        this.adres = adres;
        this.aantal = aantal;
    }

    public Date getDatum(){
        return datum;
    }

    public String getKenteken() {
        return kenteken;
    }

    public String getAdres() {
        return adres;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
    public void setAantal(long aantal) {
        this.aantal = aantal;
    }

    public String getAantal() {
        return aantal + "";
    }
}
