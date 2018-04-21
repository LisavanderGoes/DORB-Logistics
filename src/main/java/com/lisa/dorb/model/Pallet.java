package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pallets")
public class Pallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pallet_Id;

    @Column(name = "order_Id")
    private long order_Id;

    @Column(name = "wat")
    private String wat;

    @Column(name = "aantal")
    private long aantal;

    protected Pallet() {
    }

    public Pallet(long id, long order_Id, String wat, long aantal) {
        this.pallet_Id = id;
        this.order_Id = order_Id;
        this.aantal = aantal;
        this.wat = wat;
    }

    public String getOrder_Id(){
        return order_Id+"";
    }
    public String getAantal(){
        return aantal+"";
    }
    public String getWat(){
        return wat;
    }
    public long getID() {
        return pallet_Id;
    }

    public void setOrder_Id(long order_Id) {
        this.order_Id = order_Id;
    }
    public void setAantal(long aantal) {
        this.aantal = aantal;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }
}
