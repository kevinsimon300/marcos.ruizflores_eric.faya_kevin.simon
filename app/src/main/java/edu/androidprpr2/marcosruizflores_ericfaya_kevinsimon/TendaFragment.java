package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TendaFragment extends Fragment {
    private Button bPokeball;
    private Button bSuperball;
    private Button bUltraball;
    private Button bMasterball;

    public TendaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenda, container, false);

        bPokeball = view.findViewById(R.id.button_pokeball);
        bSuperball = view.findViewById(R.id.button_superball);
        bUltraball = view.findViewById(R.id.button_ultraball);
        bMasterball = view.findViewById(R.id.button_masterball);

        bPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
