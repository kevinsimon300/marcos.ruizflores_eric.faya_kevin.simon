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
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Stat;

public class PokedexDao {
    private final Context context;
    private final PokedexCallback callback;
    private final RequestQueue queue;

    public interface PokedexCallback {
        void onSuccess(ArrayList<Pokemon> pokedexList);
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

                            ArrayList<Pokemon> pokemonList = processPokemonData(response);
                            callback.onSuccess(pokemonList);

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
    private ArrayList<Pokemon> processPokemonData(JSONObject pokemonData) throws JSONException {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();

        // Process JSON data and create Pokedex objects
        JSONArray results = pokemonData.getJSONArray("results");
        for (int i = 0; i <= 1 && i < results.length(); i++) { // Limit the number of Pokedex objects
            JSONObject pokemon = results.getJSONObject(i);
            String name = pokemon.getString("name");
            String url = pokemon.getString("url");
            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
                    detailResponse -> {
                        try {
                            int id = detailResponse.getInt("id");
                            String weight = String.valueOf(detailResponse.getDouble("weight")) + " KG";
                            String height = String.valueOf(detailResponse.getDouble("height")) + " M";

                            String stat0 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(0).getInt("base_stat"));
                            String stat1 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(1).getInt("base_stat"));
                            String stat2 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(2).getInt("base_stat"));
                            String stat3 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(3).getInt("base_stat"));
                            String stat4 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(4).getInt("base_stat"));
                            String stat5 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(5).getInt("base_stat"));

                            String front = detailResponse.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default");
                            String back = detailResponse.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("back_default"); // getting the pictures
                            List<String> types = new ArrayList<>();
                            JSONArray typesArray = detailResponse.getJSONArray("types");
                            for (int j = 0; j < typesArray.length(); j++) {
                                types.add(typesArray.getJSONObject(j).getJSONObject("type").getString("name"));
                            }
                            pokemonList.add(new Pokemon(name, id, front, back, types, weight, height, "Description", stat0, stat1, stat2, stat3, stat4, stat5));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );
        }
        return pokemonList;
    }
}