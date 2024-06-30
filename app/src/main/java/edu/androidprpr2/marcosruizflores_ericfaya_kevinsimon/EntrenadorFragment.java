package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private Button btnChangeNameTrainer;
    private RecyclerView pokemonRecyclerView;
    private CapturatedPokemonAdapter pokemonAdapter;
    private DetailFragment detailFragment;
    private List<PokemonCapturado> pokemonList = new ArrayList<>();

    private static final String TAG = "EntrenadorFragment";
    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_TRAINER_NAME = "trainer_name";
    private static final String KEY_MONEY = "money";
    private static final String KEY_POKEBALLS = "pokeballs";
    private static final String KEY_SUPERBALLS = "superballs";
    private static final String KEY_ULTRABALLS = "ultraballs";
    private static final String KEY_MASTERBALLS = "masterballs";

    public EntrenadorFragment() {
    }

    public static EntrenadorFragment newInstance() {
        return new EntrenadorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        btnChangeNameTrainer = view.findViewById(R.id.btChangeNameTrainer);
        tvMoney = view.findViewById(R.id.tvTrainerCash);
        tvNameEntrenador = view.findViewById(R.id.tvTrainerName);
        tvPokeball = view.findViewById(R.id.tvPokeballs);
        tvSuperball = view.findViewById(R.id.tvSuperballs);
        tvUltraball = view.findViewById(R.id.tvUltraballs);
        tvMaterball = view.findViewById(R.id.tvMasterballs);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        tvNameEntrenador.setText(sharedPreferences.getString(KEY_TRAINER_NAME, "Default Name"));
        tvMoney.setText(String.valueOf(sharedPreferences.getInt(KEY_MONEY, 0)));
        tvPokeball.setText(String.valueOf(sharedPreferences.getInt(KEY_POKEBALLS, 0)));
        tvSuperball.setText(String.valueOf(sharedPreferences.getInt(KEY_SUPERBALLS, 0)));
        tvUltraball.setText(String.valueOf(sharedPreferences.getInt(KEY_ULTRABALLS, 0)));
        tvMaterball.setText(String.valueOf(sharedPreferences.getInt(KEY_MASTERBALLS, 0)));

        pokemonRecyclerView = view.findViewById(R.id.pokemon_recycler_view);
        pokemonList = PokemonCapturado.getPokemonList(getContext());

        pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pokemonAdapter = new CapturatedPokemonAdapter(pokemonList, getContext());
        pokemonRecyclerView.setAdapter(pokemonAdapter);
    }
}
