package com.example.ibrahimelhout.bakingapp_project4.Networking;

import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {



    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getFoodTypes();



}
