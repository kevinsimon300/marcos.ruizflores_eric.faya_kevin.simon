package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence;



import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

    public PokedexDao(Context context, PokedexCallback callback) {
        this.context = context;
        this.callback = callback;
        this.queue = Volley.newRequestQueue(context);
    }

    public ArrayList<Pokedex> getPokemonList() {
        String url = "https://pokeapi.co/api/v2/pokemon";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
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
        return null;
    }

    private ArrayList<Pokedex> processPokemonData(JSONObject pokemonData) throws JSONException {
        ArrayList<Pokedex> pokedexList = new ArrayList<>();

        String name = pokemonData.getString("name");
        JSONObject sprites = pokemonData.getJSONObject("sprites");
        String frontUrl = sprites.getString("front_default");
        String backUrl = sprites.getString("back_default");
        // de momento no pongo nada mas en el constructor por si se daña
        Pokedex pokedex = new Pokedex(name, frontUrl);
        pokedexList.add(pokedex);

        return pokedexList;
    }
}
