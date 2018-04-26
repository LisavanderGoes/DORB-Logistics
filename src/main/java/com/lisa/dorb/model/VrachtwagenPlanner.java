package com.lisa.dorb.model;

import com.lisa.dorb.repository.VrachtwagenTypeRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


public class VrachtwagenPlanner implements Serializable {

    private long vrachtwagen_Id;
    private long typ_Id;
    private String kenteken;
    private Date apk;
    private String status;
    private String typ;

    protected VrachtwagenPlanner() {
    }

    public VrachtwagenPlanner(long id, long typ_Id, String kenteken, Date apk, String status, String typ) {
        this.vrachtwagen_Id = id;
        this.typ_Id = typ_Id;
        this.kenteken = kenteken;
        this.apk = apk;
        this.status = status;
        this.typ = typ;
    }

    public String getTyp_Id(){
        return typ_Id+"";
    }

    public String getTyp(){
        return typ;
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
