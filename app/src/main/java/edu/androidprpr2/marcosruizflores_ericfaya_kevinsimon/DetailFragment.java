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
import java.util.Random;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
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

        tvType = (TextView) itemView.findViewById(R.id.tvType); //El item view es internament el view holder,no es un objecte creat per nosaltres
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        tvStats = (TextView) itemView.findViewById(R.id.tvStats); //El item view es internament el view holder,no es un objecte creat per nosaltres

        tvNomPokedex.setText(pokedex.getName());
        tvType.setText(pokedex.getTypes().toString());
        tvDescription.setText(pokedex.getDescription());
        //--- Importación de los botones
        btnPokeball = (Button) itemView.findViewById(R.id.Button1_pokeball);
        btnSuperball = (Button) itemView.findViewById(R.id.Button1_superball);
        btnUltraball = (Button) itemView.findViewById(R.id.Button1_ultraball);
        btnMasterball = (Button) itemView.findViewById(R.id.Button1_masterball);

        tvSkills = (TextView) itemView.findViewById(R.id.tvHabilidades); //El item view es internament el view holder,no es un objecte creat per nosaltres

        Random random = new Random();
        int randomAbility = random.nextInt(4) + 1;
        String ability = "";
        if (randomAbility == 1){
            // aqui poner la habilidad oculta
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (int i = 0; i < abilities.size(); i++){
                if (abilities.get(i).getIs_hidden()){
                    ability = abilities.get(i).getName();
                    tvSkills.setText(ability);
                }
            }
        } else {
            ArrayList<Ability> abilities = pokedex.getAbilities();
            for (int i = 0; i < abilities.size(); i++){
                if (!abilities.get(i).getIs_hidden()){
                    ability = abilities.get(i).getName();
                    tvSkills.setText(ability);
                }
            }
        }

        int randomNumber = random.nextInt(500) + 1; // Esto generará un número entre 1 y 500 inclusive

        if (randomNumber == 1) {
            // Mostrar imágenes "shiny"
            Picasso.get().load(pokedex.getBack_shiny()).into(ivPokedex);
            Picasso.get().load(pokedex.getFront_shiny()).into(imageViewFront);
        } else {
            // Mostrar imágenes normales
            Picasso.get().load(pokedex.getBackImage()).into(ivPokedex);
            Picasso.get().load(pokedex.getImageUrl()).into(imageViewFront);
        }


        btnPokeball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        tvStats.setText("hp: "+pokedex.getStat0() + " \nattack: " + pokedex.getStat1() + " \ndefense: " + pokedex.getStat2()+ " \nspecial-attack: " + pokedex.getStat3() + " \nspecial-defense: " + pokedex.getStat4() + " \nspeed: " + pokedex.getStat5());
        return itemView;
    }
}