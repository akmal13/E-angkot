package com.example.akmal_pc.eangkot;

public class Ride {
    public double latawal,longawal;
    String Iddriver;
    String Idcustomer;
    Boolean end;

    public Ride() {
    }

    public Ride(double latawal,double longawal,String idcustomer,String iddriver,Boolean end) {
        this.latawal = latawal;
        this.longawal = longawal;
        this.Iddriver = iddriver;
        this.Idcustomer = idcustomer;
        this.end = end;
    }

    public double getLatawal() {
        return latawal;
    }

    public void setLatawal(double latawal) {
        this.latawal = latawal;
    }

    public double getLongawal() {
        return longawal;
    }

    public void setLongawal(double longawal) {
        this.longawal = longawal;
    }

    public String getIddriver() {
        return Iddriver;
    }

    public void setIddriver(String iddriver) {
        Iddriver = iddriver;
    }

    public String getIdcustomer() {
        return Idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
        Idcustomer = idcustomer;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }
}


