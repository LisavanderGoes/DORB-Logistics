package com.lisa.dorb.model;

public class Pallets {

    String wat;
    long aantal;

    public Pallets(String wat, long aantal){
        this.wat = wat;
        this.aantal = aantal;
    }

    public String getWat(){
        return wat;
    }

    public long getAantal() {
        return aantal;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }

    public void setAantal(long aantal) {
        this.aantal = aantal;
    }
}
