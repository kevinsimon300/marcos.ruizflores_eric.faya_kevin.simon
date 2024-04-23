package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

public class Ability {
    private String name;
    private double probability;

    public Ability(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
