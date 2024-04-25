package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

class Stats {
    private int attack;
    private int defense;
    private int speed;

    public Stats(int attack, int defense, int speed) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}