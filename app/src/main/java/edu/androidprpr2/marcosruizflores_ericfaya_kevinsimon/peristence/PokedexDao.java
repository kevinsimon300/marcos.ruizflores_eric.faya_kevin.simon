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

    public void getPokemonList(int page, boolean cargar15, int countPage) {
        int offset = page * 15;
        String url = cargar15
                ? "https://pokeapi.co/api/v2/pokemon/?offset=" + offset + "&limit=15"
                : "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=" + ((countPage + 1) * 15);

        Log.d("PokedexDao", "Requesting Pokemon list from: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<Pokemon> pokemonList = processPokemonData(response);
                            callback.onSuccess(pokemonList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error procesando los datos del Pokémon: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
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

            fetchPokemonDetails(name, url, pokemonList, results.length());
        }

        return pokemonList;
    }

    private void fetchPokemonDetails(String name, String url, ArrayList<Pokemon> pokemonList, int totalPokemons) {
        String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + name;

        JsonObjectRequest requestDetail2 = new JsonObjectRequest(Request.Method.GET, speciesUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject detailResponse) {
                        try {
                            String description = detailResponse.getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text");
                            int indexEvolution = determineEvolutionIndex(detailResponse);

                            JsonObjectRequest requestDetail = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Pokemon newPokemon = createPokemonFromResponse(response, name, description, indexEvolution);
                                                pokemonList.add(newPokemon);

                                                if (pokemonList.size() == totalPokemons) {
                                                    callback.onSuccess(pokemonList);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    });

                            queue.add(requestDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(requestDetail2);
    }

    private int determineEvolutionIndex(JSONObject detailResponse) throws JSONException {
        int indexEvolution = 1;
        if (!detailResponse.isNull("evolves_from_species")) {
            indexEvolution = 2;
            String evolvesFromSpecies = detailResponse.getJSONObject("evolves_from_species").getString("name");
            if (!evolvesFromSpecies.isEmpty()) {
                indexEvolution = 3;
            }
        }

        if (detailResponse.getBoolean("is_legendary")) {
            indexEvolution = 4;
        }

        return indexEvolution;
    }

    private Pokemon createPokemonFromResponse(JSONObject response, String name, String description, int indexEvolution) throws JSONException {
        int id = response.getInt("id");
        String weight = response.getString("weight") + " KG";
        String height = response.getString("height") + " M";
        String stat0 = response.getJSONArray("stats").getJSONObject(0).getString("base_stat");
        String stat1 = response.getJSONArray("stats").getJSONObject(1).getString("base_stat");
        String stat2 = response.getJSONArray("stats").getJSONObject(2).getString("base_stat");
        String stat3 = response.getJSONArray("stats").getJSONObject(3).getString("base_stat");
        String stat4 = response.getJSONArray("stats").getJSONObject(4).getString("base_stat");
        String stat5 = response.getJSONArray("stats").getJSONObject(5).getString("base_stat");
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
            double probability = isHidden ? 0.25 : 0.50;
            Ability ability = new Ability(abilityName, isHidden, probability);
            abilities.add(ability);
        }

        return new Pokemon(name, id, front, back, types, weight, height, description, stat0, stat1, stat2, stat3, stat4, stat5, abilities, backShiny, frontShiny, indexEvolution, "@drawable/wikiball");
    }

    public void getSearchedPokemon(String name) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + name;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Pokemon searchedPokemon = createPokemonFromResponse(response, name, "", 0);
                            ArrayList<Pokemon> pokemonList = new ArrayList<>();
                            pokemonList.add(searchedPokemon);
                            callback.onSuccess(pokemonList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error procesando los datos del Pokémon: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onError("Error en la respuesta HTTP: " + error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
