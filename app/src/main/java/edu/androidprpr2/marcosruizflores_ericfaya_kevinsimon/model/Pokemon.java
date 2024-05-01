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
    private ArrayList<Stat> stats;
    private String imageUrl;
    private String backImage;
    private List<String> types;


    private String description;


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



    public String getDescription() {
        return description;
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

        intent.putParcelableArrayListExtra("stats", stats);

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
