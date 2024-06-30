package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;

public class CapturatedPokemonAdapter extends RecyclerView.Adapter<CapturatedPokemonAdapter.PokemonViewHolder> {

    private List<PokemonCapturado> pokemonList;
    private Context context;
    private static final String TAG = "CapturedPokemonAdapter";
    private static final String PREFERENCES_FILE = "trainer_prefs";
    private static final String KEY_CAPTURED_POKEMON = "captured_pokemon";

    public CapturatedPokemonAdapter(List<PokemonCapturado> pokemonList, Context context) {
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
        PokemonCapturado pokemon = pokemonList.get(position);
        holder.textViewName.setText(pokemon.getName());

        Picasso.get().load(pokemon.getFrontImage()).into(holder.imageViewFront);

        String resourceName = pokemon.getCapturedPokeballImage();
        int resourceId = holder.itemView.getResources().getIdentifier(resourceName, "drawable", holder.itemView.getContext().getPackageName());
        holder.imageViewBack.setImageResource(resourceId);

        holder.btnDeletePokemon.setOnClickListener(v -> {
            if (pokemonList.size() > 1) {
                Toast.makeText(context, "Pokémon eliminado", Toast.LENGTH_SHORT).show();
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    pokemonList.remove(adapterPosition);
                    savePokemonListToPreferences(context, new ArrayList<>(pokemonList));
                    notifyItemRemoved(adapterPosition);
                }
            } else {
                Toast.makeText(context, "No puedes eliminar el último Pokémon capturado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void updatePokemonList(List<PokemonCapturado> newPokemonList) {
        this.pokemonList = newPokemonList;
        notifyDataSetChanged();
    }

    private void savePokemonListToPreferences(Context context, List<PokemonCapturado> pokemonList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        for (PokemonCapturado pokemon : pokemonList) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", pokemon.getName());
                jsonObject.put("frontImage", pokemon.getFrontImage());
                jsonObject.put("capturedPokeballImage", pokemon.getCapturedPokeballImage());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(KEY_CAPTURED_POKEMON, jsonArray.toString());
        editor.apply();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewFront;
        public ImageView imageViewBack;
        public Button btnDeletePokemon;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewFront = itemView.findViewById(R.id.imageViewFront);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            btnDeletePokemon = itemView.findViewById(R.id.btnDeletePokemon);
        }
    }
}
