package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Pokedex;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokedex> pokemonList;
    private Context context;

    public PokemonAdapter(List<Pokedex> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokedex pokemon = pokemonList.get(position);
        holder.textViewName.setText(pokemon.getName());

        // Picasso.get().load(pokemon.getPokemonImageUrl()).into(holder.imageViewFront);
        // Picasso.get().load(pokemon.getPokeballImageUrl()).into(holder.imageViewBack);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewFront;
        public ImageView imageViewBack;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewFront = itemView.findViewById(R.id.imageViewFront);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
        }
    }
}

