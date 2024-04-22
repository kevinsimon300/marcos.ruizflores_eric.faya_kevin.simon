package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.widget.ImageView;

public class Pokedex {
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;

    private String name;

    private String thumbnail;

    public Pokedex() {
    }

    public Pokedex(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public ImageView transformImageView(Context context, String imageUrl) {
        ImageView imageView = new ImageView(context);
        //Picasso.get().load(imageUrl).into(imageView);
        return imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
