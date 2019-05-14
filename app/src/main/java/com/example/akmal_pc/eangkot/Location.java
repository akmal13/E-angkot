package com.example.akmal_pc.eangkot;

public class Location {
    public double latit,longit;


    public Location() {
    }

    public Location(double latit,double longit) {
        this.latit = latit;
        this.longit = longit;

    }

    public double getLatit() {
        return latit;
    }

    public void setLatit(float latit) {
        this.latit = latit;
    }

    public double getLongit() {
        return longit;
    }

    public void setLongit(float longit) {
        this.longit = longit;
    }
}
