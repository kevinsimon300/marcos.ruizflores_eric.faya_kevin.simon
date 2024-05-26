package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Random;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Ability;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Entrenador;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Item;
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
    private TextView tvError;
    private Button btnPokeball;
    private Button btnSuperball;
    private Button btnUltraball;
    private Button btnMasterball;
    private ImageView ivPokedex;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;//TODO
    private TextView tvNomPokedex;
    private LinearLayout llBalls;
    private LinearLayout llBalls2;

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
        ArrayList<Item> items = new ArrayList<>(); //Harcoded items
        items.add(new Item("Pokeball", 200));
        items.add(new Item("Superball", 600));
        items.add(new Item("Ultraball", 1200));
        items.add(new Item("Masterball", 5000));
        ArrayList<PokemonDetail> pokemonsTrainer = new ArrayList<>(); //Harcoded pokemons
        PokemonDetail pokemonDetail = new PokemonDetail("Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png", new ArrayList<String>(), "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON.", new ArrayList<Ability>(), null);
        pokemonsTrainer.add(pokemonDetail);
        Entrenador entrenador = new Entrenador("Ash", 100,items, pokemonsTrainer);
        boolean[] checks = checkIfPokemonIsCaptured(entrenador);

        tvNomPokedex = (TextView) itemView.findViewById(R.id.tvNamePokemon); //El item view es internament el view holder,no es un objecte creat per nosaltres

        ivPokedex = (ImageView) itemView.findViewById(R.id.ivPokemonBack);
        imageViewFront = (ImageView) itemView.findViewById(R.id.ivPokemonFront);

        tvType = (TextView) itemView.findViewById(R.id.tvType); //El item view es internament el view holder,no es un objecte creat per nosaltres
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        tvStats = (TextView) itemView.findViewById(R.id.tvStats); //El item view es internament el view holder,no es un objecte creat per nosaltres

        tvError = (TextView) itemView.findViewById(R.id.tvError);
        llBalls = (LinearLayout) itemView.findViewById(R.id.llCapturaBall);

        tvNomPokedex.setText(pokedex.getName());
        tvType.setText(pokedex.getTypes().toString());
        tvDescription.setText(pokedex.getDescription());
        //--- Importación de los botones
        btnPokeball = (Button) itemView.findViewById(R.id.Button1_pokeball);
        btnSuperball = (Button) itemView.findViewById(R.id.Button1_superball);
        btnUltraball = (Button) itemView.findViewById(R.id.Button1_ultraball);
        btnMasterball = (Button) itemView.findViewById(R.id.Button1_masterball);

        tvSkills = (TextView) itemView.findViewById(R.id.tvHabilidades); //El item view es internament el view holder,no es un objecte creat per nosaltres

        if (checks[0]){
            tvError.setText("You have already captured 6 pokemons");
            llBalls.setVisibility(View.GONE);
        } else if (checks[1]){
            tvError.setText("You have already captured this pokemon");
            llBalls.setVisibility(View.GONE);
        }

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

    private boolean[] checkIfPokemonIsCaptured(Entrenador entrenador) {
        boolean[] captured = new boolean[2];
        if (entrenador.getlPokedex().size() > 6) {
            captured[0] = true;
        } else {
            for (PokemonDetail pokemonDetail : entrenador.getlPokedex()) {
                if (pokemonDetail.getNamePokemon().equals(pokedex.getName())){
                    captured[1] = true;
                }
            }
        }
        return captured;
    }
}