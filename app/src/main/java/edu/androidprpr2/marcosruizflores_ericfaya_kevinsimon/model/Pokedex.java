package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.widget.ImageView;

public class Pokedex {
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;

    private String name;

    private double review;
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

    public String getLengthH(int totalMinutes) {
        int hours = totalMinutes / 60; // Calculate hours
        return String.valueOf(hours)+"h "; // Convert hours to string
    }

    public String getLengthM(int num){
        int hours = num / 60; // Get the whole hours
        int minutes = num % 60; // Get the remaining minutes
        return String.valueOf(minutes)+"min";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getReview() {
        return String.valueOf(review)+"/10";
    }

    public void setReview(double review) {
        this.review = review;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
