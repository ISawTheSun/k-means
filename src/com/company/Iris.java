package com.company;

import java.util.List;

public class Iris {
    private List<Double> Atrybuty;
    private String AtrybutDecyzyjny = "Default";
    private Cluster cluster;

    public Iris(List<Double> atrybuty, String atrybutDecyzyjny) {
        Atrybuty = atrybuty;
        AtrybutDecyzyjny = atrybutDecyzyjny;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Iris(List<Double> atrybuty) {
        Atrybuty = atrybuty;
    }

    public List<Double> getAtrybuty() {
        return Atrybuty;
    }

    public String getAtrybutDecyzyjny() {
        return AtrybutDecyzyjny;
    }

    @Override
    public String toString() {
        return Atrybuty + " -- " + AtrybutDecyzyjny+'\n';
    }
}

