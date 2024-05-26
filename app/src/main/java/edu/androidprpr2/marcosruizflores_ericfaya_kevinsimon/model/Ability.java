package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

public class Ability {
    private String name;
    private boolean is_hidden;
    private double probability;

    public Ability(String name, boolean is_hidden, double probability) {
        this.name = name;
        this.is_hidden = is_hidden;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean getIs_hidden(){ return is_hidden;}

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public void setIsHidden (boolean is_hidden){this.is_hidden = is_hidden;}
}
