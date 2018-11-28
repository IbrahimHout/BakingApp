package com.example.ibrahimelhout.bakingapp_project4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ibrahimelhout.bakingapp_project4.Adapters.StepsAdapter;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.R;
import com.example.ibrahimelhout.bakingapp_project4.StepDetailsActivity;
import com.example.ibrahimelhout.bakingapp_project4.StepsListActivity;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;

public class StepsListFragment extends Fragment {

    @BindView(R.id.buttonIngredient)
    Button buttonIngredient;
    @BindView(R.id.recyclerSteps)
    RecyclerView recyclerSteps;
    Unbinder unbinder;


    Bundle arguments;

    StepsAdapter stepsAdapter;
    public StepsListFragment() {
        // Required empty public constructor
    }



    boolean isTwoPane;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);
        unbinder = ButterKnife.bind(this, view);


        context = getContext();


        if (arguments !=null){

            final Recipe recipe = (Recipe) arguments.get(Constants.RECIPE_KEY);

            isTwoPane = arguments.getBoolean(Constants.IS_TWO_PANE);
            stepsAdapter = new StepsAdapter((StepsListActivity)getActivity(),recipe,isTwoPane);
            recyclerSteps.setAdapter(stepsAdapter);
            recyclerSteps.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerSteps.addItemDecoration(itemDecoration);

            if (isTwoPane){

                buttonIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        IngredientsFragment ingredientsFragment = new IngredientsFragment();
                        ingredientsFragment.setArguments(arguments);
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameDetails,ingredientsFragment).commit();

                    }
                });


            }else {

                buttonIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, StepDetailsActivity.class);
                        intent.putExtra(Constants.TYPE,Constants.TYPE_INGREDIENT);
                        intent.putExtra(Constants.RECIPE_KEY,arguments);
                        intent.putExtra(Constants.IS_TWO_PANE,isTwoPane);
                        context.startActivity(intent);

                    }
                });


            }

        }else {

            Log.d(TAG, "onCreateView: no arguments");
        }


        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
