package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Pokedexes {
    private static  Pokedexes sPokedexes;
    private  List<Pokedex> lPokedex;

    public static Pokedexes getInstance(Context context, ArrayList<Pokedex> pokedexes){
        if(sPokedexes==null){
            sPokedexes = new Pokedexes(context,pokedexes);
        }
        return sPokedexes;
    }
    private Pokedexes(Context context, ArrayList<Pokedex> pokedexes){
        lPokedex = new ArrayList<>();
        for (int i = 0; i < pokedexes.size(); i++) {

            lPokedex.add(pokedexes.get(i));
        }
    }

    public List<Pokedex> getPokedexes() {
        return lPokedex;
    }


}
