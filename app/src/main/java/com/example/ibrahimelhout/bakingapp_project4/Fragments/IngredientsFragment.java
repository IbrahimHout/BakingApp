package com.example.ibrahimelhout.bakingapp_project4.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahimelhout.bakingapp_project4.Adapters.IngredientAdapter;
import com.example.ibrahimelhout.bakingapp_project4.Models.Ingredient;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.R;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    IngredientAdapter adapter;


    Unbinder unbinder;
    @BindView(R.id.ingredientRc)
    RecyclerView ingredientRc;

    public IngredientsFragment() {
        // Required empty public constructor
    }


    Bundle arguments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);


        if (arguments != null) {
            Recipe r = arguments.getParcelable(Constants.RECIPE_KEY);
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) r.getIngredients();
            adapter = new IngredientAdapter(ingredients, getContext());
            ingredientRc.setAdapter(adapter);
            ingredientRc.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
