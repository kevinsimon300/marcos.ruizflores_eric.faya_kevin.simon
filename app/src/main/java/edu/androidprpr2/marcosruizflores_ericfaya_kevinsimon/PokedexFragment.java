package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;

public class PokedexFragment extends Fragment {
    private RecyclerView pokedexesRecyclerView;
    private PokedexAdapter adapter;
    private ArrayList<Pokemon> pokedexes;
    private boolean isLoading = false;
    private int visibleThreshold = 5;
    private EditText namePokemon;
    public ImageButton searchButton;
    private PokedexDao pokedexDao;
    private int currentPage = 1;
    public int countPage = 0;

    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_CAPTURED_POKEMON = "captured_pokemon";

    public PokedexFragment(ArrayList<Pokemon> pokedexes, PokedexDao pokedexDao) {
        this.pokedexes = pokedexes;
        this.pokedexDao = pokedexDao;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);
        pokedexesRecyclerView = view.findViewById(R.id.pokedex_recycler_view);
        pokedexesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PokedexAdapter(pokedexes, getActivity(), isLoading, visibleThreshold);
        pokedexesRecyclerView.setAdapter(adapter);

        namePokemon = view.findViewById(R.id.editTextSearch);
        searchButton = view.findViewById(R.id.btButtonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = namePokemon.getText().toString();
                PokedexDao pokedexDao = new PokedexDao(getContext(), new PokedexDao.PokedexCallback() {
                    @Override
                    public void onSuccess(ArrayList<Pokemon> pokemonList) {
                        if (!pokemonList.isEmpty()) {
                            Pokemon pokemon = pokemonList.get(0);
                            DetailFragment detailFragment = new DetailFragment(pokemon, pokedexes);
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, detailFragment);
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getActivity(), "No se encontró el pokemon: " + name, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), "Error al buscar el pokemon", Toast.LENGTH_SHORT).show();
                    }
                });
                pokedexDao.getSearchedPokemon(name);
            }
        });

        pokedexesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    isLoading = true;
                    countPage++;
                    boolean cargar15 = true;
                    pokedexDao.getPokemonList(countPage, cargar15, countPage);
                }
            }
        });

        pokedexDao = new PokedexDao(getActivity(), new PokedexDao.PokedexCallback() {
            @Override
            public void onSuccess(ArrayList<Pokemon> newPokemons) {
                pokedexes.addAll(newPokemons);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("PokedexFragment", "No existe en la pokedex");
                isLoading = false;
            }
        });

        return view;
    }

    public class PokedexHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Pokemon pokedex;
        private final ImageView ivBack;
        private final ImageView ivFront;
        private final ImageView ivPokeball;
        private final TextView tvPokemonName;
        private Context context;

        public PokedexHolder(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
            super(layoutInflater.inflate(R.layout.list_item_pokemon, parent, false));
            ivBack = itemView.findViewById(R.id.ivPokemonBack);
            ivFront = itemView.findViewById(R.id.ivPokemonFront);
            ivPokeball = itemView.findViewById(R.id.ivPokeball);
            tvPokemonName = itemView.findViewById(R.id.tvNamePokemon);

            itemView.setOnClickListener(this);
            this.context = context;
        }

        public void bind(Pokemon pokedex) {
            this.pokedex = pokedex;
            tvPokemonName.setText(pokedex.getName());
            Picasso.get().load(pokedex.getImageUrl()).into(this.ivFront);
            Picasso.get().load(pokedex.getBackImage()).into(this.ivBack);

            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
            List<PokemonCapturado> capturedPokemons = getCapturedPokemons(sharedPreferences);
            String captured = checkIfPokemonIsCaptured(capturedPokemons);

            if (!captured.equals("")) {
                switch (captured) {
                    case "@drawable/pokeball_pokemon_svgrepo_com":
                        ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pokeball_pokemon_svgrepo_com));
                        break;
                    case "@drawable/superball":
                        ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.superball));
                        break;
                    case "@drawable/ultraball":
                        ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.wikiball));
                        break;
                    case "@drawable/master_ball_icon_icons_com_67545":
                        ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.master_ball_icon_icons_com_67545));
                        break;
                }
            } else {
                ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.color.orangeRecycler));
            }
        }

        private String checkIfPokemonIsCaptured(List<PokemonCapturado> pokemonList) {
            String namePokeball = "";
            for (PokemonCapturado pokemon : pokemonList) {
                if (pokemon.getName().equals(pokedex.getName())) {
                    namePokeball = pokemon.getCapturedPokeballImage();
                    break;
                }
            }
            return namePokeball;
        }

        @Override
        public void onClick(View view) {
            DetailFragment detailFragment = new DetailFragment(pokedex, pokedexes);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, detailFragment);
            fragmentTransaction.commit();
        }
    }

    public class PokedexAdapter extends RecyclerView.Adapter<PokedexHolder> {
        private List<Pokemon> lPokedexes;
        private Activity activity;
        private boolean isLoading;
        private int visibleThreshold;
        private int currentPage = 1;
        private int pageSize = 15;

        public PokedexAdapter(List<Pokemon> lPokedexes, Activity activity, boolean isLoading, int visibleThreshold) {
            this.lPokedexes = lPokedexes;
            this.activity = activity;
            this.isLoading = isLoading;
            this.visibleThreshold = visibleThreshold;
        }

        @NonNull
        @Override
        public PokedexHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new PokedexHolder(layoutInflater, parent, parent.getContext());
        }

        @Override
        public void onBindViewHolder(PokedexHolder holder, int position) {
            Pokemon pokedex = lPokedexes.get(position);
            holder.bind(pokedex);
        }

        public int getCountPage() {
            return countPage;
        }

        @Override
        public int getItemCount() {
            return lPokedexes.size();
        }
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
}