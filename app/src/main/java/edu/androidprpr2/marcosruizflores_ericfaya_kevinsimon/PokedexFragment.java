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

import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedexes;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {

    private RecyclerView pokedexesRecyclerView;//A la clase del fragment tenim recycler view
    private  PokedexAdapter adapter;//A la clase del fragment tenim adapter
    private ArrayList<Pokedex> pokedexes;

    public PokedexFragment(ArrayList<Pokedex> pokedexes) {
        this.pokedexes=pokedexes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);
        pokedexesRecyclerView=(RecyclerView)view.findViewById(R.id.pokedex_recycler_view);
        pokedexesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUi();//Hem identificat el recycler view pero fa falta  pasarli la informacio,ficar quin adapter te el recycler view

        return view;
    }

    private void updateUi() {
        Log.d("PokedexFragment", "Actualizando UI");

        Log.d("PokedexFragment", "Tamaño de pokedexes: " + pokedexes.size());

        Pokedexes movies2 = Pokedexes.getInstance(getActivity(),pokedexes);//Aqui dintre detecta si esta creada o no,si no esta creada la creem
        List<Pokedex> lpokedex = movies2.getPokedexes(); //Agafem la llista de pokemons i fem un get pokemons per obtenir els pokemons que hem creat a la clase de la pokedex

        Log.d("PokedexFragment", "Tamaño de lpokedex: " + lpokedex.size());


        adapter = new PokedexAdapter(lpokedex,getActivity());// li pasem la llista de pokemons que te que tenir aquest adapter,i la activitat
        pokedexesRecyclerView.setAdapter(adapter);//Afegim a la recycler view l'adapter que hem creat
    }

    public class PokedexHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Pokedex pokedex;
        private ImageView ivPokedex;

        private TextView tvNomMovie;

        private Activity activity; //De on ve la activity

        public PokedexHolder(LayoutInflater layoutInflater, ViewGroup parent, Activity activity)  {

            super(layoutInflater.inflate(R.layout.list_item_pokemon,parent,false));//Agafem el layout inflater,afegim el item que hem creat,estem dient al viewholder quin item es
            ivPokedex = (ImageView) itemView.findViewById(R.id.ivImageFilm);

            tvNomMovie = (TextView) itemView.findViewById(R.id.tvFilmName); //El item view es internament el view holder,no es un objecte creat per nosaltres

            itemView.setOnClickListener(this);
            this.activity=activity;//La activity es la que li pasem per paremetres

        }

        public void bind(Pokedex pokedex) {
            this.pokedex = pokedex;//Instanciem el pokemon

            tvNomMovie.setText(pokedex.getName()); //Li pasem el nom
        }

        @Override
        public void onClick(View view) {
            DetailFragment detailFragment = new DetailFragment(pokedex,pokedexes);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container,detailFragment); //TODO ESTO HACE FALTA METERLO O NO?

            fragmentTransaction.addToBackStack(null);//La pila,cuan tenim varios fragemnts hem de fer aixo,per tornar enrere el fragment
            fragmentTransaction.commit();

        }
    }
    public class PokedexAdapter extends RecyclerView.Adapter<PokedexHolder>{
        private List<Pokedex> lPokedexes;//Te la llista de la informacio que pasem
        private Activity activity; //La activity

        public PokedexAdapter(List<Pokedex> lPokedexes, Activity activity) {
            this.lPokedexes = lPokedexes;
            this.activity = activity;
        }

        @NonNull
        @Override
        public PokedexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            return new PokedexHolder(layoutInflater,parent,activity);
        }

        @Override
        public void onBindViewHolder(@NonNull PokedexHolder holder, int position) {
            Pokedex pokedex = lPokedexes.get(position);//Creem un pokemon que l'agafem de la llista
            holder.bind(pokedex);
        }

        @Override
        public int getItemCount() {
            return lPokedexes.size();
        }
    }
}