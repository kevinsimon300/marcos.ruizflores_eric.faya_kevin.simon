package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Random;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonDetail;

public class DetailFragment extends Fragment {
    private ArrayList<Pokemon> pokedexes;
    private Pokemon pokedex;
    private TextView tvType;
    private TextView tvDescription;
    private TextView tvSkills;
    private TextView tvError;
    private Button btnPokeball;
    private Button btnSuperball;
    private Button btnUltraball;
    private Button btnMasterball;
    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private TextView tvNomPokedex;
    private LinearLayout llStats;
    private LinearLayout llBalls;

    private static final String TAG = "DetailFragment";
    private static final String PREFERENCES_FILE = "edu.androidprpr2.preferences";
    private static final String KEY_CAPTURED_POKEMON = "captured_pokemon";
    private static final String KEY_POKEBALLS = "pokeballs";
    private static final String KEY_SUPERBALLS = "superballs";
    private static final String KEY_ULTRABALLS = "ultraballs";
    private static final String KEY_MASTERBALLS = "masterballs";
    private static final String KEY_MONEY = "money";

    public DetailFragment(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        this.pokedex = pokedex;
        this.pokedexes = pokedexes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_detail, container, false);

        tvNomPokedex = itemView.findViewById(R.id.tvNamePokemon);
        ivPokedex = itemView.findViewById(R.id.ivPokemonBack);
        imageViewFront = itemView.findViewById(R.id.ivPokemonFront);
        tvType = itemView.findViewById(R.id.tvType);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvError = itemView.findViewById(R.id.tvError);
        llBalls = itemView.findViewById(R.id.llCapturaBall);
        tvSkills = itemView.findViewById(R.id.tvHabilidades);
        btnPokeball = itemView.findViewById(R.id.Button1_pokeball);
        btnSuperball = itemView.findViewById(R.id.Button1_superball);
        btnUltraball = itemView.findViewById(R.id.Button1_ultraball);
        btnMasterball = itemView.findViewById(R.id.Button1_masterball);
        llStats = itemView.findViewById(R.id.llStats);

        tvNomPokedex.setText(pokedex.getName());
        tvType.setText(pokedex.getTypes().toString());
        tvDescription.setText(pokedex.getDescription());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        String capturedPokemonJson = sharedPreferences.getString(KEY_CAPTURED_POKEMON, "[]");
        JSONArray capturedPokemons;
        try {
            capturedPokemons = new JSONArray(capturedPokemonJson);
        } catch (JSONException e) {
            capturedPokemons = new JSONArray();
        }

        boolean[] pokemonChecks = checkIfPokemonIsCaptured(capturedPokemons);

        if (pokemonChecks[0]) {
            tvError.setText("You have already captured 6 pokemons");
            llBalls.setVisibility(View.GONE);
        } else if (pokemonChecks[1]) {
            tvError.setText("You have already captured this pokemon");
            llBalls.setVisibility(View.GONE);
        }

        Random random = new Random();
        int randomAbility = random.nextInt(4) + 1;
        String ability = "";
        if (randomAbility == 1) {
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (Ability ab : abilities) {
                if (ab.getIs_hidden()) {
                    ability = ab.getName();
                    tvSkills.setText(ability);
                }
            }
        } else {
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (Ability ab : abilities) {
                if (!ab.getIs_hidden()) {
                    ability = ab.getName();
                    tvSkills.setText(ability);
                }
            }
        }

        int randomNumber = random.nextInt(500) + 1;
        if (randomNumber == 1) {
            Picasso.get().load(pokedex.getBack_shiny()).into(ivPokedex);
            Picasso.get().load(pokedex.getFront_shiny()).into(imageViewFront);
        } else {
            Picasso.get().load(pokedex.getBackImage()).into(ivPokedex);
            Picasso.get().load(pokedex.getImageUrl()).into(imageViewFront);
        }

