package com.example.exo2.beans;

public class MarqueCounter {
    private String libelle;
    private int count;

    public MarqueCounter() {
    }

    public MarqueCounter(String libelle, int count) {
        this.libelle = libelle;
        this.count = count;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
