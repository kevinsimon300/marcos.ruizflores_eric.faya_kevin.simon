package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokemon;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonDetail;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {//Que es creei el on create,el fragment,i que es crei la vista,tot lo altre fora

    private ArrayList<Pokemon> pokedexes;
    private Pokemon pokedex;//El pokemon que hem cliquen
    private PokemonDetail pokemon;
    private TextView tvType;
    private TextView tvDescription;
    private TextView tvSkills;
    private TextView tvStats;
    private Button btnPokeball;
    private Button btnSuperball;
    private Button btnUltraball;
    private Button btnMasterball;
    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;//TODO
    private TextView tvNomPokedex;

    public DetailFragment(Pokemon pokedex, ArrayList<Pokemon> pokedexes) {
        this.pokedex = pokedex;
        this.pokedexes = pokedexes;
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

        tvNomPokedex = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres

        ivPokedex = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
        imageViewFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);
        Picasso.get().load(pokedex.getBackImage()).into(this.ivPokedex);
        Picasso.get().load(pokedex.getImageUrl()).into(this.imageViewFront);

        tvType = (TextView) itemView.findViewById(R.id.tvType); //El item view es internament el view holder,no es un objecte creat per nosaltres
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        tvSkills = (TextView) itemView.findViewById(R.id.tvHabilidades); //El item view es internament el view holder,no es un objecte creat per nosaltres
        tvStats = (TextView) itemView.findViewById(R.id.tvStats); //El item view es internament el view holder,no es un objecte creat per nosaltres

        tvNomPokedex.setText(pokedex.getName());
        tvType.setText(pokedex.getTypes().toString());
        tvDescription.setText(pokedex.getDescription());
        //--- Importación de los botones
        btnPokeball = (Button) itemView.findViewById(R.id.Button1_pokeball);
        btnSuperball = (Button) itemView.findViewById(R.id.Button1_superball);
        btnUltraball = (Button) itemView.findViewById(R.id.Button1_ultraball);
        btnMasterball = (Button) itemView.findViewById(R.id.Button1_masterball);

        btnPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Aquí va el resto del código de la captura
                Toast.makeText(getContext(), "Pokeball", Toast.LENGTH_SHORT).show();
            }
        });

        btnSuperball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Aquí va el resto del código de la captura
                Toast.makeText(getContext(), "Superball", Toast.LENGTH_SHORT).show();
            }
        });

        btnUltraball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Aquí va el resto del código de la captura
                Toast.makeText(getContext(), "Ultraball", Toast.LENGTH_SHORT).show();
            }
        });

        btnMasterball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Aquí va el resto del código de la captura
                Toast.makeText(getContext(), "Masterball", Toast.LENGTH_SHORT).show();
            }
        });


        //tvSkills.setText(pokedex.get());
        //tvStats.setText(pokedex.getStat0());
        tvStats.setText("Ataque: "+pokedex.getStat0() + " Stat2: " + pokedex.getStat1() + " Stat3: " + pokedex.getStat2()+ " Stat4: " + pokedex.getStat3() + " Stat5: " + pokedex.getStat4() + " Stat6: " + pokedex.getStat5());
        return itemView;
    }
}