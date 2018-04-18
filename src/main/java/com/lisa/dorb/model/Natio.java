package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nationaliteit")
public class Natio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long nat_Id;

    @Column(name = "chauffeur_Id")
    private long chauffeur_Id;

    @Column(name = "land_Id")
    private long land_Id;

    protected Natio() {
    }

    public Natio(long id, long chauffeur_Id, long land_Id) {
        this.nat_Id = id;
        this.chauffeur_Id = chauffeur_Id;
        this.land_Id = land_Id;
    }

    public long getChauffeur_Id(){
        return chauffeur_Id;
    }
    public long getLand_Id(){
        return land_Id;
    }
    public long getID() {
        return nat_Id;
    }

    public void setChauffeur_Id(long chauffeur_Id) {
        this.chauffeur_Id = chauffeur_Id;
    }

    public void setLand_Id(long land_Id) {
        this.land_Id = land_Id;
    }
}
