package com.s63b.domain;

import javax.persistence.*;

/**
 * Created by bramd on 7-3-2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Pol.getPolls", query = "SELECT pol FROM Pol AS pol WHERE licensePlate = :licensePlate"),
        @NamedQuery(name = "Pol.getPollsBetween", query = "SELECT pol FROM Pol AS pol WHERE licensePlate = :licensePlate AND timestampMillis BETWEEN :startDate AND :endDate")
})
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

    public String getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }
}
