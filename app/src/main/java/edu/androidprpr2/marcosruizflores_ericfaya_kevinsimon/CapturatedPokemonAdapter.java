package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import android.content.Context;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;

public class CapturatedPokemonAdapter extends RecyclerView.Adapter<CapturatedPokemonAdapter.PokemonViewHolder> {

    private List<PokemonCapturado> pokemonList;
    private Context context;
    private static final String TAG = "CapturatedPokemonAdapter";


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
        Log.d(TAG, "Nombre del recurso drawable: " + pokemon.getCapturedPokeballImage());
        Picasso.get().load(pokemon.getCapturedPokeballImage()).into(holder.imageViewBack);

        String resourceName = pokemon.getCapturedPokeballImage();

        int resourceId = holder.itemView.getResources().getIdentifier(resourceName, "drawable", holder.itemView.getContext().getPackageName());

        holder.imageViewBack.setImageResource(resourceId);

        holder.btnDeletePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Índice: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                if (pokemonList.size() > 1) {
                    Toast.makeText(context, "Pokémon eliminado", Toast.LENGTH_SHORT).show();
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        PokemonCapturado pokemonToRemove = pokemonList.get(adapterPosition);
                        pokemonList.remove(holder.getAdapterPosition());

                        //TODO ELIMINAR DEL JSON
                        try {
                            File file = new File(context.getFilesDir(), "Files/entrenador.json");
                            FileInputStream fis = new FileInputStream(file);
                            byte[] data = new byte[(int) file.length()];
                            fis.read(data);
                            fis.close();

                            String jsonString = new String(data, "UTF-8");
                            JSONObject datosEntrenador = new JSONObject(jsonString);

                            JSONArray pokemonCapturadosArray = datosEntrenador.getJSONArray("PokemonCapturados");

                            for (int i = 0; i < pokemonCapturadosArray.length(); i++) {
                                JSONObject pokemonJson = pokemonCapturadosArray.getJSONObject(i);
                                if (pokemonJson.getString("name").equals(pokemonToRemove.getName())) {
                                    pokemonCapturadosArray.remove(i);
                                    break;
                                }
                            }

                            datosEntrenador.put("PokemonCapturados", pokemonCapturadosArray);

                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                fos.write(datosEntrenador.toString(2).getBytes());
                                Log.d(TAG, "JSON actualizado en " + file.getAbsolutePath());
                            }

                            notifyItemRemoved(adapterPosition);

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(context, "No puedes eliminar el último Pokémon capturado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Picasso.get().load(pokemon.getCapturedPokeballImage()).into(holder.imageViewBack);

       // Pokedex pokemon = Pokedex.getInstance(context, pokemonList.get(0).getPokemonsList());
        //holder.textViewName.setText(pokemon.getPokemonsList().get(0).getName());

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

