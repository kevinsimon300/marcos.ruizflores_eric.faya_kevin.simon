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
public class DetailFragment extends Fragment {
//Que es creei el on create,el fragment,i que es crei la vista,tot lo altre fora

    private ArrayList<Pokedex> pokedexes;
    private Pokedex pokedex;//El pokemon que hem cliquen

    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;//TODO

    private TextView tvNomPokedex;
    private TextView tvReview;


    public DetailFragment(Pokedex movie, ArrayList<Pokedex> movies) {
        this.pokedex = movie;
        this.pokedexes =movies;
        //Constructor amb el pokemon
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Aquest codi crea la vista,pero realment es el nostre layout en el fragment que toca,es a dir,s'infla el layout al fragment(inflater.inflate)
    //S'aplica al fragment que acabem de fer nosaltres,
    // El container es una part que s'aplica al layout del container,es per anar afegint fragments dintre del contenidor,es una cosa que es fa internament
    //El false es per dir que hi haura mes d'un,normalment sempre es fica false per dir que hi hauran mes fragments dintre del container
    //Aixo s'aplica el layout que hem fet a aquest fragment.
    //Despres per la part del main hem de gestionar aquest fragment,llavors aixo es un codi de fragment que nomes carrega la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View itemView= inflater.inflate(R.layout.fragment_detail, container, false);
        ivPokedex = itemView.findViewById(R.id.ivImageFilm); // Initialize ivMovie here

        tvNomPokedex = (TextView) itemView.findViewById(R.id.tvFilmName); //El item view es internament el view holder,no es un objecte creat per nosaltres
        //El item view es internament al pokemon holder,no l'hem creat nosaltres
        tvReview = (TextView) itemView.findViewById(R.id.tvReview);

        //ImageView ivMovie = movie.transformImageView(getContext(), movie.getThumbnail());
        //ivMovie.setImageDrawable(ivMovie.getDrawable());

        //Picasso.get().load(movie.getThumbnail()).into(ivMovie);


        tvNomPokedex.setText(pokedex.getName()); //Li pasem el nom

        String reviewValue = pokedex.getReview();
        String reviewText = String.valueOf(reviewValue);
        tvReview.setText(reviewText);


        //tvCast.setText(movie.getCast()); //Li pasem el nom

        return itemView;
    }
}