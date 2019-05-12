package com.example.akmal_pc.Eangkot;

public class Location {
    public double latit,longit;
    public String email;


    public Location() {
    }

    public Location(double latit,double longit, String email) {
        this.latit = latit;
        this.longit = longit;
        this.email = email;

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
