package com.example.outofthisworld.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.outofthisworld.Models.APOD;
import com.example.outofthisworld.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.parceler.Parcels;


public class PictureofthedayDetail extends Fragment {
    TextView tvExplanation;
    TextView tvCopyright;
    TextView tvTitle;
    ImageView ivPicture;
    YouTubePlayerView youTubePlayer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        APOD apod = Parcels.unwrap(getArguments().getParcelable("apod"));
        if ("video".equals(apod.getType())) {
            return inflater.inflate(R.layout.fragment_pictureoftheday_detailvideo, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_pictureoftheday_detailimage, container, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        APOD apod = Parcels.unwrap(getArguments().getParcelable("apod"));
        tvExplanation = view.findViewById(R.id.tvOverview);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvCopyright = view.findViewById(R.id.tvCopyright);
        tvTitle.setText(apod.getTitle());
        tvExplanation.setText(apod.getExplanation());
        tvCopyright.setText(apod.getCopyright());



        if ("video".equals(apod.getType())) {
            youTubePlayer = view.findViewById(R.id.youtube_player_view);
            getLifecycle().addObserver(youTubePlayer);
            youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = apod.getUrl();
                    youTubePlayer.loadVideo(videoId, 0);
                }
        });
        }
        else if ("image".equals(apod.getType())) {
            ivPicture = view.findViewById(R.id.ivPicture2);
            Glide.with(view.getContext())
                    .load(apod.getUrl())
                    .placeholder(Drawable.createFromPath("http://via.placeholder.com/300.png"))
                    .into(ivPicture);
        }

    }
}