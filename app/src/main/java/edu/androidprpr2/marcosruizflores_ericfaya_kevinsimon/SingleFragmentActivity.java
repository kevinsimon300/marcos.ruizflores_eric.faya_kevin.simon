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

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.databinding.ActivitySinglefragmentactivityBinding;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Entrenador;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;

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

        initializeJsonFile();
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

    private void initializeJsonFile() {
        try {
            File dir = new File(getFilesDir(), "Files");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir, "entrenador.json");

            JSONObject datosEntrenador;

            if (!file.exists()) {
                JSONObject defaultData = new JSONObject();
                defaultData.put("Money", 500000);
                defaultData.put("Name", "Jarrambo el mas jambo");
                defaultData.put("Pokeballs", 0);
                defaultData.put("Superballs", 0);
                defaultData.put("Ultraballs", 0);
                defaultData.put("Masterballs", 0);

                JSONArray pokemonCapturadosArray = new JSONArray();
                PokemonCapturado pokemon1 = new PokemonCapturado("ditto", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png", "master_ball_icon_icons_com_67545");
                pokemonCapturadosArray.put(new JSONObject()
                        .put("name", pokemon1.getName())
                        .put("frontImage", pokemon1.getFrontImage())
                        .put("capturedPokeballImage", pokemon1.getCapturedPokeballImage()));

                defaultData.put("PokemonCapturados", pokemonCapturadosArray);

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(defaultData.toString(2).getBytes());
                    Log.d(TAG, "JSON inicial creado en " + file.getAbsolutePath());
                }
            } else {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();

                String jsonString = new String(data, "UTF-8");
                datosEntrenador = new JSONObject(jsonString);

                if (!datosEntrenador.has("Money")) {
                    datosEntrenador.put("Money", 700000);
                }
                if (!datosEntrenador.has("Name")) {
                    datosEntrenador.put("Name", "Jarrambo el mas jambo");
                }
                if (!datosEntrenador.has("Pokeballs")) {
                    datosEntrenador.put("Pokeballs", 0);
                }
                if (!datosEntrenador.has("Superballs")) {
                    datosEntrenador.put("Superballs", 0);
                }
                if (!datosEntrenador.has("Ultraballs")) {
                    datosEntrenador.put("Ultraballs", 0);
                }
                if (!datosEntrenador.has("Masterballs")) {
                    datosEntrenador.put("Masterballs", 0);
                }

                JSONArray pokemonCapturadosArray;
                if (datosEntrenador.has("PokemonCapturados")) {
                    pokemonCapturadosArray = datosEntrenador.getJSONArray("PokemonCapturados");
                } else {
                    pokemonCapturadosArray = new JSONArray();
                }

                if (pokemonCapturadosArray.length() == 0) {
                    PokemonCapturado pokemon1 = new PokemonCapturado("ditto", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png", "master_ball_icon_icons_com_67545");
                    pokemonCapturadosArray.put(new JSONObject()
                            .put("name", pokemon1.getName())
                            .put("frontImage", pokemon1.getFrontImage())
                            .put("capturedPokeballImage", pokemon1.getCapturedPokeballImage()));
                }

                datosEntrenador.put("PokemonCapturados", pokemonCapturadosArray);

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(datosEntrenador.toString(2).getBytes());
                    Log.d(TAG, "JSON actualizado en " + file.getAbsolutePath());
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
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
    public void onError(String errorMessage) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private int getFieldValue(String fieldName) {
        File file = new File(getFilesDir(), "Files/entrenador.json");
        try {
            FileInputStream fis = new FileInputStream(file);// Leer l'arxiu JSON existent
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            if (datosEntrenador.has(fieldName)) {// Obtenir el valor del camp
                return datosEntrenador.getInt(fieldName);
            } else {
                Log.e(TAG, "El campo " + fieldName + " no existe en el JSON");
                return -1;  // O qualsevol valor que consideres apropiat per indicar un error
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al leer el archivo JSON", e);
            return -1;
        }
    }

    public String readFile(File filename) {
        StringBuilder stringBuilder = new StringBuilder();
        //try (FileInputStream fis = openFileInput(filename)) {
        try (FileInputStream fis = new FileInputStream(filename)) {

            int ch;
            while ((ch = fis.read()) != -1) {
                stringBuilder.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
