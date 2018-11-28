package com.example.ibrahimelhout.bakingapp_project4.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahimelhout.bakingapp_project4.Fragments.StepDetailsFragment;
import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.R;
import com.example.ibrahimelhout.bakingapp_project4.StepDetailsActivity;
import com.example.ibrahimelhout.bakingapp_project4.StepsListActivity;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;

import static android.support.constraint.Constraints.TAG;

public  class StepsAdapter
        extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private final StepsListActivity mParentActivity;
    private final Recipe mRecipe;
    private final boolean mTwoPane;



    public StepsAdapter(StepsListActivity parent,
                        Recipe recipe,
                        boolean twoPane) {
        mRecipe = recipe;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RECIPE_KEY,mRecipe);
        bundle.putBoolean(Constants.IS_TWO_PANE,mTwoPane);
        bundle.putInt(Constants.STEP_NUM,holder.getAdapterPosition());



            holder.mIdView.setText((position )+ " - "+( mRecipe.getSteps().get(position )).getShortDescription());

            holder.mIdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (mTwoPane){


                        Log.e(TAG, "onClick: position "+position );

                        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                        stepDetailsFragment.setArguments(bundle);
                        ((AppCompatActivity) mParentActivity).getSupportFragmentManager().beginTransaction().replace(R.id.frameDetails,stepDetailsFragment).commit();

                    }else {


                        Intent intent = new Intent(mParentActivity, StepDetailsActivity.class);
                        intent.putExtra(Constants.TYPE,Constants.TYPE_STEP);
                        intent.putExtra(Constants.RECIPE_KEY,bundle);
                        mParentActivity.startActivity(intent);
                    }

                }
            });




    }


    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        MyViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}