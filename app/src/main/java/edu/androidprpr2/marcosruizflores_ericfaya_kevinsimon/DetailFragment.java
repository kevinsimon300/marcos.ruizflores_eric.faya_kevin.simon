package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonDetail;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {//Que es creei el on create,el fragment,i que es crei la vista,tot lo altre fora

    private ArrayList<Pokemon> pokedexes;
    private Pokemon pokedex;//El pokemon que hem cliquen
    private PokemonDetail pokemon;
    private TextView tvType;
    private TextView tvDescription;
    private TextView tvSkills;
    private TextView tvStats;
    private TextView tvError;
    private Button btnPokeball;
    private Button btnSuperball;
    private Button btnUltraball;
    private Button btnMasterball;
    private Button btnReleasePokemon;
    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;//TODO
    private TextView tvNomPokedex;
    private LinearLayout llBalls;
    private LinearLayout llBalls2;
    private PokemonCapturado pokemonCapturado;

    private static final String TAG = "DetailFragment";

    public DetailFragment(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        this.pokedex = pokedex;
        this.pokedexes = pokedexes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView= inflater.inflate(R.layout.fragment_detail, container, false);
        //ivPokedex = itemView.findViewById(R.id.ivImageFilm); // Initialize ivMovie here

        JSONArray checks = readPokemonCapturadosArrayFromFile();
        Log.d(TAG, "Contenido del JSONArray checks: " + String.valueOf(checks.toString()));

        checkIfPokemonIsCaptured(checks);
        //Log.d(TAG, "Contenido del JSONArray checks: " + String.valueOf(checks.toString()));

        boolean[] pokemonChecks = checkIfPokemonIsCaptured(checks);

        tvNomPokedex = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres

        ivPokedex = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
        imageViewFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);

        tvType = (TextView) itemView.findViewById(R.id.tvType); //El item view es internament el view holder,no es un objecte creat per nosaltres
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        tvStats = (TextView) itemView.findViewById(R.id.tvStats); //El item view es internament el view holder,no es un objecte creat per nosaltres

        tvError = (TextView) itemView.findViewById(R.id.tvError);
        llBalls = (LinearLayout) itemView.findViewById(R.id.llCapturaBall);

        tvNomPokedex.setText(pokedex.getName());
        tvType.setText(pokedex.getTypes().toString());
        tvDescription.setText(pokedex.getDescription());
        //--- Importación de los botones
        btnPokeball = (Button) itemView.findViewById(R.id.Button1_pokeball);
        btnSuperball = (Button) itemView.findViewById(R.id.Button1_superball);
        btnUltraball = (Button) itemView.findViewById(R.id.Button1_ultraball);
        btnMasterball = (Button) itemView.findViewById(R.id.Button1_masterball);
        btnReleasePokemon = (Button) itemView.findViewById(R.id.btnReleasePokemon);

        tvSkills = (TextView) itemView.findViewById(R.id.tvHabilidades); //El item view es internament el view holder,no es un objecte creat per nosaltres
        deletePokemonCapturado("Pikachu");
        File file = new File(requireContext().getFilesDir(), "Files/entrenador.json");

        if (pokemonChecks[0]) {
            tvError.setText("You have already captured 6 pokemons");
            llBalls.setVisibility(View.GONE);
            btnReleasePokemon.setVisibility(View.GONE);
        } else if (pokemonChecks[1]) {
            tvError.setText("You have already captured this pokemon");
            llBalls.setVisibility(View.GONE);
        } else {
            btnReleasePokemon.setVisibility(View.GONE);
        }

        //readFile(file);
        Log.d(TAG, "Lectura desde detail fragment: " + readFile(file));


        /*try {
            if (checks.get(5) != null){
                tvError.setText("You have already captured 6 pokemons");
                llBalls.setVisibility(View.GONE);
            } else if (checks.get(1)!=null){
                tvError.setText("You have already captured this pokemon");
                llBalls.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/

        Random random = new Random();
        int randomAbility = random.nextInt(4) + 1;
        String ability = "";
        if (randomAbility == 1){
            // aqui poner la habilidad oculta
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (int i = 0; i < abilities.size(); i++){
                if (abilities.get(i).getIs_hidden()){
                    ability = abilities.get(i).getName();
                    tvSkills.setText(ability);
                }
            }
        } else {
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (int i = 0; i < abilities.size(); i++){
                if (!abilities.get(i).getIs_hidden()){
                    ability = abilities.get(i).getName();
                    tvSkills.setText(ability);
                }
            }
        }

        int randomNumber = random.nextInt(500) + 1; // Esto generará un número entre 1 y 500 inclusive

        if (randomNumber == 1) {
            // Mostrar imágenes "shiny"
            Picasso.get().load(pokedex.getBack_shiny()).into(ivPokedex);
            Picasso.get().load(pokedex.getFront_shiny()).into(imageViewFront);
        } else {
            // Mostrar imágenes normales
            Picasso.get().load(pokedex.getBackImage()).into(ivPokedex);
            Picasso.get().load(pokedex.getImageUrl()).into(imageViewFront);
        }


        btnPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityPokebals = getFieldValue("Pokeballs");
                if (quantityPokebals > 0 ){
                    int index_evolution = pokedex.getIndex_evolution();
                    int accuracy_pokeball =  (600 - index_evolution) / (600);
                    accuracy_pokeball *= 100;
                    int random_pokeball = random.nextInt(100) + 1;
                    if (accuracy_pokeball < random_pokeball){
                        Toast.makeText(getContext(), "Pokemon Captured", Toast.LENGTH_SHORT).show();
                        modifyJsonFieldValue("Money", 400 + 100 * pokedex.getIndex_evolution());
                        modifyJsonFieldValue("Pokeballs",-1);
                        PokemonCapturado pokemonCapturado = new PokemonCapturado(pokedex.getName(), pokedex.getImageUrl(), "@drawable/pokeball_pokemon_svgrepo_com");
                        addPokemonCapturado(pokemonCapturado);
                        //deletePokemonCapturado("Pikachu");
                    } else Toast.makeText(getContext(), "Pokeball failed", Toast.LENGTH_SHORT).show();
                } // poner mensaje de que tiene que comprar mas pokeballs
            }
        });

        btnSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityPokebals = getFieldValue("Superballs");
                if (quantityPokebals > 0 ){
                    int index_evolution = pokedex.getIndex_evolution();
                    float accuracy_pokeball = (float) ((600 - index_evolution) / (600 * 1.5));
                    accuracy_pokeball *= 100;
                    int random_pokeball = random.nextInt(100) + 1;
                    if (accuracy_pokeball < random_pokeball){
                        Toast.makeText(getContext(), "Pokemon Captured", Toast.LENGTH_SHORT).show();
                        modifyJsonFieldValue("Money", 400 + 100 * pokedex.getIndex_evolution());
                        modifyJsonFieldValue("Superballs",-1);
                        PokemonCapturado pokemonCapturado = new PokemonCapturado(pokedex.getName(), pokedex.getImageUrl(), "@drawable/superball");
                        addPokemonCapturado(pokemonCapturado);
                        //deletePokemonCapturado("Pikachu");
                    } else Toast.makeText(getContext(), "Superball failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityPokeballs = getFieldValue("Ultraballs");
                if (quantityPokeballs > 0 ){
                    int index_evolution = pokedex.getIndex_evolution();
                    int accuracy_pokeball =  (600 - index_evolution) / (600 * 2);
                    accuracy_pokeball *= 100;
                    int random_pokeball = random.nextInt(100) + 1;
                    if (accuracy_pokeball < random_pokeball){
                        Toast.makeText(getContext(), "Pokemon Captured", Toast.LENGTH_SHORT).show();
                        modifyJsonFieldValue("Money", 400 + 100 * pokedex.getIndex_evolution());
                        modifyJsonFieldValue("Ultraballs",-1);
                        PokemonCapturado pokemonCapturado = new PokemonCapturado(pokedex.getName(), pokedex.getImageUrl(), "@drawable/wikiball");
                        addPokemonCapturado(pokemonCapturado);
                        //deletePokemonCapturado("Pikachu");
                    } else Toast.makeText(getContext(), "Ultraball failed", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityPokeballs = getFieldValue("Masterballs");
                if (quantityPokeballs > 0 ){
                    Toast.makeText(getContext(), "Pokemon Captured", Toast.LENGTH_SHORT).show();
                    modifyJsonFieldValue("Money", 400 + 100 * pokedex.getIndex_evolution());
                    modifyJsonFieldValue("Masterballs",-1);
                    PokemonCapturado pokemonCapturado = new PokemonCapturado(pokedex.getName(), pokedex.getImageUrl(), "@drawable/wikiball");
                    addPokemonCapturado(pokemonCapturado);
                    //deletePokemonCapturado("Pikachu");
                }

            }
        });

        btnReleasePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Release Pokemon, elimina el pokemon del JSON
            }
        });

        tvStats.setText("hp: "+pokedex.getStat0() + " \nattack: " + pokedex.getStat1() + " \ndefense: " + pokedex.getStat2()+ " \nspecial-attack: " + pokedex.getStat3() + " \nspecial-defense: " + pokedex.getStat4() + " \nspeed: " + pokedex.getStat5());
        return itemView;
    }

    private boolean[] checkIfPokemonIsCaptured(JSONArray pokemonList) {
        boolean[] captured = new boolean[2];
        if (pokemonList.length() > 6) {
            captured[0] = true;
        }
        try {
            for (int i = 0; i < pokemonList.length(); i++) {
                JSONObject pokemon = pokemonList.getJSONObject(i);
                String name = pokemon.getString("name");
                if (name.equals(pokedex.getName())) {
                    captured[1] = true;
                    break;
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
        return captured;
    }

    private void modifyJsonFieldValue(String fieldName, int incrementValue) {
        File file = new File(requireContext().getFilesDir(), "Files/entrenador.json");

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            int currentValue = datosEntrenador.getInt(fieldName); // Incrementem el valor del camp

            datosEntrenador.put(fieldName, currentValue + incrementValue);


            try (FileOutputStream fos = new FileOutputStream(file)) { // Ho guardem de nou al arxiu JSON
                fos.write(datosEntrenador.toString(2).getBytes());
                Log.d(TAG, "Valor de " + fieldName + " incrementado y JSON guardado en " + file.getAbsolutePath());
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al modificar el archivo JSON", e);
        }

    }
    /*private String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                stringBuilder.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }*/

    private int getFieldValue(String fieldName) {
        File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
        try {
            FileInputStream fis = new FileInputStream(file);// Llegim l'arxiu JSON existent
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

    private void deletePokemonCapturado(String pokemonName) {
        JSONObject datosEntrenador = readJsonFromFile();

        try {
            if (datosEntrenador.has("PokemonCapturados")) {
                JSONArray pokemonCapturadosArray = datosEntrenador.getJSONArray("PokemonCapturados");

            for (int i = 0; i < pokemonCapturadosArray.length(); i++) {
                JSONObject pokemonCapturado = pokemonCapturadosArray.getJSONObject(i);
                String name = pokemonCapturado.getString("name");

                if (name.equals(pokemonName)) {
                    pokemonCapturadosArray.remove(i);
                    break;
                }
            }

            datosEntrenador.put("PokemonCapturados", pokemonCapturadosArray);

            saveJsonToFile(datosEntrenador);
                Log.d(TAG, "PokemonCapturado '" + pokemonName + "' eliminado exitosamente.");
            } else {
                Log.e(TAG, "No se encontró el array PokemonCapturados en el JSON.");
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error al eliminar PokemonCapturado: " + e.getMessage());
        }
    }

    private JSONObject readJsonFromFile() {
        JSONObject jsonObject = new JSONObject();
        try {
            File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
            if (file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                jsonObject = new JSONObject(stringBuilder.toString());
            } else {
                Log.e(TAG, "File not found: entrenador.json");
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error reading JSON file: " + e.getMessage());
        }
        return jsonObject;
    }
        /**
     * Guardem l'objecte JSON actualitzat en l'arxiu JSON
     * @param datosEntrenador
     */
    private void saveJsonToFile(JSONObject datosEntrenador) {
        try {
            File dir = new File(getContext().getFilesDir(), "Files");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir, "entrenador.json");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                String jsonString = datosEntrenador.toString(2);
                fos.write(jsonString.getBytes());
                Log.d(TAG, "JSON guardado en " + file.getAbsolutePath());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar el archivo JSON: " + e.getMessage());
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
        } catch (IOException e)     {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private JSONArray readPokemonCapturadosArrayFromFile() {
        JSONArray pokemonCapturadosArray = new JSONArray();
        try {
            File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
            if (file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                if (jsonObject.has("PokemonCapturados")) {
                    pokemonCapturadosArray = jsonObject.getJSONArray("PokemonCapturados");
                }
            } else {
                Log.e(TAG, "File not found: entrenador.json");
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error reading JSON file: " + e.getMessage());
        }
        return pokemonCapturadosArray;
    }

    private void addPokemonCapturado(PokemonCapturado pokemonCapturado) {
        try {
            JSONObject datosEntrenador = readJsonFromFile();

            JSONArray pokemonCapturadosArray;
            if (datosEntrenador.has("PokemonCapturados")) {
                pokemonCapturadosArray = datosEntrenador.getJSONArray("PokemonCapturados");
            } else {
                pokemonCapturadosArray = new JSONArray();
            }

            JSONObject nuevoPokemonCapturado = new JSONObject();
            nuevoPokemonCapturado.put("name", pokemonCapturado.getName());
            nuevoPokemonCapturado.put("frontImage", pokemonCapturado.getFrontImage());
            nuevoPokemonCapturado.put("capturedPokeballImage", pokemonCapturado.getCapturedPokeballImage());

            pokemonCapturadosArray.put(nuevoPokemonCapturado);

            datosEntrenador.put("PokemonCapturados", pokemonCapturadosArray);

            saveJsonToFile(datosEntrenador);

            Log.d(TAG, "PokemonCapturado '" + pokemonCapturado.getName() + "' añadido exitosamente.");
        } catch (JSONException e) {
            Log.e(TAG, "Error al agregar PokemonCapturado: " + e.getMessage());
        }
    }

}