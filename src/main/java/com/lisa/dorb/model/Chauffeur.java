package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chauffeurs")
public class Chauffeur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_Id")
    private long user_Id;

    @Column(name = "rijbewijs")
    private String rijbewijs;

    @Column(name = "werkdagen")
    private long werkdagen;

    protected Chauffeur() {
    }

    public Chauffeur(long id, long user_Id, String rijbewijs, long werkdagen) {
        this.id = id;
        this.user_Id = user_Id;
        this.rijbewijs = rijbewijs;
        this.werkdagen = werkdagen;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", user_Id);
    }

    public String getRijbewijs(){
        return rijbewijs;
    }
    public long getUser_Id(){
        return user_Id;
    }
    public String getWerkdagen(){
        String s = String.valueOf(werkdagen);
        return s;
    }
    public long getID() {
        return id;
    }

    public void setRijbewijs(String rijbewijs) {
        this.rijbewijs = rijbewijs;
    }
    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }

    public void setWerkdagen(long werkdagen) {
        this.werkdagen = werkdagen;
    }
}
