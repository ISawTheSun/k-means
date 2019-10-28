package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<Double> Atrybuty;
    public List<Iris> Objects;
    private String Name;

    public Cluster(List<Double> atrybuty, String name) {
        Objects = new ArrayList<>();
        Atrybuty = atrybuty;
        Name = name;
    }

    public List<Double> getAtrybuty() {
        return Atrybuty;
    }

    public void clearAtrybuty(){
        Atrybuty.clear();
    }

    public void changeAtrybutyAt(int index, double value){
        Atrybuty.add(index, value);
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "\n"+Name+"("+Atrybuty+")"+ ": {\n" + Objects + "}";
    }
}
