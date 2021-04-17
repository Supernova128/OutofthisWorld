package com.example.outofthisworld.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.outofthisworld.Fragments.PictureofthedayDetail;
import com.example.outofthisworld.Models.APOD;
import com.example.outofthisworld.R;

import org.parceler.Parcels;

import java.util.List;

public class APODAdapter extends RecyclerView.Adapter<APODAdapter.ViewHolder>{


    Context context;
    List<APOD> APODs;

    public APODAdapter(Context context, List<APOD> APODs) {
        this.context = context;
        this.APODs = APODs;
    }

    @NonNull
    @Override
    public APODAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View APODview = LayoutInflater.from(context).inflate(R.layout.item_apod, parent, false);
        return new ViewHolder(APODview);
    }

    @Override
    public int getItemCount() {
        return APODs.size();
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the passed in position
        APOD apod = APODs.get(position);
        holder.bind(apod);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvDate;
        TextView tvAuthor;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivPoster = itemView.findViewById(R.id.ivPicture);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(APOD apod) {
            tvTitle.setText(apod.getTitle());
            tvDate.setText(apod.getDate());
            String type = apod.getType();
            String imageURL;
            if ("image".equals(type)){
                imageURL = apod.getUrl();
            }
            else if ("video".equals(type)){
                imageURL = getYoutubeThumbnailUrlFromVideoUrl(apod.getUrl());
                Log.i("APODAdapter",imageURL);
            }
            else {
                imageURL = "https://via.placeholder.com/300.png";
            }
            Glide.with(context)
                    .load(imageURL)
                    .placeholder(Drawable.createFromPath("https://via.placeholder.com/300.png"))
                    .into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment;
                    fragment = new PictureofthedayDetail();
                    Bundle b = new Bundle();
                    b.putParcelable("apod",Parcels.wrap(apod));
                    fragment.setArguments(b);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });
        }
        public String getYoutubeThumbnailUrlFromVideoUrl(String id) {
            return "https://img.youtube.com/vi/"+id+ "/0.jpg";
        }

    }
}
