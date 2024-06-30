package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private int id;
    private String weight;
    private String height;
    private String stat0;
    private String stat1;
    private String stat2;
    private String stat3;
    private String stat4;
    private String stat5;
    private String imageUrl;
    private String backImage;
    private List<String> types;
    private String description;
    private ArrayList<Ability> abilities;
    private String backShiny;
    private String frontShiny;
    private int indexEvolution;
    private String pokeballType;

    public Pokemon(String name, int id, String imageUrl, String backImage, List<String> types, String weight, String height, String description, String stat0, String stat1, String stat2, String stat3, String stat4, String stat5, ArrayList<Ability> abilities, String backShiny, String frontShiny, int indexEvolution, String pokeballType) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.backImage = backImage;
        this.types = types;
        this.description = description;
        this.weight = weight;
        this.height = height;
        this.stat0 = stat0;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
        this.abilities = abilities;
        this.backShiny = backShiny;
        this.frontShiny = frontShiny;
        this.indexEvolution = indexEvolution;
        this.pokeballType = pokeballType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getStat0() {
        return stat0;
    }

    public void setStat0(String stat0) {
        this.stat0 = stat0;
    }

    public String getStat1() {
        return stat1;
    }

    public void setStat1(String stat1) {
        this.stat1 = stat1;
    }

    public String getStat2() {
        return stat2;
    }

    public void setStat2(String stat2) {
        this.stat2 = stat2;
    }

    public String getStat3() {
        return stat3;
    }

    public void setStat3(String stat3) {
        this.stat3 = stat3;
    }

    public String getStat4() {
        return stat4;
    }

    public void setStat4(String stat4) {
        this.stat4 = stat4;
    }

    public String getStat5() {
        return stat5;
    }

    public void setStat5(String stat5) {
        this.stat5 = stat5;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public int getIndexEvolution() {
        return indexEvolution;
    }

    public void setIndexEvolution(int indexEvolution) {
        this.indexEvolution = indexEvolution;
    }

    public String getPokeballType() {
        return pokeballType;
    }

    public void setPokeballType(String pokeballType) {
        this.pokeballType = pokeballType;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("id", id);
            jsonObject.put("imageUrl", imageUrl);
            jsonObject.put("backImage", backImage);
            jsonObject.put("types", new JSONArray(types));
            jsonObject.put("weight", weight);
            jsonObject.put("height", height);
            jsonObject.put("description", description);
            jsonObject.put("stat0", stat0);
            jsonObject.put("stat1", stat1);
            jsonObject.put("stat2", stat2);
            jsonObject.put("stat3", stat3);
            jsonObject.put("stat4", stat4);
            jsonObject.put("stat5", stat5);
            JSONArray abilitiesArray = new JSONArray();
            for (Ability ability : abilities) {
                abilitiesArray.put(new JSONObject(ability.toString()));
            }
            jsonObject.put("abilities", abilitiesArray);
            jsonObject.put("backShiny", backShiny);
            jsonObject.put("frontShiny", frontShiny);
            jsonObject.put("indexEvolution", indexEvolution);
            jsonObject.put("pokeballType", pokeballType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static Pokemon fromString(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String name = jsonObject.getString("name");
            int id = jsonObject.getInt("id");
            String imageUrl = jsonObject.getString("imageUrl");
            String backImage = jsonObject.getString("backImage");
            List<String> types = new ArrayList<>();
            JSONArray typesArray = jsonObject.getJSONArray("types");
            for (int i = 0; i < typesArray.length(); i++) {
                types.add(typesArray.getString(i));
            }
            String weight = jsonObject.getString("weight");
            String height = jsonObject.getString("height");
            String description = jsonObject.getString("description");
            String stat0 = jsonObject.getString("stat0");
            String stat1 = jsonObject.getString("stat1");
            String stat2 = jsonObject.getString("stat2");
            String stat3 = jsonObject.getString("stat3");
            String stat4 = jsonObject.getString("stat4");
            String stat5 = jsonObject.getString("stat5");
            ArrayList<Ability> abilities = new ArrayList<>();
            JSONArray abilitiesArray = jsonObject.getJSONArray("abilities");
            for (int i = 0; i < abilitiesArray.length(); i++) {
                abilities.add(Ability.fromString(abilitiesArray.getJSONObject(i).toString()));
            }
            String backShiny = jsonObject.getString("backShiny");
            String frontShiny = jsonObject.getString("frontShiny");
            int indexEvolution = jsonObject.getInt("indexEvolution");
            String pokeballType = jsonObject.getString("pokeballType");

            return new Pokemon(name, id, imageUrl, backImage, types, weight, height, description, stat0, stat1, stat2, stat3, stat4, stat5, abilities, backShiny, frontShiny, indexEvolution, pokeballType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
