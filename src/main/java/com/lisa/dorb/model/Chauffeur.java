package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chauffeurs")
public class Chauffeur implements Serializable {

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

    @Column(name = "rijbewijs_Id")
    private long rijbewijs_Id;

    @Column(name = "nat_Id")
    private long nat_Id;

    @Column(name = "werkdagen")
    private long werkdagen;

    @Column(name = "inlognaam")
    private String inlognaam;

    @Column(name = "wachtwoord")
    private String wachtwoord;

    protected Chauffeur() {
    }

    public Chauffeur(String voornaam, String tussenvoegsel, String achternaam, long rijbewijs_Id, long nat_Id, long werkdagen, String inlognaam, String wachtwoord) {
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.rijbewijs_Id = rijbewijs_Id;
        this.nat_Id = nat_Id;
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

    public long getID() {
        return user_Id;
    }
}
