package com.lisa.dorb.model.DB;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "prijzen")
public class Prijs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long prijs_Id;

    @Column(name = "prijs")
    private String prijs;

    @Column(name = "wat")
    private String wat;

    protected Prijs() {
    }

    public Prijs(long id, String prijs, String wat) {
        this.prijs_Id = id;
        this.prijs = prijs;
        this.wat = wat;
    }

    public String getPrijs(){
        return prijs;
    }
    public String getWat(){
        return wat;
    }
    public long getID() {
        return prijs_Id;
    }

    public void setPrijs(String prijs) {
        this.prijs = prijs;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }
}
