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
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
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

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == POKEDEX_ITEM_ID) {
                //ArrayList<Pokedex> l = pokedexDao.getPokemonList();
                //System.out.println(l.get(0).getName() + l.get(0).getBackImage());
                pokedexDao.getPokemonList(page++);
            } else if (id == ENTRENADOR_ITEM_ID) {
                replaceFragment(new EntrenadorFragment());
            } else if (id == TENDA_ITEM_ID) {
                replaceFragment(new TendaFragment());
            }
            return true;
        });
    }

    private void initializeJsonFile() {
        JSONObject datosEntrenador = new JSONObject();
        try {
            datosEntrenador.put("Money",500000);
            datosEntrenador.put("Name","Juanjo Eljambo");
            datosEntrenador.put("Pokeballs",0);
            datosEntrenador.put("Superballs",0);
            datosEntrenador.put("Ultraballs",0);
            datosEntrenador.put("Masterballs",0);

            JSONArray pokemonCapturadosArray = new JSONArray(); // Hardcodejem dos pokemons per veure

            /*PokemonCapturado pokemon1 = new PokemonCapturado("Pikachu", "pikachu_front_image.png", "pikachu_pokeball_image.png");
            PokemonCapturado pokemon2 = new PokemonCapturado("Charmander", "charmander_front_image.png", "charmander_pokeball_image.png");

            try {
                pokemonCapturadosArray.put(new JSONObject()
                        .put("name", pokemon1.getName())
                        .put("frontImage", pokemon1.getFrontImage())
                        .put("capturedPokeballImage", pokemon1.getCapturedPokeballImage()));

                pokemonCapturadosArray.put(new JSONObject()
                        .put("name", pokemon2.getName())
                        .put("frontImage", pokemon2.getFrontImage())
                        .put("capturedPokeballImage", pokemon2.getCapturedPokeballImage()));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            // Agregem l'array de Pokemons capturats al JSON principal
            datosEntrenador.put("PokemonCapturados", pokemonCapturadosArray);

            File dir = new File(getFilesDir(), "Files");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir, "entrenador.json");//Creem l'arxiu data.json en el directori Files
            try (FileOutputStream fos = new FileOutputStream(file)) {

                fos.write(datosEntrenador.toString(2).getBytes());
                Log.d(TAG, "JSON guardado en " + file.getAbsolutePath());

                //Log.d(TAG, "JSON guardado en " + getFilesDir() + "/" + file.getName());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(getFilesDir(), "Files/entrenador.json");
        Log.d(TAG, "Lecturan " + readFile(file)) ;
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
