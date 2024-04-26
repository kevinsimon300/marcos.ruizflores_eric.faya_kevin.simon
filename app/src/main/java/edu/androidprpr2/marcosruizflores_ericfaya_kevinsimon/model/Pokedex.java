package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Pokedex {

    private String name;

    private String thumbnail;

    private String backImage;
    private String frontImage;

    public Pokedex() {
    }

    public Pokedex(String name, String frontImage, String backImage) {
        this.name = name;
        this.frontImage = frontImage;
        this.backImage = backImage;
    }

    public ImageView transformImageView(Context context, String imageUrl) {
        ImageView imageView = new ImageView(context);
        Picasso.get().load(imageUrl).into(imageView);
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

    public String getBackImage() {
        return backImage;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }
}
