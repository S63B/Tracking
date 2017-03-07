package com.s63b.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by bramd on 7-3-2017.
 */
@Entity
public class Pol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String licensePlate;
    private float lat;
    private float lng;
    private long timestampMillis;

    public Pol(String licensePlate, float lat, float lng, long timestampMillis) {
        this.licensePlate = licensePlate;
        this.lat = lat;
        this.lng = lng;
        this.timestampMillis = timestampMillis;
    }

}
