package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;

public class SingleFragmentActivity extends AppCompatActivity {
    private PokedexDao pokedexDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlefragmentactivity);
        pokedexDao = new PokedexDao(this, new PokedexDao.PokedexCallback() {
            @Override
            public void onSuccess(ArrayList<Pokedex> pokedexList) {
                replaceFragment(new PokedexFragment(pokedexList));
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("PokedexDao", errorMessage);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.poked_button) {
                replaceFragment(new PokedexFragment(pokedexDao.getPokemonList()));
            } else if (id == R.id.entrenador_button) {
                replaceFragment(new EntrenadorFragment());
            } else if (id == R.id.tenda_button) {
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
}
