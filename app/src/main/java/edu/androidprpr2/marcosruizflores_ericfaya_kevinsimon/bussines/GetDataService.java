package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.bussines;

import java.util.List;

import edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/pokemon?limit=15&offset=0")
    Call<List<Result>> getAllPokemonNames();
}