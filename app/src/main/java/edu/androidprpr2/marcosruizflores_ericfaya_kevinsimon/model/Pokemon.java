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


    public Pokemon(String name, int id, String imageUrl, String backImage, List<String> types, String weight, String height, String description, String stat0, String stat1, String stat2, String stat3, String stat4, String stat5) {
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
