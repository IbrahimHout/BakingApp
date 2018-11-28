package com.example.ibrahimelhout.bakingapp_project4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.ibrahimelhout.bakingapp_project4.Adapters.RecipesAdapter;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.Networking.RetrofitInterface;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipesActivity extends AppCompatActivity {


    private static final String TAG = "RecipesActivity";
    RetrofitInterface retrofitInterface;

    RecipesAdapter adapter;


    RecyclerView foodRecycler;

    RecyclerView foodRecycler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        foodRecycler = findViewById(R.id.foodRecycler);
        foodRecycler2 = findViewById(R.id.foodRecycler2);

        networkingHandlingMethod();


    }

    private void setOneLineAdapter(ArrayList<Recipe> recipes) {
        adapter = new RecipesAdapter(this, recipes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        foodRecycler.setAdapter(adapter);
        foodRecycler.setLayoutManager(linearLayoutManager);

    }

    private void setTwoLineAdapter(ArrayList<Recipe> recipes) {
        adapter = new RecipesAdapter(this, recipes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        foodRecycler2.setAdapter(adapter);
        foodRecycler2.setLayoutManager(gridLayoutManager);

    }


    private void networkingHandlingMethod() {

        Retrofit retrofit = new
                Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ArrayList<Recipe>> getFoods = retrofitInterface.getFoodTypes();


        getFoods.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Log.d(TAG, "onResponse: " + response.body().size());
                if (foodRecycler!=null){
                    setOneLineAdapter(response.body());
                }else {
                    setTwoLineAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
