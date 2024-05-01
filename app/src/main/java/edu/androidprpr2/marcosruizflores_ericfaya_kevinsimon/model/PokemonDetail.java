package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.widget.ImageView;

import java.util.List;

public class PokemonDetail {
    private String namePokemon;
    private ImageView imageViewFront;
    private ImageView imageViewPokeball;
    private List<String> tipusPokemon;
    private String descripcioPokemon;
    private List<Ability> abilities; // Lista de habilidades del Pokémon
    private Stats stats; // Estadísticas del

    public PokemonDetail(String namePokemon, ImageView imageViewFront, ImageView imageViewPokeball, List<String> tipusPokemon, String descripcioPokemon, List<Ability> abilities, Stats stats) {
        this.namePokemon = namePokemon;
        this.imageViewFront = imageViewFront;
        this.imageViewPokeball = imageViewPokeball;
        this.tipusPokemon = tipusPokemon;
        this.descripcioPokemon = descripcioPokemon;
        this.abilities = abilities;
        this.stats = stats;
    }

    public String getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(String namePokemon) {
        this.namePokemon = namePokemon;
    }

    public ImageView getImageViewFront() {
        return imageViewFront;
    }

    public void setImageViewFront(ImageView imageViewFront) {
        this.imageViewFront = imageViewFront;
    }

    public ImageView getImageViewPokeball() {
        return imageViewPokeball;
    }

    public void setImageViewPokeball(ImageView imageViewPokeball) {
        this.imageViewPokeball = imageViewPokeball;
    }

    public List<String> getTipusPokemon() {
        return tipusPokemon;
    }

    public void setTipusPokemon(List<String> tipusPokemon) {
        this.tipusPokemon = tipusPokemon;
    }

    public String getDescripcioPokemon() {
        return descripcioPokemon;
    }

    public void setDescripcioPokemon(String descripcioPokemon) {
        this.descripcioPokemon = descripcioPokemon;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
