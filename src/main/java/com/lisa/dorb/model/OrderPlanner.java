package com.lisa.dorb.model;

import java.io.Serializable;
import java.sql.Date;


public class OrderPlanner implements Serializable {

    long order_Id;
    long rit_Id;
    long ruimte;
    String klantnaam;
    String adres;
    double prijs;
    Date datum;
    String naamChauffeur;
    String kenteken;

    public OrderPlanner(long order_Id,  long rit_Id, String klantnaam, Date datum, String kenteken, String adres, String naamChauffeur, long ruimte, double prijs){
        this.order_Id = order_Id;
        this.rit_Id = rit_Id;
        this.ruimte = ruimte;
        this.prijs = prijs;
        this.klantnaam = klantnaam;
        this.datum = datum;
        this.kenteken = kenteken;
        this.adres = adres;
        this.naamChauffeur = naamChauffeur;
    }

    public long getId(){
        return order_Id;
    }

    public long getRit_Id(){
        return rit_Id;
    }
    public long getRuimte(){
        return ruimte;
    }
    public double getPrijs(){
        return prijs;
    }

    public String getKlantnaam(){
        return klantnaam;
    }

    public String getNaamChauffeur(){
        return naamChauffeur;
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

    public void setKlantnaam(String klantnaam) {
        this.klantnaam = klantnaam;
    }

    public void setNaamChauffeur(String naamChauffeur) {
        this.naamChauffeur = naamChauffeur;
    }

}
