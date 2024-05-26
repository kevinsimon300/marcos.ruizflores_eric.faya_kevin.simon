package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TendaFragment extends Fragment {

    private TextView tvMoney;
    private Button bPokeball;
    private Button bSuperball;
    private Button bUltraball;
    private Button bMasterball;

    private static final String TAG = "TendaFragment";

    public TendaFragment() {
        // Required empty public constructor
    }
    public static TendaFragment newInstance(String param1, String param2) {
        TendaFragment fragment = new TendaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tenda, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMoney = (TextView) view.findViewById(R.id.tvMoney);
        tvMoney.setText(String.valueOf(getFieldValue("Money")));
        bPokeball = (Button) view.findViewById(R.id.Button1_pokeball);
        bPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyJsonFieldValue("Money",-200);

                modifyJsonFieldValue("Pokeballs",1);

                updateMoneyDisplay();
            }
        });
        bSuperball = (Button) view.findViewById(R.id.Button1_superball);
        bSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyJsonFieldValue("Money",-500);
                modifyJsonFieldValue("Superballs",1);

                updateMoneyDisplay();
            }
        });
        bUltraball = (Button) view.findViewById(R.id.Button1_ultraball);
        bUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyJsonFieldValue("Money",-1500);

                modifyJsonFieldValue("Ultraballs",1);

                updateMoneyDisplay();
            }
        });
        bMasterball = (Button) view.findViewById(R.id.Button1_masterball);
        bMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyJsonFieldValue("Money",-100000);

                modifyJsonFieldValue("Masterballs",1);

                updateMoneyDisplay();
            }
        });
    }
    private void modifyJsonFieldValue(String fieldName, int incrementValue) {
        File file = new File(requireContext().getFilesDir(), "Files/entrenador.json");

        try {
            // Leer el archivo JSON existente
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
        //Log.d(TAG, "Lectura desde tenda: " + readFile(file));

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
    private void updateMoneyDisplay() {
        tvMoney.setText(String.valueOf(getFieldValue("Money")));
    }
}