package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence.PokedexDao;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {
    private RecyclerView pokedexesRecyclerView;//A la clase del fragment tenim recycler view
    private  PokedexAdapter adapter;//A la clase del fragment tenim adapter
    private ArrayList<Pokemon> pokedexes;
    private boolean isLoading = false; // Declara isLoading y establece su valor inicial
    private int visibleThreshold = 5;
    private EditText namePokemon;
    public ImageButton searchButton;
    private PokedexDao pokedexDao; // Define una instancia de PokedexDao
    private int currentPage = 1; // D
    public int countPage = 0;
    public PokedexFragment(ArrayList<Pokemon> pokedexes,PokedexDao pokedexDao) {
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

        final PokedexAdapter adapter = new PokedexAdapter(pokedexes, getActivity(), isLoading, visibleThreshold);
        pokedexesRecyclerView.setAdapter(adapter);

        namePokemon = (EditText) view.findViewById(R.id.editTextSearch);
        searchButton = (ImageButton) view.findViewById(R.id.btButtonSearch);
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
                    //int offset = pokedexes.size();
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

    private void updateUi() {
        if (pokedexes != null) {
            Log.d("PokedexFragment", "Tamaño de pokedexes: " + pokedexes.size());

            for (int i = 0; i < pokedexes.size(); i++) {
                Pokemon pokedex = pokedexes.get(i);
                Log.d("PokedexFragment", "Pokedex[" + i + "]: " + pokedex.getName());
            }

            adapter = new PokedexAdapter(pokedexes, getActivity(), isLoading, visibleThreshold);
        } else {
            Log.e("PokedexFragment", "La lista de pokedexes es nula");
            adapter = new PokedexAdapter(new ArrayList<>(), getActivity(), isLoading, visibleThreshold);
        }

        pokedexesRecyclerView.setAdapter(adapter);
    }


    public class PokedexHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Pokemon pokedex;
        private final ImageView ivBack;
        private final ImageView ivFront;
        private final ImageView ivPokeball;
        private final TextView tvPokemonName;
        //private Activity activity; //De on ve la activity
        private Context context;
        public PokedexHolder(LayoutInflater layoutInflater, ViewGroup parent, Context context)  {

            super(layoutInflater.inflate(R.layout.list_item_pokemon,parent,false));//Agafem el layout inflater,afegim el item que hem creat,estem dient al viewholder quin item es

            ivBack = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
            ivFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);
            ivPokeball = (ImageView) itemView.findViewById(R.id.ivPokeball);
            tvPokemonName = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres

            itemView.setOnClickListener(this);
            //this.activity=activity;//La activity es la que li pasem per paremetres
            this.context = context;

        }

        public PokedexHolder(View itemView) {//new
            super(itemView);
            ivBack = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
            ivFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);
            tvPokemonName = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres
            ivPokeball = (ImageView) itemView.findViewById(R.id.ivPokeball);

            itemView.setOnClickListener(this);
        }

        public void bind(Pokemon pokedex) {
            this.pokedex = pokedex; // Instanciar el Pokemon
            tvPokemonName.setText(pokedex.getName()); // Asignar el nombre
            // Cargar imágenes frontales y traseras del Pokémon
            Picasso.get().load(pokedex.getImageUrl()).into(this.ivFront);
            Picasso.get().load(pokedex.getBackImage()).into(this.ivBack);
            JSONArray pokemonCapturadosArray = readPokemonCapturadosArrayFromFile();
            String captured = checkIfPokemonIsCaptured(pokemonCapturadosArray);
            // Cargar imagen de Pokeball
            if(!captured.equals("")) {
                if (captured.equals("@drawable/pokeball_pokemon_svgrepo_com")) {
                    ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pokeball_pokemon_svgrepo_com));
                } else if (captured.equals("@drawable/superball")) {
                    ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.superball));
                } else if (captured.equals("@drawable/wikiball")) {
                    ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.wikiball));
                } else if (captured.equals("@drawable/master_ball_icon_icons_com_67545")) {
                    ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.master_ball_icon_icons_com_67545));
                }
            } else {
                ivPokeball.setImageDrawable(ContextCompat.getDrawable(context, R.color.orangeRecycler));
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

        private String checkIfPokemonIsCaptured(JSONArray pokemonList) {
            String namePokeball = "";
            try {
                for (int i = 0; i < pokemonList.length(); i++) {
                    JSONObject pokemon = pokemonList.getJSONObject(i);
                    String name = pokemon.getString("name");
                    if (name.equals(pokedex.getName())) {
                        namePokeball = pokemon.getString("capturedPokeballImage");
                        break;
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException("Error parsing JSON", e);
            }
            return namePokeball;
        }
        @Override
        public void onClick(View view) {
            // coger entrenador del json
            //int  entrenador_pokeballs = getJSONObject(); // leer del json
            DetailFragment detailFragment = new DetailFragment(pokedex,pokedexes);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,detailFragment);

            // fragmentTransaction.replace(R.id.fragment_container,detailFragment); //TODO ESTO HACE FALTA METERLO O NO?

            //fragmentTransaction.addToBackStack(null);//La pila,cuan tenim varios fragemnts hem de fer aixo,per tornar enrere el fragment
            fragmentTransaction.commit();

        }
    }
    public class PokedexAdapter extends RecyclerView.Adapter<PokedexHolder>{
        private List<Pokemon> lPokedexes;//Te la llista de la informacio que pasem
        private Activity activity; //La activity
        private boolean isLoading = false; // Declara isLoading y establece su valor inicial
        private int visibleThreshold = 5; // Declara visibleThreshold y establece su valor
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
        public PokedexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // LayoutInflater layoutInflater = LayoutInflater.from(activity);
            //  return new PokedexHolder(layoutInflater,parent,activity);
            //OLD

            //NEW
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new PokedexHolder(layoutInflater, parent, parent.getContext());

        }

        @Override
        public void onBindViewHolder(@NonNull PokedexHolder holder, int position) {
            Pokemon pokedex = lPokedexes.get(position);//Creem un pokemon que l'agafem de la llista
            holder.bind(pokedex);
        }

        public int getCountPage() {
            return countPage;
        }

        @Override
        public int getItemCount() {
            return lPokedexes.size();
        }
        private void loadMoreItems() {
          /*   PokedexDao dao = new PokedexDao();
            ArrayList<Pokedex> moreItems = dao.getPokemonListContination(currentPage);
            // Update the adapter with the new list of Pokémon
            notifyDataSetChanged();*/
        }
    }
}