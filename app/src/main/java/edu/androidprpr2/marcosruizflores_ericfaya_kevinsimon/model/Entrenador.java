package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import java.util.List;

public class Entrenador {
    private String nameEntrenador;
    private int diners;
    private List<Item> items;
    private List<PokemonDetail> lPokedex;

    public Entrenador(String nameEntrenador, int diners, List<Item> items, List<PokemonDetail> lPokedex) {
        this.nameEntrenador = nameEntrenador;
        this.diners = diners;
        this.items = items;
        this.lPokedex = lPokedex;
    }

    public String getNameEntrenador() {
        return nameEntrenador;
    }

    public void setNameEntrenador(String nameEntrenador) {
        this.nameEntrenador = nameEntrenador;
    }

    public int getDiners() {
        return diners;
    }

    public void setDiners(int diners) {
        if (diners >= 0) {
            this.diners = diners;
        } else {
            throw new IllegalArgumentException("El dinero no puede ser negativo");
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<PokemonDetail> getlPokedex() {
        return lPokedex;
    }

    public void setlPokedex(List<PokemonDetail> lPokedex) {
        this.lPokedex = lPokedex;
    }
    public void addItem(Item item) {
        items.add(item);
    }
}