        JSONArray finalCapturedPokemons = capturedPokemons;
        btnPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityPokeballs = sharedPreferences.getInt(KEY_POKEBALLS, 0);
                if (quantityPokeballs > 0 && finalCapturedPokemons.length() < 6) {
                    int indexEvolution = pokedex.getIndex_evolution();
                    indexEvolution = getIndexValue(indexEvolution);
                    int accuracyPokeball = (600 - indexEvolution) / 6;
                    int randomPokeball = random.nextInt(100) + 1;
                    if (randomPokeball < accuracyPokeball) {
                        capturePokemon(finalCapturedPokemons, pokedex);
                        quantityPokeballs--;
                        saveQuantity(sharedPreferences, KEY_POKEBALLS, quantityPokeballs);
                        Toast.makeText(getContext(), "You captured the Pokemon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "You missed", Toast.LENGTH_SHORT).show();
                        quantityPokeballs--;
                        saveQuantity(sharedPreferences, KEY_POKEBALLS, quantityPokeballs);
                    }
                } else {
                    Toast.makeText(getContext(), "You do not have pokeballs or you already have 6 pokemons", Toast.LENGTH_SHORT).show();
                }
            }
        });

        JSONArray finalCapturedPokemons3 = capturedPokemons;
        btnSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantitySuperballs = sharedPreferences.getInt(KEY_SUPERBALLS, 0);
                if (quantitySuperballs > 0 && finalCapturedPokemons3.length() < 6) {
                    int indexEvolution = pokedex.getIndex_evolution();
                    indexEvolution = getIndexValue(indexEvolution);
                    int accuracySuperball = (800 - indexEvolution) / 4;
                    int randomSuperball = random.nextInt(100) + 1;
                    if (randomSuperball < accuracySuperball) {
                        capturePokemon(finalCapturedPokemons3, pokedex);
                        quantitySuperballs--;
                        saveQuantity(sharedPreferences, KEY_SUPERBALLS, quantitySuperballs);
                        Toast.makeText(getContext(), "You captured the Pokemon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "You missed", Toast.LENGTH_SHORT).show();
                        quantitySuperballs--;
                        saveQuantity(sharedPreferences, KEY_SUPERBALLS, quantitySuperballs);
                    }
                } else {
                    Toast.makeText(getContext(), "You do not have superballs or you already have 6 pokemons", Toast.LENGTH_SHORT).show();
                }
            }
        });

        JSONArray finalCapturedPokemons2 = capturedPokemons;
        btnUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityUltraballs = sharedPreferences.getInt(KEY_ULTRABALLS, 0);
                if (quantityUltraballs > 0 && finalCapturedPokemons2.length() < 6) {
                    int indexEvolution = pokedex.getIndex_evolution();
                    indexEvolution = getIndexValue(indexEvolution);
                    int accuracyUltraball = (900 - indexEvolution) / 3;
                    int randomUltraball = random.nextInt(100) + 1;
                    if (randomUltraball < accuracyUltraball) {
                        capturePokemon(finalCapturedPokemons2, pokedex);
                        quantityUltraballs--;
                        saveQuantity(sharedPreferences, KEY_ULTRABALLS, quantityUltraballs);
                        Toast.makeText(getContext(), "You captured the Pokemon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "You missed", Toast.LENGTH_SHORT).show();
                        quantityUltraballs--;
                        saveQuantity(sharedPreferences, KEY_ULTRABALLS, quantityUltraballs);
                    }
                } else {
                    Toast.makeText(getContext(), "You do not have ultraballs or you already have 6 pokemons", Toast.LENGTH_SHORT).show();
                }
            }
        });

        JSONArray finalCapturedPokemons1 = capturedPokemons;
        btnMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityMasterballs = sharedPreferences.getInt(KEY_MASTERBALLS, 0);
                if (quantityMasterballs > 0 && finalCapturedPokemons1.length() < 6) {
                    capturePokemon(finalCapturedPokemons1, pokedex);
                    quantityMasterballs--;
                    saveQuantity(sharedPreferences, KEY_MASTERBALLS, quantityMasterballs);
                    Toast.makeText(getContext(), "You captured the Pokemon", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You do not have masterballs or you already have 6 pokemons", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return itemView;
    }

    private boolean[] checkIfPokemonIsCaptured(JSONArray capturedPokemons) {
        boolean[] checks = new boolean[2];
        checks[0] = capturedPokemons.length() >= 6;

        for (int i = 0; i < capturedPokemons.length(); i++) {
            try {
                JSONObject obj = capturedPokemons.getJSONObject(i);
                if (obj.getString("id").equals(pokedex.getId())) {
                    checks[1] = true;
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return checks;
    }

    private void capturePokemon(JSONArray capturedPokemons, Pokemon pokedex) {
        try {
            JSONObject newPokemon = new JSONObject();
            newPokemon.put("id", pokedex.getId());
            newPokemon.put("name", pokedex.getName());
            newPokemon.put("imageUrl", pokedex.getImageUrl());
            capturedPokemons.put(newPokemon);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_CAPTURED_POKEMON, capturedPokemons.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveQuantity(SharedPreferences sharedPreferences, String key, int quantity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, quantity);
        editor.apply();
    }

    private int getIndexValue(int indexEvolution) {
        return indexEvolution < 100 ? 100 : indexEvolution;
    }

    private void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
