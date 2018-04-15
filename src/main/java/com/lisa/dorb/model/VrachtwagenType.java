package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "typevrachtwagens")
public class VrachtwagenType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long typ_Id;

    @Column(name = "type")
    private String type;

    @Column(name = "ruimte")
    private long ruimte;

    @Column(name = "rijbewijs")
    private String rijbewijs;

    @Column(name = "grootst")
    private String grootst;

    protected VrachtwagenType() {
    }

    public VrachtwagenType(long id, String type, long ruimte, String rijbewijs, String grootst) {
        this.typ_Id = id;
        this.type = type;
        this.ruimte = ruimte;
        this.rijbewijs = rijbewijs;
        this.grootst = grootst;
    }

    public String getType(){
        return type;
    }
    public long getRuimte(){
        return ruimte;
    }
    public String getRijbewijs(){
        return rijbewijs;
    }
    public String getGrootst(){
        return grootst;
    }
    public long getID() {
        return typ_Id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRuimte(long ruimte) {
        this.ruimte = ruimte;
    }

    public void setRijbewijs(String rijbewijs) {
        this.rijbewijs = rijbewijs;
    }

    public void setGrootst(String grootst) {
        this.grootst = grootst;
    }
}
