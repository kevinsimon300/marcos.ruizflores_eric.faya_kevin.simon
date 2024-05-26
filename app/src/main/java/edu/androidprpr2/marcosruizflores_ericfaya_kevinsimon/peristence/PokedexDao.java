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

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
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

        JSONArray results = pokemonData.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject pokemon = results.getJSONObject(i);
            String name = pokemon.getString("name");
            String url = pokemon.getString("url");

            String url2 = "https://pokeapi.co/api/v2/pokemon-species/" + name;
            JsonObjectRequest requestDetail2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                    detailResponse -> {
                        try {
                            String description = detailResponse.getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text");

                            int indexEvolution = 1;
                            if (!detailResponse.isNull("evolves_from_species")) {
                                indexEvolution = 2;
                                String evolvesFromSpecies = detailResponse.getJSONObject("evolves_from_species").getString("name");
                                if (!evolvesFromSpecies.isEmpty()) {
                                    indexEvolution = 3;
                                }
                            }

                            boolean isLegendary = detailResponse.getBoolean("is_legendary");
                            if (isLegendary) {
                                indexEvolution = 4;
                            }

                            int finalIndexEvolution = indexEvolution;
                            int finalIndexEvolution1 = indexEvolution;
                            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
                                    response -> {
                                        try {
                                            int id = response.getInt("id");
                                            String weight = String.valueOf(response.getDouble("weight")) + " KG";
                                            String height = String.valueOf(response.getDouble("height")) + " M";

                                            String stat0 = String.valueOf(response.getJSONArray("stats").getJSONObject(0).getInt("base_stat"));
                                            String stat1 = String.valueOf(response.getJSONArray("stats").getJSONObject(1).getInt("base_stat"));
                                            String stat2 = String.valueOf(response.getJSONArray("stats").getJSONObject(2).getInt("base_stat"));
                                            String stat3 = String.valueOf(response.getJSONArray("stats").getJSONObject(3).getInt("base_stat"));
                                            String stat4 = String.valueOf(response.getJSONArray("stats").getJSONObject(4).getInt("base_stat"));
                                            String stat5 = String.valueOf(response.getJSONArray("stats").getJSONObject(5).getInt("base_stat"));

                                            String back = response.getJSONObject("sprites").getString("back_default");
                                            String front = response.getJSONObject("sprites").getString("front_default");
                                            String backShiny = response.getJSONObject("sprites").getString("back_shiny");
                                            String frontShiny = response.getJSONObject("sprites").getString("front_shiny");

                                            List<String> types = new ArrayList<>();
                                            JSONArray typesArray = response.getJSONArray("types");
                                            for (int j = 0; j < typesArray.length(); j++) {
                                                types.add(typesArray.getJSONObject(j).getJSONObject("type").getString("name"));
                                            }

                                            ArrayList<Ability> abilities = new ArrayList<>();
                                            JSONArray abilitiesArray = response.getJSONArray("abilities");
                                            for (int j = 0; j < abilitiesArray.length(); j++) {
                                                JSONObject abilityObject = abilitiesArray.getJSONObject(j).getJSONObject("ability");
                                                String abilityName = abilityObject.getString("name");
                                                boolean isHidden = abilitiesArray.getJSONObject(j).getBoolean("is_hidden");
                                                double probability = 0.25;
                                                if (!isHidden) probability = 0.50;
                                                Ability ability = new Ability(abilityName, isHidden, probability);
                                                abilities.add(ability);
                                            }

                                            Pokemon newPokemon = new Pokemon(name, id, front, back, types, weight, height, description, stat0, stat1, stat2, stat3, stat4, stat5, abilities, backShiny, frontShiny, finalIndexEvolution1);
                                            pokemonList.add(newPokemon);

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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            );
            queue.add(requestDetail2);
        }
        return pokemonList;
    }

}