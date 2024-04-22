package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.databinding.ActivitySinglefragmentactivityBinding;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;

public class SingleFragmentActivity extends AppCompatActivity {
    private PokedexFragment pokedexFragment;

    private static final int POKEDEX_ITEM_ID = R.id.poked_button;
    private static final int ENTRENADOR_ITEM_ID = R.id.entrenador_button;
    private static final int TENDA_ITEM_ID = R.id.tenda_button;
      ActivitySinglefragmentactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySinglefragmentactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_singlefragmentactivity);

        /*ArrayList<Pokedex> arrayList= new ArrayList<>();
        Pokedex pokedex= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE
        Pokedex pokedex1= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE
        Pokedex pokedex2= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE

        arrayList.add(pokedex);//TODO HARDCODED TREURE
        arrayList.add(pokedex1);//TODO HARDCODED TREURE
        arrayList.add(pokedex2); //TODO HARDCODED TREURE

        makeRequest();

        replaceFragment(new PokedexFragment(arrayList));*/
        //replaceFragment(new PokedexFragment()); //TODO si volem que començi en el fragment de pokedex
        //pokedexFragment = new PokedexFragment();
        //replaceFragment(pokedexFragment);
        //  makeRequest();
        //makeRequest2();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            /*switch (item.getItemId()) {
                case R.id.poked_button:
                    ArrayList<Pokedex> arrayList2= new ArrayList<>();
                    Pokedex pokedex3= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE
                    Pokedex pokedex4= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE
                    Pokedex pokedex5= new Pokedex("Hola","Hola2");//TODO HARDCODED TREURE

                    arrayList2.add(pokedex3);//TODO HARDCODED TREURE
                    arrayList2.add(pokedex4);//TODO HARDCODED TREURE
                    arrayList2.add(pokedex5); //TODO HARDCODED TREURE

                    //makeRequest();

                    replaceFragment(new PokedexFragment(arrayList2));
                    break;

                case R.id.entrenador_button:
                    replaceFragment(new EntrenadorFragment());
                         break;
                case R.id.tenda_button:
                    replaceFragment(new TendaFragment());

                    break;

            }
            return true;
        });
                    */

            int id = item.getItemId();
            if (id == R.id.poked_button) {
                ArrayList<Pokedex> arrayList2 = new ArrayList<>();
                Pokedex pokedex3 = new Pokedex("Hola", "Hola2");//TODO HARDCODED TREURE
                Pokedex pokedex4 = new Pokedex("Hola", "Hola2");//TODO HARDCODED TREURE
                Pokedex pokedex5 = new Pokedex("Hola", "Hola2");//TODO HARDCODED TREURE

                arrayList2.add(pokedex3);//TODO HARDCODED TREURE
                arrayList2.add(pokedex4);//TODO HARDCODED TREURE
                arrayList2.add(pokedex5); //TODO HARDCODED TREURE

                //makeRequest();

                replaceFragment(new PokedexFragment(arrayList2));
            } else if (id == R.id.entrenador_button) {
                makeRequest();
                replaceFragment(new EntrenadorFragment());

            } else if (id == R.id.tenda_button) {
                replaceFragment(new TendaFragment());
            }
        return true;
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
    private void makeRequest2() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://pokeapi.co/api/v2/pokemon";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                );

        queue.add(jsonObjectRequest);
    }
    private void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
       // String url ="https://pokeapi.co/api/v2/pokemon";
        String url ="https://api.imgflip.com/get_memes";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Pokedex> pokedexArrayList = parseResponse(response);
                        replaceFragment(new PokedexFragment(pokedexArrayList));
                        //pokedexFragment.updatePokedex(pokedexArrayList);
                        //   ArrayList<Pokedex> pokedexArrayList = parseResponse(response);
                        for (Pokedex pokedex : pokedexArrayList) {
                            Log.d("Pokemon", pokedex.getName());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("resposta", "Hi ha hagut un error:" + volleyError);

                    }
                }
                );
        queue.add(jsonObjectRequest);
    }

    private ArrayList<Pokedex> parseResponse(JSONObject response) {
        ArrayList<Pokedex> pokedexArrayList = new ArrayList<>();
        try {
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject pokemonObject = results.getJSONObject(i);
                String name = pokemonObject.getString("name");
                String url = pokemonObject.getString("url");
                Pokedex pokedex = new Pokedex(name, url);
                pokedexArrayList.add(pokedex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pokedexArrayList;
    }

}