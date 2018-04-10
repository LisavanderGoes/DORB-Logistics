package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "vrachtwagens")
public class Vrachtwagen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long vrachtwagen_Id;

    @Column(name = "typ_Id")
    private long typ_Id;

    @Column(name = "kenteken")
    private String kenteken;

    @Column(name = "apk")
    private Date apk;

    @Column(name = "status")
    private String status;

    protected Vrachtwagen() {
    }

    public Vrachtwagen(long id, long typ_Id, String kenteken, Date apk, String status) {
        this.vrachtwagen_Id = id;
        this.typ_Id = typ_Id;
        this.kenteken = kenteken;
        this.apk = apk;
        this.status = status;
    }

    public String getTyp_Id(){
        return typ_Id+"";
    }
    public String getKenteken(){
        return kenteken;
    }
    public String getApk(){
        return apk+"";
    }
    public String getStatus(){
        return status;
    }
    public long getID() {
        return vrachtwagen_Id;
    }

    public void setTyp_Id(long typ_Id) {
        this.typ_Id = typ_Id;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public void setApk(Date apk) {
        this.apk = apk;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
