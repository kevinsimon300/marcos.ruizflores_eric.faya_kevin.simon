package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;

public class EntrenadorFragment extends Fragment {
    private TextView tvNameEntrenador;
    private TextView tvMoney;
    private TextView tvPokeball;
    private TextView tvSuperball;
    private TextView tvUltraball;
    private TextView tvMaterball;
    //private Button btnDeletePokemon;
    private Button btnChangeNameTrainer;
    private RecyclerView pokemonRecyclerView;
    private CapturatedPokemonAdapter pokemonAdapter;
    private DetailFragment detailFragment;
    private List<PokemonCapturado> pokemonList = new ArrayList<>();

    private static final String TAG = "EntrenadorFragment";

    public EntrenadorFragment() {

    }

    public static EntrenadorFragment newInstance() {
        EntrenadorFragment fragment = new EntrenadorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrenador, container, false);

        Button btnChangeName = view.findViewById(R.id.btChangeNameTrainer);
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new ChangeNameFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragment = new DetailFragment(null, null);
        //btnDeletePokemon = view.findViewById(R.id.buttonDeletePokemon);
        btnChangeNameTrainer = view.findViewById(R.id.btChangeNameTrainer);
        tvMoney = view.findViewById(R.id.tvTrainerName);
        tvMoney.setText(String.valueOf(getFieldValueName("Name")));
        tvNameEntrenador = view.findViewById(R.id.tvTrainerCash);
        tvNameEntrenador.setText(String.valueOf(getFieldValue("Money")));
        tvPokeball = view.findViewById(R.id.tvPokeballs);
        tvPokeball.setText(String.valueOf(getFieldValue("Pokeballs")));
        tvSuperball = view.findViewById(R.id.tvSuperballs);
        tvSuperball.setText(String.valueOf(getFieldValue("Superballs")));
        tvUltraball = view.findViewById(R.id.tvUltraballs);
        tvUltraball.setText(String.valueOf(getFieldValue("Ultraballs")));
        tvMaterball = view.findViewById(R.id.tvMasterballs);
        tvMaterball.setText(String.valueOf(getFieldValue("Masterballs")));

        pokemonRecyclerView = view.findViewById(R.id.pokemon_recycler_view);
        JSONArray checks = readPokemonCapturadosArrayFromFile();
        pokemonList = new ArrayList<>();
        try {
            for (int i = 0; i < checks.length(); i++) {
                JSONObject pokemonObject = checks.getJSONObject(i);
                PokemonCapturado pokemonCapturado = new PokemonCapturado(
                        pokemonObject.getString("name"),
                        pokemonObject.getString("frontImage"),
                        pokemonObject.getString("capturedPokeballImage")
                );
                pokemonList.add(pokemonCapturado);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON array: " + e.getMessage());
        }

        /*btnDeletePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar el último Pokémon capturado
                if (!pokemonList.isEmpty()) {
                    pokemonList.remove(pokemonList.size() - 1);
                    // Lógica adicional para guardar el nuevo arreglo en el archivo JSON
                    // detailFragment.deletePokemonCapturado(pokemonList.get(pokemonList.size()-1).getName());
                    // actualiza el adaptador
                    pokemonAdapter.notifyDataSetChanged();
                }
            }
        });*/

        pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pokemonAdapter = new CapturatedPokemonAdapter(pokemonList, getContext());
        pokemonRecyclerView.setAdapter(pokemonAdapter);
    }

    private String getFieldValueName(String fieldName) {
        File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            if (datosEntrenador.has(fieldName)) {
                return datosEntrenador.getString(fieldName);
            } else {
                Log.e(TAG, "El campo " + fieldName + " no existe en el JSON");
                return null;
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al leer el archivo JSON", e);
            return null;
        }
    }

    private int getFieldValue(String fieldName) {
        File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            if (datosEntrenador.has(fieldName)) {
                return datosEntrenador.getInt(fieldName);
            } else {
                Log.e(TAG, "El campo " + fieldName + " no existe en el JSON");
                return -1;
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al leer el archivo JSON", e);
            return -1;
        }
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
}
