package com.example.ibrahimelhout.bakingapp_project4.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahimelhout.bakingapp_project4.MyAppWidgetProvider;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.R;
import com.example.ibrahimelhout.bakingapp_project4.StepsListActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolderRecipe> {

    private Context context;
    private ArrayList<Recipe> recipes;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolderRecipe onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderRecipe(LayoutInflater.from(context).inflate(R.layout.cell_recipe, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderRecipe viewHolderRecipe, int i) {

        final Recipe recipe = recipes.get(viewHolderRecipe.getAdapterPosition());
        viewHolderRecipe.recipeName.setText(recipe.getName());
//        viewHolderRecipe.recipeServings.setText(recipe.getServings()+" "+context.getString(R.string.servinfs));

        viewHolderRecipe.recipePic.setImageResource(R.drawable.error);

        viewHolderRecipe.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWidget(context,recipes.get(viewHolderRecipe.getAdapterPosition()));



                Intent intent = new Intent(context,StepsListActivity.class);
                intent.putExtra(Constants.RECIPE_KEY,recipe);

                context.startActivity(intent);
            }
        });
    }

    private void updateWidget(Context context, Recipe recipe ) {
        Log.d("aaaaa", "updateWidget: "+ recipe.getName() + recipe.getIngredients().toString());

        String name = recipe.getName();
        String ingredient = "";
        for (int i = 0; i < recipe.getIngredients().size(); i++) {

            String ing = recipe.getIngredients().get(i).getIngredient();
            ingredient =  ing + ". \n" + ingredient ;


        }
        Log.d("aaaaa", "updateWidget2: " + name+ "\n"+ingredient);


        Hawk.put(Constants.RECIPE_SAVED_FOR_WIDGET, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = new Bundle();
        int appWidgetId = bundle.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        MyAppWidgetProvider.updateWidget(context, appWidgetManager, appWidgetId, name,
                ingredient);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolderRecipe extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout linearLayout;

        @BindView(R.id.recipePic)
        ImageView recipePic;
        @BindView(R.id.recipeName)
        TextView recipeName;
        @BindView(R.id.recipeServings)
        TextView recipeServings;

        public ViewHolderRecipe(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }






}
