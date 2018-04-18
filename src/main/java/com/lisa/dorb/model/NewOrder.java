package com.lisa.dorb.model;

import java.io.Serializable;


public class NewOrder implements Serializable {

    Order order;
    Rit rit;

    public NewOrder(Order order, Rit rit){
        this.order = order;
        this.rit = rit;
    }

    public Order getOrder(){
        return order;
    }

    public Rit getRit() {
        return rit;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRit(Rit rit) {
        this.rit = rit;
    }
}
