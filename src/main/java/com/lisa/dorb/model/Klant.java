package com.lisa.dorb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "klanten")
public class Klant implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_Id;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "rekeningnummer")
    private String rekeningnummer;

    @Column(name = "inlognaam")
    private String inlognaam;

    @Column(name = "wachtwoord")
    private String wachtwoord;

    protected Klant() {
    }

    public Klant(String voornaam, String tussenvoegsel, String achternaam, String rekeningnummer, String inlognaam, String wachtwoord) {
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.rekeningnummer = rekeningnummer;
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

    public long getID() {
        return user_Id;
    }
}
