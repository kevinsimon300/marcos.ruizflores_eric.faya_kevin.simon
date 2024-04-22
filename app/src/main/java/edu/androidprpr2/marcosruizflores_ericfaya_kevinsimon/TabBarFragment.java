package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabBarFragment extends Fragment {
    private Button bPokedex;
    private Button bEntrenador;
    private Button bTenda;

    public TabBarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.fragment_tab_bar, container, false);
        bPokedex = (Button) View.findViewById(R.id.bPokedex);
        bEntrenador = (Button) View.findViewById(R.id.bEntrenador);
        bTenda = (Button) View.findViewById(R.id.bTenda);

        bPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pokedex", Toast.LENGTH_SHORT).show();
                //TODO: Do the pokedex fragment
            }
        });

        bEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Entrenador", Toast.LENGTH_SHORT).show();
                //TODO: Do the entrenador fragment
            }
        });

        bTenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tenda", Toast.LENGTH_SHORT).show();
                //TODO: Do the tenda fragment
            }
        });

        return View;
    }
}