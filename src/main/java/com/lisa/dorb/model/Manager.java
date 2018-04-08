package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "managers")
public class Manager implements Serializable {

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

    protected Manager() {
    }

    public Manager(long id, String voornaam, String tussenvoegsel, String achternaam, String inlognaam, String wachtwoord) {
        this.user_Id = id;
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
    public long getID() {
        return user_Id;
    }

    public void setVoornaam(String s) {
        this.voornaam = s;
    }

    public void setTussenvoegsel(String s) {
        this.tussenvoegsel = s;
    }

    public void setAchternaam(String s) {
        this.achternaam = s;
    }

    public void setInlognaam(String s) {
        this.inlognaam = s;
    }

    public void setWachtwoord(String s) {
        this.wachtwoord = s;
    }
}
