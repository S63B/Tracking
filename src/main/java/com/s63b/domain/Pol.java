package com.s63b.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by bramd on 7-3-2017.
 */
@Entity
@NamedQuery(name = "Pol.getPolls", query = "SELECT pol FROM Pol AS pol WHERE licensePlate = :licensePlate")
public class Pol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String licensePlate;
    private float lat;
    private float lng;
    private long timestampMillis;

    public Pol() {}

    public Pol(String licensePlate, float lat, float lng, long timestampMillis) {
        this.licensePlate = licensePlate;
        this.lat = lat;
        this.lng = lng;
        this.timestampMillis = timestampMillis;
    }
    
}
