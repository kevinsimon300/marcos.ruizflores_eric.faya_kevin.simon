package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Item> items;

    public Shop() {
        items = new ArrayList<>();
        items.add(new Item("Pokeball", 200));
        items.add(new Item("Superball", 500));
        items.add(new Item("Ultraball", 1500));
        items.add(new Item("Masterball", 100000));
    }

    public List<Item> getItems() {
        return items;
    }}
