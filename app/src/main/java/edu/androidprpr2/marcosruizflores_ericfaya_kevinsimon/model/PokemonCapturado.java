package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

public class PokemonCapturado {
    private String name;
    private String frontImage;
    private String capturedPokeballImage;

    public PokemonCapturado(String name, String frontImage, String capturedPokeballImage) {
        this.name = name;
        this.frontImage = frontImage;
        this.capturedPokeballImage = capturedPokeballImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getCapturedPokeballImage() {
        return capturedPokeballImage;
    }

    public void setCapturedPokeballImage(String capturedPokeballImage) {
        this.capturedPokeballImage = capturedPokeballImage;
    }
}
