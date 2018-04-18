package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long order_Id;

    @Column(name = "klant_Id")
    private long klant_Id;

    @Column(name = "order")
    private String adres;

    @Column(name = "prijs")
    private String prijs;


    @Column(name = "datum")
    private Date datum;

    @Column(name = "pallet_Id")
    private long pallet_Id;

    @Column(name = "rit_Id")
    private long rit_Id;

    @Column(name = "land_Id")
    private long land_Id;

    @Column(name = "palletAantal")
    private long palletAantal;

    protected Order() {
    }

    public Order(long id, long klant_Id, String adres, String prijs, Date datum, long pallet_Id, long rit_Id, long land_Id, long palletAantal) {
        this.order_Id = id;
        this.klant_Id = klant_Id;
        this.adres = adres;
        this.prijs = prijs;
        this.datum = datum;
        this.pallet_Id = pallet_Id;
        this.rit_Id = rit_Id;
        this.land_Id = land_Id;
        this.palletAantal = palletAantal;
    }

    public String getKlant_Id(){
        return klant_Id +"";
    }
    public String getAdres(){
        return adres;
    }
    public String getPrijs(){
        return prijs;
    }
    public String getDatum(){
        return datum+"";
    }
    public String getPallet_Id(){
        return pallet_Id+"";
    }
    public String getRit_Id(){
        return rit_Id+"";
    }
    public String getPalletAantal(){
        return palletAantal+"";
    }
    public long getLand_Id(){
        return land_Id;
    }
    public long getID() {
        return order_Id;
    }

    public void setKlant_Id(long rit_Id) {
        this.klant_Id = rit_Id;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setPrijs(String prijs) {
        this.prijs = prijs;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setPallet_Id(long pallet_Id) {
        this.pallet_Id = pallet_Id;
    }
    public void setRit_Id(long rit_id) {
        this.rit_Id = rit_id;
    }
    public void setPalletAantal(long palletAantal) {
        this.palletAantal = palletAantal;
    }
    public void setLand_Id(long land_Id) {
        this.land_Id = land_Id;
    }
}
