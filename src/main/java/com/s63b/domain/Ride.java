package com.s63b.domain;

import java.util.ArrayList;
import java.util.List;

public class Ride {

    private long startDate;
    private long endDate;

    private List<Pol> pols;

    private long distance;

    public Ride(){
        this.pols = new ArrayList<>();
    }

    public boolean addPol(Pol pol){
        return this.pols.add(pol);
    }

    public List<Pol> getPols() {
        return pols;
    }

    public void setPols(List<Pol> pols) {
        this.pols = pols;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }
}
