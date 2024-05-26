package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pokedex {
    private static  Pokedex sPokedexes;
    private ArrayList<Pokemon> pokemonsList;

    public static Pokedex getInstance(Context context, ArrayList<Pokemon> pokemonsList) {
        if (pokemonsList == null) {
            sPokedexes = new Pokedex(context, pokemonsList);
        }
        return sPokedexes;
    }

    private Pokedex(Context context, ArrayList<Pokemon> pokemonsList) {
        this.pokemonsList = pokemonsList;
    }

    /*
    public Pokedex(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }
     */

    public ArrayList<Pokemon> getPokemonsList() {
        return pokemonsList;
    }
}
