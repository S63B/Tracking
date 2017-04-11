package com.s63b.domain;

import java.util.ArrayList;
import java.util.List;

public class Ride {

    private List<Pol> pols;

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
}
