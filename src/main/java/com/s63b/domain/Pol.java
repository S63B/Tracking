package com.s63b.domain;

import javax.persistence.*;

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
    private double lat;
    private double lng;
    private long timestampMillis;

    public Pol() {}

    public Pol(String licensePlate, double lat, double lng, long timestampMillis) {
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }
}
