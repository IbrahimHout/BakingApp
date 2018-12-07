package com.example.ibrahimelhout.bakingapp_project4.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibrahimelhout.bakingapp_project4.Models.Recipe;
import com.example.ibrahimelhout.bakingapp_project4.Models.Step;
import com.example.ibrahimelhout.bakingapp_project4.R;
import com.example.ibrahimelhout.bakingapp_project4.Utils.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;


public class StepDetailsFragment extends Fragment {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    private SimpleExoPlayer mExoPlayer;


    @BindView(R.id.stepDescDetails)
    TextView stepDescDetails;
    @BindView(R.id.backButton)
    Button backButton;
    @BindView(R.id.nextButton)
    Button nextButton;
    Unbinder unbinder;


    Recipe recipe;

    Step step;

    int position;
    int total;


    Bundle arguments;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        unbinder = ButterKnife.bind(this, view);


        if (arguments.containsKey(Constants.RECIPE_KEY)) {
            recipe = arguments.getParcelable(Constants.RECIPE_KEY);
            position = arguments.getInt(Constants.STEP_NUM, 0);

            total = recipe.getSteps().size();

            step = recipe.getSteps().get(position);
            populateStep(step);

        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;

                releaseVideoAssets();
                populateStep(recipe.getSteps().get(position));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                releaseVideoAssets();
                populateStep(recipe.getSteps().get(position));
            }
        });


//            populateStep(step);

        return view;
    }

    private void populateStep(Step step) {

        if (position == total - 1) nextButton.setEnabled(false);
        else nextButton.setEnabled(true);

        if (position == 0) backButton.setEnabled(false);
        else backButton.setEnabled(true);

        stepDescDetails.setText(step.getDescription());

//        String mVideoUrl = "https://s3-us-west-2.amazonaws.com/askquran-media/video/Arabic_Efasy_001_001_007.mp4";
        String mVideoUrl = step.getVideoURL();
        Log.d(TAG, "populateStep: movie url" + mVideoUrl);
        String mThumbnail = step.getThumbnailURL();
        Log.d(TAG, "populateStep: thumpnail url" + mThumbnail);

        if (mThumbnail.contains(getString(R.string.mp4))) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mThumbnail));

        } else if (mVideoUrl.contains(getString(R.string.mp4))) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mVideoUrl));
        } else {

            playerView.setVisibility(View.GONE);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            initializePlayer(mediaUri);


        } else {


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));


//            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


//            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
//            playerView.setPlayer(mExoPlayer);


//            String userAgent = Util.getUserAgent(getContext(), "");

            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    defaultHttpDataSourceFactory,
                    extractorsFactory,
                    null,
                    null);


            playerView.setPlayer(mExoPlayer);


            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mExoPlayer!=null){
            outState.putLong(Constants.POSITION,mExoPlayer.getCurrentPosition());
        }
        outState = getArguments();

    }

    @Override
    public void onResume() {
        super.onResume();

        String mVideoUrl = step.getVideoURL();
        Log.d(TAG, "populateStep: movie url" + mVideoUrl);
        String mThumbnail = step.getThumbnailURL();
        Log.d(TAG, "populateStep: thumpnail url" + mThumbnail);

        if (mThumbnail.contains(getString(R.string.mp4))) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mThumbnail));

        } else if (mVideoUrl.contains(getString(R.string.mp4))) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mVideoUrl));
        } else {

            playerView.setVisibility(View.GONE);
        }
    }





    @Override
    public void onPause() {
        super.onPause();
        releaseVideoAssets();

    }

    private void releaseVideoAssets() {
        if (mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }
}
