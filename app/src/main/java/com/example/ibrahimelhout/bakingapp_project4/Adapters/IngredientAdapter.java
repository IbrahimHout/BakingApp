package com.example.ibrahimelhout.bakingapp_project4.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahimelhout.bakingapp_project4.Models.Ingredient;
import com.example.ibrahimelhout.bakingapp_project4.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {


    ArrayList<Ingredient> ingredients;
    Context context;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_ingredient, null, false));

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int i) {

        Ingredient ingredient = ingredients.get(i);
        ingredientViewHolder.textViewIngreadient.setText(ingredient.getIngredient());
        ingredientViewHolder.textSerieal.setText((i+1)+"");
        ingredientViewHolder.textQuantity.setText(ingredient.getQuantity()+"");
        ingredientViewHolder.textViewMeasure.setText(ingredient.getMeasure());


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView textQuantity;
        TextView textSerieal;
        TextView textViewIngreadient;
        TextView textViewMeasure;


        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            textSerieal = itemView.findViewById(R.id.numberTV);
            textQuantity= itemView.findViewById(R.id.quantityTv);
            textViewIngreadient= itemView.findViewById(R.id.ingredientTV);
            textViewMeasure= itemView.findViewById(R.id.measureTV);

        }
    }
}
