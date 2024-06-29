package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import java.util.List;

public class Entrenador {
    private int money;
    private String name;
    private int pokeballs;
    private int superballs;
    private int ultraballs;
    private int masterballs;
    private List<PokemonCapturado> pokemonCapturados;

    public Entrenador(int money, String name, int pokeballs, int superballs, int ultraballs, int masterballs, List<PokemonCapturado> pokemonCapturados) {
        this.money = money;
        this.name = name;
        this.pokeballs = pokeballs;
        this.superballs = superballs;
        this.ultraballs = ultraballs;
        this.masterballs = masterballs;
        this.pokemonCapturados = pokemonCapturados;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(int pokeballs) {
        this.pokeballs = pokeballs;
    }

    public int getSuperballs() {
        return superballs;
    }

    public void setSuperballs(int superballs) {
        this.superballs = superballs;
    }

    public int getUltraballs() {
        return ultraballs;
    }

    public void setUltraballs(int ultraballs) {
        this.ultraballs = ultraballs;
    }

    public int getMasterballs() {
        return masterballs;
    }

    public void setMasterballs(int masterballs) {
        this.masterballs = masterballs;
    }

    public List<PokemonCapturado> getPokemonCapturados() {
        return pokemonCapturados;
    }

    public void setPokemonCapturados(List<PokemonCapturado> pokemonCapturados) {
        this.pokemonCapturados = pokemonCapturados;
    }
}
