package com.lisa.dorb.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pallet")
public class Pallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pallet_Id;

    @Column(name = "order_Id")
    private long order_Id;

    @Column(name = "wat")
    private String wat;

    protected Pallet() {
    }

    public Pallet(long id, long order_Id, String wat) {
        this.pallet_Id = id;
        this.order_Id = order_Id;
        this.wat = wat;
    }

    public String getOrder_Id(){
        return order_Id+"";
    }
    public String getWat(){
        return wat;
    }
    public long getID() {
        return pallet_Id;
    }

    public void setPrijs(long order_Id) {
        this.order_Id = order_Id;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }
}
