package com.example.ibrahimelhout.bakingapp_project4;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.ibrahimelhout.bakingapp_project4.Fragments.IngredientsFragment;
import com.example.ibrahimelhout.bakingapp_project4.Fragments.StepDetailsFragment;
import com.example.ibrahimelhout.bakingapp_project4.Fragments.StepsListFragment;
import com.example.ibrahimelhout.bakingapp_project4.Models.Ingredient;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsListActivity extends AppCompatActivity {


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    FrameLayout frameMaster;
    FrameLayout frameDetails;

    FragmentManager fragmentManager;



    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fragmentManager = getSupportFragmentManager();


        if (getIntent() != null) {

            Recipe recipe = getIntent().getParcelableExtra(Constants.RECIPE_KEY);


            if (recipe != null) {

                toolbar.setTitle(recipe.getName());

                if (findViewById(R.id.frameDetails) != null) {
                    // The detail container view will be present only in the
                    // large-screen layouts (res/values-w900dp).
                    // If this view is present, then the
                    // activity should be in two-pane mode.
                    mTwoPane = true;

                    frameMaster = findViewById(R.id.frameMaster);
                    frameDetails =findViewById(R.id.frameDetails);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.RECIPE_KEY,recipe);
                    bundle.putBoolean(Constants.IS_TWO_PANE,mTwoPane);


                    StepsListFragment stepsListFragment = new StepsListFragment();
                    stepsListFragment.setArguments(bundle);

                    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                    stepDetailsFragment.setArguments(bundle);


                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(bundle);


                    fragmentManager.beginTransaction().add(R.id.frameMaster,stepsListFragment).commit();
                    fragmentManager.beginTransaction().add(R.id.frameDetails,ingredientsFragment).commit();



                } else {

                    mTwoPane = false;

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.RECIPE_KEY,recipe);
                    bundle.putBoolean(Constants.IS_TWO_PANE,mTwoPane);

                    frameMaster = findViewById(R.id.frameMaster);
                    StepsListFragment stepsListFragment = new StepsListFragment();
                    stepsListFragment.setArguments(bundle);

                    fragmentManager.beginTransaction().add(R.id.frameMaster,stepsListFragment).commit();




                }


            }
        }


    }


}
