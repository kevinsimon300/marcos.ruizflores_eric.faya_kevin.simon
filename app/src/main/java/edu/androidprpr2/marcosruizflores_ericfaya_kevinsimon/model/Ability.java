package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Ability {
    private String name;
    private boolean is_hidden;
    private double probability;

    public Ability(String name, boolean is_hidden, double probability) {
        this.name = name;
        this.is_hidden = is_hidden;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("is_hidden", is_hidden);
            jsonObject.put("probability", probability);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static Ability fromString(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String name = jsonObject.getString("name");
            boolean is_hidden = jsonObject.getBoolean("is_hidden");
            double probability = jsonObject.getDouble("probability");
            return new Ability(name, is_hidden, probability);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
