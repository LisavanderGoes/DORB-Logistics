package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "planners")
public class Planner implements Serializable {

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


    @Column(name = "inlognaam")
    private String inlognaam;

    @Column(name = "wachtwoord")
    private String wachtwoord;

    protected Planner() {
    }

    public Planner(String voornaam, String tussenvoegsel, String achternaam, String inlognaam, String wachtwoord) {
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
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
