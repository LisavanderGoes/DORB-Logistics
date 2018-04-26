package com.lisa.dorb.model;

public class StringLongModel {

    String string;
    long _long;

    public StringLongModel(String string, long _long){
        this.string = string;
        this._long = _long;
    }

    public String getString(){
        return string;
    }

    public long get_long() {
        return _long;
    }

    public void setString(String wat) {
        this.string = wat;
    }

    public void set_long(long aantal) {
        this._long = aantal;
    }
}
