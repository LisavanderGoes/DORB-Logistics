package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chauffeurs")
public class Chauffeur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_Id;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "rijbewijs")
    private String rijbewijs;

    @Column(name = "werkdagen")
    private long werkdagen;

    @Column(name = "inlognaam")
    private String inlognaam;

    @Column(name = "wachtwoord")
    private String wachtwoord;

    protected Chauffeur() {
    }

    public Chauffeur(long id, String voornaam, String tussenvoegsel, String achternaam, String rijbewijs, long werkdagen, String inlognaam, String wachtwoord) {
        this.user_Id = id;
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.rijbewijs = rijbewijs;
        this.werkdagen = werkdagen;
        this.inlognaam = inlognaam;
        this.wachtwoord = wachtwoord;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", user_Id, voornaam, achternaam);
    }

    public String getWachtwoord(){
        return wachtwoord;
    }
    public String getVoornaam(){
        return voornaam;
    }
    public String getTussenvoegsel(){
        return tussenvoegsel;
    }
    public String getAchternaam(){
        return achternaam;
    }
    public String getInlognaam(){
        return inlognaam;
    }
    public String getRijbewijs(){
        return rijbewijs;
    }
    public String getWerkdagen(){
        String s = String.valueOf(werkdagen);
        return s;
    }
    public long getID() {
        return user_Id;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setInlognaam(String inlognaam) {
        this.inlognaam = inlognaam;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public void setRijbewijs(String rijbewijs) {
        this.rijbewijs = rijbewijs;
    }

    public void setWerkdagen(long werkdagen) {
        this.werkdagen = werkdagen;
    }
}
