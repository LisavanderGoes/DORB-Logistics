package com.lisa.dorb.model.db.users;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(name = "rekeningnummer")
    private String rekeningnummer;

    @Column(name = "user_Id")
    private long user_Id;

    protected Klant() {
    }

    public Klant(long id, String rekeningnummer, long user_Id) {
        this.Id = id;
        this.rekeningnummer = rekeningnummer;
        this.user_Id = user_Id;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", Id);
    }

    public long getID() {
        return Id;
    }
    public long getUser_Id(){
        return user_Id;
    }
    public String getRekeningnummer(){
        return rekeningnummer;
    }

    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }
}
