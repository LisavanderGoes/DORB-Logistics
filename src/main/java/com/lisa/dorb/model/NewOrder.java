package com.lisa.dorb.model;

import com.lisa.dorb.model.db.Order;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.model.db.Rit;

import java.io.Serializable;
import java.util.List;


public class NewOrder implements Serializable {

    Order order;
    Rit rit;
    List<Pallet> pallet;

    public NewOrder(Order order, Rit rit, List<Pallet> pallet){
        this.order = order;
        this.rit = rit;
        this.pallet = pallet;
    }

    public Order getOrder(){
        return order;
    }

    public Rit getRit() {
        return rit;
    }

    public List<Pallet> getPallet() {
        return pallet;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRit(Rit rit) {
        this.rit = rit;
    }

    public void setPallet(List<Pallet> pallet) {
        this.pallet = pallet;
    }
}
