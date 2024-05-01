package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private int id;
    private String weight;
    private String height;
    private ArrayList<Stat> stats;
    private String imageUrl;
    private String backImage;
    private List<String> types;


    private String description;

    // constrains
    public Pokemon(String name, int id, String imageUrl, String backImage, List<String> types, String weight, String height, ArrayList<Stat> stats) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.backImage = backImage;
        this.types = types;
        this.description = description;
        this.weight = weight;
        this.height = height;
        this.stats = stats;

    }

    // getters

    public String getBackImage() {
        return backImage;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }
    public String getHeight() {
        return height;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

//    public List<String> getStats() {
//        return stats;
//    }

    public String getDescription() {
        return description;
    } // Description is not going to be showed, because apparently the url i use does not have it,
    // but this "https://pokeapi.co/api/v2/pokemon-species/<pokemon_number>/ url contains some cool info about each pokemon and their description in different languages
    // Unfortunately, i did not use it, as i am running out of time, but i am planning on doing that anyway, even after submitting the project in its current state."



}
