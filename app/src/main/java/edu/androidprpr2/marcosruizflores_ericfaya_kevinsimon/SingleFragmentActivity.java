package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.databinding.ActivitySinglefragmentactivityBinding;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;

public class SingleFragmentActivity extends AppCompatActivity implements PokedexDao.PokedexCallback{
    private PokedexFragment pokedexFragment;
    private PokedexDao pokedexDao;

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

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == POKEDEX_ITEM_ID) {
                //ArrayList<Pokedex> l = pokedexDao.getPokemonList();
                //System.out.println(l.get(0).getName() + l.get(0).getBackImage());
                pokedexDao.getPokemonList(1);

            } else if (id == ENTRENADOR_ITEM_ID) {
                replaceFragment(new EntrenadorFragment());
            } else if (id == TENDA_ITEM_ID) {
                replaceFragment(new TendaFragment());
            }
            return true;
        });
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
        pokedexFragment = new PokedexFragment(pokedexList);
        replaceFragment(pokedexFragment);
    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}