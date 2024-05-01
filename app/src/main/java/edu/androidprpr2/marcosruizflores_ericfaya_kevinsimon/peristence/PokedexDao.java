package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;

public class PokedexDao {
    private final Context context;
    private final PokedexCallback callback;
    private final RequestQueue queue;

    public interface PokedexCallback {
        void onSuccess(ArrayList<Pokedex> pokedexList);
        void onError(String errorMessage);
    }
    /*public PokedexDao(){
        this.queue = Volley.newRequestQueue(context);

    }*/
    public PokedexDao(Context context, PokedexCallback callback) {
        this.context = context;
        this.callback = callback;
        this.queue = Volley.newRequestQueue(context);
    }

    public void getPokemonList(int page) {
        String url = "https://pokeapi.co/api/v2/pokemon/?limit=15&page=" + page;

        //String url = "https://pokeapi.co/api/v2/pokemon/";
        Log.d("PokedexDao", "Requesting Pokemon list from: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener
                        <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("PokedexDao", "SUCCESS JODER: ");

                            ArrayList<Pokedex> pokedexList = processPokemonData(response);
                            callback.onSuccess(pokedexList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onError("Error en la respuesta HTTP: " + error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }
    private ArrayList<Pokedex> processPokemonData(JSONObject pokemonData) throws JSONException {
        ArrayList<Pokedex> pokedexList = new ArrayList<>();

        // Process JSON data and create Pokedex objects
        JSONArray results = pokemonData.getJSONArray("results");
        for (int i = 0; i < 15 && i < results.length(); i++) { // Limit the number of Pokedex objects
            JSONObject pokemon = results.getJSONObject(i);
            String name = pokemon.getString("name");
            String url = pokemon.getString("url");
            JSONObject sprites = pokemonData.getJSONObject("sprites");

            String frontUrl = sprites.getString("front_default");
            String backUrl = sprites.getString("back_default");


        }

        return pokedexList;
    }

}
