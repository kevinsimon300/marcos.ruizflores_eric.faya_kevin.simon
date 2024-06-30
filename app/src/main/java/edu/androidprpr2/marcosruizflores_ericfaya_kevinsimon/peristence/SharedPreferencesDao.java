package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.peristence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Entrenador;
import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.PokemonCapturado;

public class SharedPreferencesDao {

    private static final String PREFS_NAME = "trainer_prefs";
    private static final String KEY_MONEY = "money";
    private static final String KEY_NAME = "name";
    private static final String KEY_POKEBALLS = "pokeballs";
    private static final String KEY_SUPERBALLS = "superballs";
    private static final String KEY_ULTRABALLS = "ultraballs";
    private static final String KEY_MASTERBALLS = "masterballs";
    private static final String KEY_POKEMON_NAME_PREFIX = "pokemon_name_";
    private static final String KEY_POKEMON_FRONT_IMAGE_PREFIX = "pokemon_front_image_";
    private static final String KEY_POKEMON_CAUGHT_BALL_PREFIX = "pokemon_caught_ball_";
    private static final String KEY_POKEMON_COUNT = "pokemon_count";

    public static void saveTrainer(Context context, Entrenador entrenador) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_MONEY, entrenador.getMoney());
        editor.putString(KEY_NAME, entrenador.getName());
        editor.putInt(KEY_POKEBALLS, entrenador.getPokeballs());
        editor.putInt(KEY_SUPERBALLS, entrenador.getSuperballs());
        editor.putInt(KEY_ULTRABALLS, entrenador.getUltraballs());
        editor.putInt(KEY_MASTERBALLS, entrenador.getMasterballs());

        List<PokemonCapturado> pokemonCapturados = entrenador.getPokemonCapturados();
        editor.putInt(KEY_POKEMON_COUNT, pokemonCapturados.size());
        for (int i = 0; i < pokemonCapturados.size(); i++) {
            PokemonCapturado pokemon = pokemonCapturados.get(i);
            editor.putString(KEY_POKEMON_NAME_PREFIX + i, pokemon.getName());
            editor.putString(KEY_POKEMON_FRONT_IMAGE_PREFIX + i, pokemon.getFrontImage());
            editor.putString(KEY_POKEMON_CAUGHT_BALL_PREFIX + i, pokemon.getCapturedPokeballImage());
        }

        editor.apply();
    }

    public static Entrenador getTrainer(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int money = sharedPreferences.getInt(KEY_MONEY, 0);
        String name = sharedPreferences.getString(KEY_NAME, null);
        int pokeballs = sharedPreferences.getInt(KEY_POKEBALLS, 0);
        int superballs = sharedPreferences.getInt(KEY_SUPERBALLS, 0);
        int ultraballs = sharedPreferences.getInt(KEY_ULTRABALLS, 0);
        int masterballs = sharedPreferences.getInt(KEY_MASTERBALLS, 0);

        int pokemonCount = sharedPreferences.getInt(KEY_POKEMON_COUNT, 0);
        List<PokemonCapturado> pokemonCapturados = new ArrayList<>();
        for (int i = 0; i < pokemonCount; i++) {
            String pokemonName = sharedPreferences.getString(KEY_POKEMON_NAME_PREFIX + i, null);
            String frontImage = sharedPreferences.getString(KEY_POKEMON_FRONT_IMAGE_PREFIX + i, null);
            String capturedPokeballImage = sharedPreferences.getString(KEY_POKEMON_CAUGHT_BALL_PREFIX + i, null);
            pokemonCapturados.add(new PokemonCapturado(pokemonName, frontImage, capturedPokeballImage));
        }

        return new Entrenador(money, name, pokeballs, superballs, ultraballs, masterballs, pokemonCapturados);
    }

    public static void removeCapturedPokemon(Context context, PokemonCapturado pokemonToRemove) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int pokemonCount = sharedPreferences.getInt(KEY_POKEMON_COUNT, 0);
        List<PokemonCapturado> pokemonCapturados = new ArrayList<>();
        for (int i = 0; i < pokemonCount; i++) {
            String pokemonName = sharedPreferences.getString(KEY_POKEMON_NAME_PREFIX + i, null);
            String frontImage = sharedPreferences.getString(KEY_POKEMON_FRONT_IMAGE_PREFIX + i, null);
            String capturedPokeballImage = sharedPreferences.getString(KEY_POKEMON_CAUGHT_BALL_PREFIX + i, null);

            PokemonCapturado pokemon = new PokemonCapturado(pokemonName, frontImage, capturedPokeballImage);
            if (!pokemon.getName().equals(pokemonToRemove.getName())) {
                pokemonCapturados.add(pokemon);
            }
        }

        editor.putInt(KEY_POKEMON_COUNT, pokemonCapturados.size());
        for (int i = 0; i < pokemonCapturados.size(); i++) {
            PokemonCapturado pokemon = pokemonCapturados.get(i);
            editor.putString(KEY_POKEMON_NAME_PREFIX + i, pokemon.getName());
            editor.putString(KEY_POKEMON_FRONT_IMAGE_PREFIX + i, pokemon.getFrontImage());
            editor.putString(KEY_POKEMON_CAUGHT_BALL_PREFIX + i, pokemon.getCapturedPokeballImage());
        }

        editor.apply();
    }
}
