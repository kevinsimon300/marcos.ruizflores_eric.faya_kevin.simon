package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ChangeNameFragment extends Fragment {
    private TextView tvNameEntrenador;
    private EditText etNameEntrenador;
    private Button btnChangeNameTrainer;
    private static final String TAG = "EntrenadorFragment";

    public ChangeNameFragment() {
    }

    public static ChangeNameFragment newInstance() {
        return new ChangeNameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_name, container, false);
        String trainerName = getTrainerName();

        tvNameEntrenador = view.findViewById(R.id.tvTrainerName);
        etNameEntrenador = view.findViewById(R.id.etNewName);
        btnChangeNameTrainer = view.findViewById(R.id.btnConfirm);
        tvNameEntrenador.setText(trainerName);

        btnChangeNameTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etNameEntrenador.getText().toString();
                if (!newName.isEmpty()) {
                    updateEntrenadorName(newName);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new EntrenadorFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Introduce un nuevo nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private String getTrainerName(){
        File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            if (datosEntrenador.has("Name")) {
                return datosEntrenador.getString("Name");
            } else {
                Log.e(TAG, "El campo Name no existe en el JSON");
                return null;
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al leer el archivo JSON", e);
            return null;
        }
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

    private void updateEntrenadorName(String newName) {
        File file = new File(getContext().getFilesDir(), "Files/entrenador.json");
        try {
            // Leer el JSON existente
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            // Convertir a String y crear un objeto JSON
            String jsonString = new String(data, "UTF-8");
            JSONObject datosEntrenador = new JSONObject(jsonString);

            // Actualizar el nombre
            datosEntrenador.put("Name", newName);

            // Escribir de vuelta al archivo
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(datosEntrenador.toString(2).getBytes());
            fos.close();


        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error al actualizar el nombre del entrenador en el archivo JSON", e);
        }
    }

}
