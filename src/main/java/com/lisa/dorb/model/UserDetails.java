package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;


public class UserDetails implements Serializable {


    private long id;
    private long user_Id;
    private String rol;
    private String rijbewijs;
    private long werkdagen;
    private String rekeningnummer;
    private String landen;


    protected UserDetails() {
    }

    public UserDetails(long id, long user_Id, String rol, String rijbewijs, long werkdagen, String rekeningnummer, String landen) {
        this.id = id;
        this.user_Id = user_Id;
        this.rol = rol;
        this.rekeningnummer = rekeningnummer;
        this.werkdagen = werkdagen;
        this.rijbewijs = rijbewijs;
        this.landen = landen;
    }

    public long getUser_Id(){
        return user_Id;
    }
    public String getRol(){
        return rol;
    }
    public long getID() {
        return id;
    }
    public String getRekeningnummer(){
        return rekeningnummer;
    }
    public String getRijbewijs(){
        return rijbewijs;
    }
    public String getWerkdagen(){
        String s = String.valueOf(werkdagen);
        return s;
    }
    public String getLanden(){
        return landen;
    }


    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }
    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }
    public void setRijbewijs(String rijbewijs) {
        this.rijbewijs = rijbewijs;
    }
    public void setWerkdagen(long werkdagen) {
        this.werkdagen = werkdagen;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
