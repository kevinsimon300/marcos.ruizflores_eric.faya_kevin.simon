package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.widget.ImageView;

import java.util.List;

public class PokemonDetail {
    private String namePokemon;
    private String imageViewFront;
    private String imageViewPokeball;
    private List<String> tipusPokemon;
    private String descripcioPokemon;
    private List<Ability> abilities;
    private Stats stats;

    public PokemonDetail(String namePokemon, String imageViewFront, String imageViewPokeball, List<String> tipusPokemon, String descripcioPokemon, List<Ability> abilities, Stats stats) {
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

    public String getImageViewFront() {
        return imageViewFront;
    }

    public void setImageViewFront(String imageViewFront) {
        this.imageViewFront = imageViewFront;
    }

    public String getImageViewPokeball() {
        return imageViewPokeball;
    }

    public void setImageViewPokeball(String imageViewPokeball) {
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
