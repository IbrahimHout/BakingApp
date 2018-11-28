package com.example.ibrahimelhout.bakingapp_project4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ibrahimelhout.bakingapp_project4.Fragments.IngredientsFragment;
import com.example.ibrahimelhout.bakingapp_project4.Fragments.StepDetailsFragment;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepsListActivity}.
 */
public class StepDetailsActivity extends AppCompatActivity {


    boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
//            ItemDetailFragment fragment = new ItemDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.item_detail_container, fragment)
//                    .commit();
        }

        Intent intent = getIntent();
        if (intent != null) {

            if (intent.getParcelableExtra(Constants.RECIPE_KEY) != null) {
                String type = intent.getStringExtra(Constants.TYPE);


//                Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_KEY);
                Bundle bundle = intent.getParcelableExtra(Constants.RECIPE_KEY);
                isTwoPane = intent.getBooleanExtra(Constants.IS_TWO_PANE, false);
                if (type.equals(Constants.TYPE_INGREDIENT)) {


                    bundle.putBoolean(Constants.IS_TWO_PANE, isTwoPane);




                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().add(R.id.detailsFrame, ingredientsFragment).commit();


                } else if (type.equals(Constants.TYPE_STEP)) {

                    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                    stepDetailsFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.detailsFrame,stepDetailsFragment).commit();



                }

            }
        }


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, StepsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
