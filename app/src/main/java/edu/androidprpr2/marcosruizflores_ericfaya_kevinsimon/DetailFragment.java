package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {//Que es creei el on create,el fragment,i que es crei la vista,tot lo altre fora

    private ArrayList<Pokedex> pokedexes;
    private Pokedex pokedex;//El pokemon que hem cliquen
    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;//TODO
    private TextView tvNomPokedex;

    public DetailFragment(Pokedex movie, ArrayList<Pokedex> movies) {
        this.pokedex = movie;
        this.pokedexes =movies;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView= inflater.inflate(R.layout.fragment_detail, container, false);
        //ivPokedex = itemView.findViewById(R.id.ivImageFilm); // Initialize ivMovie here

        tvNomPokedex = (TextView) itemView.findViewById(R.id.tvFilmName); //El item view es internament el view holder,no es un objecte creat per nosaltres


        tvNomPokedex.setText(pokedex.getName()); //Li pasem el nom

        return itemView;
    }
}