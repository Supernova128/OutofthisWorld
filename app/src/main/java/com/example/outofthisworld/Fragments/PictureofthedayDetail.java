package com.example.outofthisworld.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.outofthisworld.BuildConfig;
import com.example.outofthisworld.Models.APOD;
import com.example.outofthisworld.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;


public class PictureofthedayDetail extends YouTubePlayerSupportFragment {
    TextView tvExplanation;
    TextView tvCopyright;
    TextView tvTitle;
    YouTubePlayerView youTubePlayerView;
    ImageView ivPicture;

    APOD apod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pictureoftheday_detail, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvExplanation = view.findViewById(R.id.tvOverview);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvCopyright = view.findViewById(R.id.tvCopyright);
        ivPicture = view.findViewById(R.id.ivPicture2);
        youTubePlayerView = view.findViewById(R.id.player);
        // Set values to views
        tvTitle.setText(apod.getTitle());
        tvExplanation.setText(apod.getExplanation());
        tvCopyright.setText(apod.getCopyright());

        if (apod.type == "video") {
            youTubePlayerView.setVisibility(view.VISIBLE);
            ivPicture.setVisibility(View.GONE);


        }
        else if (apod.type == "image") {
            youTubePlayerView.setVisibility(view.GONE);
            ivPicture.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(apod.url)
                    .placeholder(Drawable.createFromPath("http://via.placeholder.com/300.png"))
                    .into(ivPicture);
        }

    }

    private void initializeYoutube(String yturl)  {
        youTubePlayerView.initialize(BuildConfig.Youtube_Key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");
                youTubePlayer.cueVideo(yturl);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    };

    public void setArguments(Parcelable arguments) {
        this.apod = Parcels.unwrap(arguments);
    }
}