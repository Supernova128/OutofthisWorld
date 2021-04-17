package com.example.outofthisworld.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.outofthisworld.Models.APOD;
import com.example.outofthisworld.R;

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
            String imageURL;
            imageURL = apod.getUrl();
            Glide.with(context)
                    .load(imageURL)
                    .placeholder(Drawable.createFromPath("http://via.placeholder.com/300.png"))
                    .into(ivPoster);
        }
    }
}
