package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
    private PokedexDao pokedexDao; // Define una instancia de PokedexDao
    private int currentPage = 1; // D
    private int countPage = 0;
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
                    //int offset = pokedexes.size();
                    pokedexDao.getPokemonList(countPage);
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
                Log.e("PokedexFragment", "Error: " + errorMessage);
                isLoading = false;
            }
        });

        return view;
    }


    private void loadMoreItems() {
        pokedexDao.getPokemonList(currentPage++);
        Log.d("PokedexFragment", "More items: " + currentPage);

        //PokedexDao dao = new PokedexDao();
        //ArrayList<Pokedex> moreItems = dao.getPokemonList(currentPage);
        // Update the adapter with the new list of Pokémon
        // adapter.notifyDataSetChanged();
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
        private Activity activity; //De on ve la activity
        public PokedexHolder(LayoutInflater layoutInflater, ViewGroup parent, Activity activity)  {

            super(layoutInflater.inflate(R.layout.list_item_pokemon,parent,false));//Agafem el layout inflater,afegim el item que hem creat,estem dient al viewholder quin item es

            ivBack = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
            ivFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);
            ivPokeball = (ImageView) itemView.findViewById(R.id.ivPokeball);
            tvPokemonName = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres

            itemView.setOnClickListener(this);
            this.activity=activity;//La activity es la que li pasem per paremetres

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
            this.pokedex = pokedex;//Instanciem el pokemon
            tvPokemonName.setText(pokedex.getName()); //Li pasem el nom
            Picasso.get().load(pokedex.getImageUrl()).into(this.ivFront);
            Picasso.get().load(pokedex.getBackImage()).into(this.ivBack);
            Picasso.get().load(R.drawable.pokeball_pokemon_svgrepo_com).into(this.ivPokeball);
            Log.d("Pokemon type", pokedex.getPokeballType());
            /*if (pokedex.getPokeballType().equals("@drawable/pokeball_pokemon_svgrepo_com")) {
                Picasso.get().load(R.drawable.pokeball_pokemon_svgrepo_com);
            } else if (pokedex.getPokeballType().equals("@drawable/superball")) {
                Picasso.get().load(R.drawable.superball);
            } else if (pokedex.getPokeballType().equals("@drawable/wikiball")) {
                Picasso.get().load(R.drawable.wikiball);
            } else if (pokedex.getPokeballType().equals("@drawable/master_ball_icon_icons_com_67545")) {
                Picasso.get().load(R.drawable.master_ball_icon_icons_com_67545);
            }*/
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
            View view = LayoutInflater.from(activity).inflate(R.layout.list_item_pokemon, parent, false);
            return new PokedexHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull PokedexHolder holder, int position) {
            Pokemon pokedex = lPokedexes.get(position);//Creem un pokemon que l'agafem de la llista
            holder.bind(pokedex);
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