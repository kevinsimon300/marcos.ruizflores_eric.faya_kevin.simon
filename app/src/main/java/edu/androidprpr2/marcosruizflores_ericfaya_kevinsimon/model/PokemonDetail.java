package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetail {
    @SerializedName("name")
    private String namePokemon;
    @SerializedName("sprites")
    private ArrayList<Sprites> sprites;
    private ArrayList<Types> types;

    // faltan añadir los demas


    public PokemonDetail(String namePokemon, ArrayList<Sprites> sprites, ArrayList<Types> types) {
        this.namePokemon = namePokemon;
        this.sprites = sprites;
        this.types = types;
    }

    public String getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(String namePokemon) {
        this.namePokemon = namePokemon;
    }

    public ArrayList<Sprites> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Sprites> sprites) {
        this.sprites = sprites;
    }

    public ArrayList<Types> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }
}
