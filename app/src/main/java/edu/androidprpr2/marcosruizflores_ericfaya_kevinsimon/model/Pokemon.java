package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.content.Intent;


import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.DetailFragment;

public class Pokemon {
    private String name;
    private int id;
    private String weight;
    private String height;
    private String stat0;
    private String stat1;
    private String stat2;
    private String stat3;
    private String stat4;
    private String stat5;
    private String imageUrl;
    private String backImage;
    private List<String> types;
    private String description;
    private ArrayList<Ability> abilities;
    private String back_shiny;
    private String front_shiny;
    private int index_evolution;
    private String pokeballType;


    public Pokemon(String name, int id, String imageUrl, String backImage, List<String> types, String weight, String height, String description, String stat0, String stat1, String stat2, String stat3, String stat4, String stat5, ArrayList<Ability> abilities, String back_shiny, String front_shiny, int index_evolution, String pokeballType) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.backImage = backImage;
        this.types = types;
        this.description = description;
        this.weight = weight;
        this.height = height;
        this.stat0 = stat0;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
        this.abilities = abilities;
        this.back_shiny = back_shiny;
        this.front_shiny = front_shiny;
        this.index_evolution = index_evolution;
        this.pokeballType = pokeballType;
    }

    public int getIndex_evolution() {
        return index_evolution;
    }

    public void setIndex_evolution(int index_evolution) {
        this.index_evolution = index_evolution;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public void setBack_shiny(String back_shiny) {
        this.back_shiny = back_shiny;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

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
    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public String getStat0() {
        return stat0;
    }

    public String getStat1() {
        return stat1;
    }

    public String getStat2() {
        return stat2;
    }

    public String getStat3() {
        return stat3;
    }

    public String getStat4() {
        return stat4;
    }

    public String getStat5() {
        return stat5;
    }

    public String getPokeballType() {
        return pokeballType;
    }

    public void setPokeballType(String pokeballType) {
        this.pokeballType = pokeballType;
    }

    public Intent getIntent(Context context) {
        Intent intent = new Intent(context, DetailFragment.class);
        intent.putExtra("name", name);
        intent.putExtra("id", id);
        intent.putExtra("imageUrl", imageUrl);
        intent.putStringArrayListExtra("types", new ArrayList<>(types));
        intent.putExtra("description", description);
        intent.putExtra("weight", weight);
        intent.putExtra("height", height);

        intent.putExtra("Stat0", stat0);
        intent.putExtra("Stat1", stat1);
        intent.putExtra("Stat2", stat2);
        intent.putExtra("Stat3", stat3);
        intent.putExtra("Stat4", stat4);
        intent.putExtra("Stat5", stat5);
        return intent;
    }


    public Intent getPokedex (Context context) {
        Intent intent = new Intent(context, PokemonDetail.class);
        intent.putExtra("name", name);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("backImage", backImage);
        return intent;
    }
}