package com.lisa.dorb.model.db;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "rit")
public class Rit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rit_Id;

    @Column(name = "vrachtwagen_Id")
    private long vrachtwagen_Id;

    @Column(name = "ruimte")
    private long ruimte;

    @Column(name = "datum")
    private Date datum;

    @Column(name = "chauffeur_Id")
    private long chauffeur_Id;

    protected Rit() {
    }

    public Rit(long id, long vrachtwagen_Id, long ruimte,  Date datum, long chauffeur_Id) {
        this.rit_Id = id;
        this.vrachtwagen_Id = vrachtwagen_Id;
        this.ruimte = ruimte;
        this.datum = datum;
        this.chauffeur_Id = chauffeur_Id;
    }

    public String getVrachtwagen_Id(){
        return vrachtwagen_Id +"";
    }
    public String getRuimte(){
        return ruimte+"";
    }
    public String getDatum(){
        return datum+"";
    }
    public String getChauffeur_Id(){
        return chauffeur_Id +"";
    }
    public long getID() {
        return rit_Id;
    }

    public void setVrachtwagen_Id(long rit_Id) {
        this.vrachtwagen_Id = rit_Id;
    }

    public void setRuimte(long ruimte) {
        this.ruimte = ruimte;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setChauffeur_Id(long chauffeur_Id) {
        this.chauffeur_Id = chauffeur_Id;
    }
}
