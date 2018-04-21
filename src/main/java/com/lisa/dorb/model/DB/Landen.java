package com.lisa.dorb.model.DB;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "landen")
public class Landen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long land_Id;

    @Column(name = "land")
    private String land;

    protected Landen() {
    }

    public Landen(long id, String land) {
        this.land_Id = id;
        this.land = land;
    }

    public String getLand(){
        return land;
    }
    public long getID() {
        return land_Id;
    }

    public void setLand(String land) {
        this.land = land;
    }
}
