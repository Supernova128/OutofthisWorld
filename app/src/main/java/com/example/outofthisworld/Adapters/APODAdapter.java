package com.example.outofthisworld.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            /* Not working
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureofthedayDetail fragment;
                    fragment = new PictureofthedayDetail();
                    Bundle b = new Bundle();
                    fragment.setArguments(Parcels.wrap(apod));
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });
            */
        }
        public String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
            return "https://img.youtube.com/vi/"+getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
        }
        public String getYoutubeVideoIdFromUrl(String inUrl) {
            inUrl = inUrl.replace("&feature=youtu.be", "");
            if (inUrl.toLowerCase().contains("youtu.be")) {
                return inUrl.substring(inUrl.lastIndexOf("/") + 1);
            }
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(inUrl);
            if (matcher.find()) {
                return matcher.group();
            }
            return null;
        }
    }
}
