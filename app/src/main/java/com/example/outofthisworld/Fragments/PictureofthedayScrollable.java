package com.example.outofthisworld.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.outofthisworld.Adapters.APODAdapter;
import com.example.outofthisworld.BuildConfig;
import com.example.outofthisworld.Models.APOD;
import com.example.outofthisworld.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Headers;


public class PictureofthedayScrollable extends Fragment {

    public static final String APIURL = "https://api.nasa.gov/planetary/apod?api_key=" + BuildConfig.NASA_KEY;
    public static final String TAG = "FragAPOD";


    private RecyclerView rvPics;
    protected APODAdapter adapter;
    protected List<APOD> APODs;
    protected SwipeRefreshLayout swipeContainer;
    protected String Enddate;
    protected String Startdate;
    private EndlessRecyclerViewScrollListener scrollListener;


    public PictureofthedayScrollable() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pictureoftheday, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPics = view.findViewById(R.id.rvPictures);
        APODs = new ArrayList<>();
        adapter = new APODAdapter(getContext(),APODs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());


        rvPics.setAdapter(adapter);
        rvPics.setLayoutManager(layoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) throws ParseException {
                Log.i(TAG,"Loading More Pics");
                Getmorepics();
            }
        };
        rvPics.addOnScrollListener(scrollListener);
        try {
            Getmorepics();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    protected void Getmorepics() throws ParseException {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (APODs.isEmpty()) {
            Enddate = df.format(cal.getTime());
        } else {
            Enddate = APODs.get(APODs.size() - 1).getDate();
            cal.setTime(df.parse(Enddate));
            cal.add(Calendar.DATE, -1);
            Enddate = df.format(cal.getTime());
        }
        cal.add(Calendar.DATE, -15);
        Startdate = df.format(cal.getTime());
        AsyncHttpClient client = new AsyncHttpClient();
        String FullURL = APIURL + "&start_date=" + Startdate + "&end_date=" + Enddate;
        client.get(FullURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONArray results = json.jsonArray;
                try {
                    APODs.addAll(APOD.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.valueOf(APODs.size()));
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG,"Fail");
                    return;
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG,s);
                return;
            }
        });
    }
}