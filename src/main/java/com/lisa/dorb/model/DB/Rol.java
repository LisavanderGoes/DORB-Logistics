package com.lisa.dorb.model.DB;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rollen")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_Id")
    private long user_Id;

    @Column(name = "rol")
    private String rol;

    protected Rol() {
    }

    public Rol(long id, long user_Id, String rol) {
        this.id = id;
        this.user_Id = user_Id;
        this.rol = rol;
    }

    public long getUser_Id(){
        return user_Id;
    }
    public String getRol(){
        return rol;
    }
    public long getID() {
        return id;
    }

    public void setUser_Id(long user_Id) {
        this.user_Id = user_Id;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
