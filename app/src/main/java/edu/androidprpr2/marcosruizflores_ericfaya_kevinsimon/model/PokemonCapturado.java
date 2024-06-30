package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

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

    public String getFrontImage() {
        return frontImage;
    }

    public String getCapturedPokeballImage() {
        return capturedPokeballImage;
    }

    @Override
    public String toString() {
        return name + "," + frontImage + "," + capturedPokeballImage;
    }

    public static PokemonCapturado fromString(String str) {
        String[] data = str.split(",");
        return new PokemonCapturado(data[0], data[1], data[2]);
    }

    public static ArrayList<PokemonCapturado> getPokemonList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("trainer_prefs", Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt("pokemon_count", 0);
        ArrayList<PokemonCapturado> pokemons = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            String pokemonString = sharedPreferences.getString("pokemon_" + i, null);
            if (pokemonString != null) {
                pokemons.add(PokemonCapturado.fromString(pokemonString));
            }
        }

        return pokemons;
    }

    public static void savePokemonList(Context context, ArrayList<PokemonCapturado> pokemons) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("trainer_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pokemon_count", pokemons.size());

        for (int i = 0; i < pokemons.size(); i++) {
            editor.putString("pokemon_" + i, pokemons.get(i).toString());
        }

        editor.apply();
    }
}
