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

public class PokedexDao {
    private final Context context;
    private final PokedexCallback callback;
    private final RequestQueue queue; // Cambiado a final

    public interface PokedexCallback {
        void onSuccess(ArrayList<Pokemon> pokedexList);
        void onError(String errorMessage);
    }

    public PokedexDao(Context context, PokedexCallback callback) {
        this.context = context;
        this.callback = callback;
        this.queue = Volley.newRequestQueue(context); // Usar una única cola para todas las solicitudes
    }

    public void getPokemonList(int page) {
        String url = "https://pokeapi.co/api/v2/pokemon/?limit=15&page=" + page;

        Log.d("PokedexDao", "Requesting Pokemon list from: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener
                        <JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("PokedexDao", "SUCCESS JODER: ");

                            // Procesar los datos de los Pokemon
                            ArrayList<Pokemon> pokemonList = processPokemonData(response);

                            // Agregar registro para mostrar el tamaño del array antes de pasarlo a través del callback
                            Log.d("PokedexDao", "Size of Pokemon list de marcos: " + pokemonList.size());

                            // Pasar el array de Pokemon al callback
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
        for (int i = 0; i < results.length(); i++) { // Removida la limitación de cantidad de objetos Pokedex
            JSONObject pokemon = results.getJSONObject(i);
            String name = pokemon.getString("name");
            String url = pokemon.getString("url");
            Log.d("Pokemon Processing", "Processing " + name + " at URL: " + url);

            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
                    detailResponse -> {
                        try {
                            int id = detailResponse.getInt("id");
                            Log.d("Id", "Id: "+String.valueOf(id));
                            String weight = String.valueOf(detailResponse.getDouble("weight")) + " KG";
                            Log.d("Weight", "Weight: "+String.valueOf(weight));
                            String height = String.valueOf(detailResponse.getDouble("height")) + " M";
                            Log.d("Height", "Height: "+String.valueOf(height));
                            //String description = String.valueOf(detailResponse.getString("description"));

                            String stat0 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(0).getInt("base_stat"));
                            String stat1 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(1).getInt("base_stat"));
                            String stat2 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(2).getInt("base_stat"));
                            String stat3 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(3).getInt("base_stat"));
                            String stat4 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(4).getInt("base_stat"));
                            String stat5 = String.valueOf(detailResponse.getJSONArray("stats").getJSONObject(5).getInt("base_stat"));

                            String back = detailResponse.getJSONObject("sprites").getString("back_default"); // getting the pictures

                            String front = detailResponse.getJSONObject("sprites").getString("front_default");

                            List<String> types = new ArrayList<>();
                            JSONArray typesArray = detailResponse.getJSONArray("types");
                            for (int j = 0; j < typesArray.length(); j++) {
                                types.add(typesArray.getJSONObject(j).getJSONObject("type").getString("name"));
                            }

                            for (String type: types) {
                                Log.d("Type", "Type: "+String.valueOf(type));
                            }
                            pokemonList.add(new Pokemon(name, id, front, back, types, weight, height, "description", stat0, stat1, stat2, stat3, stat4, stat5));

                            // Llamar al método onSuccess cuando se haya procesado todos los detalles de los Pokemon
                            if (pokemonList.size() == results.length()) {
                                callback.onSuccess(pokemonList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );
            queue.add(requestDetail);
        }
        //pokemonList.add(new Pokemon("marcos", 12, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png", null, "weight", "height", "Description", "stat0", "stat1", "stat2", "stat3", "stat4", "stat5"));
        return pokemonList;
    }
}