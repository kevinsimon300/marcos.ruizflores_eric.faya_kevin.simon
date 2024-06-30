package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.databinding.ActivitySinglefragmentactivityBinding;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Entrenador;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.SharedPreferencesDao;

public class SingleFragmentActivity extends AppCompatActivity implements PokedexDao.PokedexCallback{
    private PokedexFragment pokedexFragment;
    private PokedexDao pokedexDao;
    private int page = 0;
    private static final String TAG = "SingleFragmentActivity";
    private static final int POKEDEX_ITEM_ID = R.id.poked_button;
    private static final int ENTRENADOR_ITEM_ID = R.id.entrenador_button;
    private static final int TENDA_ITEM_ID = R.id.tenda_button;
    ActivitySinglefragmentactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySinglefragmentactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pokedexDao = new PokedexDao(this, this);

        initializeTrainerData();
        boolean cargar15 = false;
        pokedexDao.getPokemonList(0, cargar15, 0);

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == POKEDEX_ITEM_ID) {
                pokedexDao.getPokemonList(0, cargar15, 0);
            } else if (id == ENTRENADOR_ITEM_ID) {
                replaceFragment(new EntrenadorFragment());
            } else if (id == TENDA_ITEM_ID) {
                replaceFragment(new TendaFragment());
            }
            return true;
        });
    }


    private void initializeTrainerData() {
        Entrenador entrenador = SharedPreferencesDao.getTrainer(this);
        if (entrenador.getName() == null) {
            List<PokemonCapturado> pokemonCapturados = new ArrayList<>();
            pokemonCapturados.add(new PokemonCapturado("ditto", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png", "master_ball_icon_icons_com_67545"));

            entrenador = new Entrenador(500000, "Ash Ketchup", 0, 0, 0, 0, pokemonCapturados);
            SharedPreferencesDao.saveTrainer(this, entrenador);
            Log.d(TAG, "Datos iniciales del entrenador guardados en SharedPreferences");
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSuccess(ArrayList<Pokemon> pokedexList) {
        Log.d("SingleFragmentActivity", "onSuccess method called!"); // Agregar este registro

        // Manejar la lista de Pokémon obtenida
        // Por ejemplo, puedes imprimir los nombres de los Pokémon en el log
        pokedexFragment = new PokedexFragment(pokedexList,pokedexDao);
        replaceFragment(pokedexFragment);
    }

    @Override
    public void onError(String errorMessage) {}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
