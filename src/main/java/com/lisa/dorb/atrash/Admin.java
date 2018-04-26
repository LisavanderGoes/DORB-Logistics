package com.lisa.dorb.atrash;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
public class Admin implements Serializable {

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

    protected Admin() {
    }

    public Admin(long id, String voornaam, String tussenvoegsel, String achternaam, String inlognaam, String wachtwoord) {
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
}
