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
import java.util.List;
import java.util.Random;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;

public class DetailFragment extends Fragment {
    private static final String ARG_POKEMON = "pokemon";
    private static final String ARG_POKEDEXES = "pokedexes";

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
    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_CAPTURED_POKEMON = "captured_pokemon";
    private static final String KEY_POKEBALLS = "pokeballs";
    private static final String KEY_SUPERBALLS = "superballs";
    private static final String KEY_ULTRABALLS = "ultraballs";
    private static final String KEY_MASTERBALLS = "masterballs";
    private static final String KEY_MONEY = "money";

    public DetailFragment() {
        // Constructor vacío requerido
    }

    public DetailFragment(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        this.pokedex = pokedex;
        this.pokedexes = pokedexes;
    }

    public void setArguments(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        this.pokedex = pokedex;
        this.pokedexes = pokedexes;
    }

    public static DetailFragment newInstance(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(pokedex, pokedexes);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokedex = (Pokemon) getArguments().getSerializable(ARG_POKEMON);
            pokedexes = (ArrayList<Pokemon>) getArguments().getSerializable(ARG_POKEDEXES);
        }
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
        List<PokemonCapturado> capturedPokemons = getCapturedPokemons(sharedPreferences);

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
        List<Ability> abilities = pokedex.getAbilities();
        if (randomAbility == 1) {
            for (Ability ab : abilities) {
                if (ab.getIs_hidden()) {
                    ability = ab.getName();
                    break;
                }
            }
        } else {
            for (Ability ab : abilities) {
                if (!ab.getIs_hidden()) {
                    ability = ab.getName();
                    break;
                }
            }
        }
        tvSkills.setText(ability);

        int randomNumber = random.nextInt(500) + 1;
        if (randomNumber == 1) {
            Picasso.get().load(pokedex.getBackShiny()).into(ivPokedex);
            Picasso.get().load(pokedex.getFrontShiny()).into(imageViewFront);
        } else {
            Picasso.get().load(pokedex.getBackImage()).into(ivPokedex);
            Picasso.get().load(pokedex.getImageUrl()).into(imageViewFront);
        }

        btnPokeball.setOnClickListener(v -> handleCapture(sharedPreferences, capturedPokemons, "pokeballs", 600, pokedex, "pokeball_pokemon_svgrepo_com"));
        btnSuperball.setOnClickListener(v -> handleCapture(sharedPreferences, capturedPokemons, "superballs", 800, pokedex, "superball"));
        btnUltraball.setOnClickListener(v -> handleCapture(sharedPreferences, capturedPokemons, "ultraballs", 900, pokedex, "ultraball"));
        btnMasterball.setOnClickListener(v -> handleCapture(sharedPreferences, capturedPokemons, "masterballs", 1000, pokedex, "master_ball_icon_icons_com_67545"));

        for (int i = 0; i < 6; i++) {
            TextView tvStats = new TextView(getContext());
            switch (i) {
                case 0:
                    tvStats.setText("HP: " + pokedex.getStat0());
                    tvStats.setBackgroundResource(R.drawable.stats_border0);
                    break;
                case 1:
                    tvStats.setText("Attack: " + pokedex.getStat1());
                    tvStats.setBackgroundResource(R.drawable.stats_border1);
                    break;
                case 2:
                    tvStats.setText("Defense: " + pokedex.getStat2());
                    tvStats.setBackgroundResource(R.drawable.stats_border2);
                    break;
                case 3:
                    tvStats.setText("Special Attack: " + pokedex.getStat3());
                    tvStats.setBackgroundResource(R.drawable.stats_border3);
                    break;
                case 4:
                    tvStats.setText("Special Defense: " + pokedex.getStat4());
                    tvStats.setBackgroundResource(R.drawable.stats_border4);
                    break;
                case 5:
                    tvStats.setText("Speed: " + pokedex.getStat5());
                    tvStats.setBackgroundResource(R.drawable.stats_border5);
                    break;
            }
            tvStats.setPadding(8, 8, 8, 8);
            tvStats.setTextColor(getResources().getColor(android.R.color.black));
            tvStats.setTextSize(16);
            llStats.addView(tvStats);
        }

        return itemView;
    }

    private void handleCapture(SharedPreferences sharedPreferences, List<PokemonCapturado> capturedPokemons, String ballType, int baseAccuracy, Pokemon pokedex, String pokeballImage) {
        int quantityBalls = sharedPreferences.getInt(ballType, 0);
        if (quantityBalls > 0 && capturedPokemons.size() < 6) {
            int indexEvolution = pokedex.getIndexEvolution();
            indexEvolution = getIndexValue(indexEvolution);
            int accuracy = (baseAccuracy - indexEvolution) / 6;
            int randomValue = new Random().nextInt(100) + 1;
            if (randomValue < accuracy) {
                capturePokemon(sharedPreferences, capturedPokemons, ballType, pokedex, pokeballImage);
                Toast.makeText(getContext(), "You captured the Pokemon", Toast.LENGTH_SHORT).show();
            } else {
                modifyFieldValue(sharedPreferences, ballType, -1);
                Toast.makeText(getContext(), "You missed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "You do not have " + ballType + " or you already have 6 pokemons", Toast.LENGTH_SHORT).show();
        }
    }

    private void capturePokemon(SharedPreferences sharedPreferences, List<PokemonCapturado> capturedPokemons, String ballType, Pokemon pokedex, String pokeballImage) {
        capturedPokemons.add(new PokemonCapturado(pokedex.getName(), pokedex.getImageUrl(), pokeballImage));
        saveCapturedPokemons(sharedPreferences, capturedPokemons);
        modifyFieldValue(sharedPreferences, ballType, -1);
    }

    private void saveCapturedPokemons(SharedPreferences sharedPreferences, List<PokemonCapturado> capturedPokemons) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        for (PokemonCapturado pokemon : capturedPokemons) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", pokemon.getName());
                jsonObject.put("frontImage", pokemon.getFrontImage());
                jsonObject.put("capturedPokeballImage", pokemon.getCapturedPokeballImage());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(KEY_CAPTURED_POKEMON, jsonArray.toString());
        editor.apply();
    }

    private List<PokemonCapturado> getCapturedPokemons(SharedPreferences sharedPreferences) {
        List<PokemonCapturado> capturedPokemons = new ArrayList<>();
        String jsonString = sharedPreferences.getString(KEY_CAPTURED_POKEMON, "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String frontImage = jsonObject.getString("frontImage");
                String pokeballType = jsonObject.getString("capturedPokeballImage");
                capturedPokemons.add(new PokemonCapturado(name, frontImage, pokeballType));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return capturedPokemons;
    }

    private void modifyFieldValue(SharedPreferences sharedPreferences, String fieldName, int incrementValue) {
        int currentValue = sharedPreferences.getInt(fieldName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(fieldName, currentValue + incrementValue);
        editor.apply();
    }

    private int getIndexValue(int indexEvolution) {
        return indexEvolution < 100 ? 100 : indexEvolution;
    }

    private boolean[] checkIfPokemonIsCaptured(List<PokemonCapturado> capturedPokemons) {
        boolean[] checks = new boolean[2];
        checks[0] = capturedPokemons.size() == 6;
        checks[1] = false;
        for (PokemonCapturado pokemon : capturedPokemons) {
            if (pokemon.getName().equals(pokedex.getName())) {
                checks[1] = true;
                break;
            }
        }
        return checks;
    }
}
