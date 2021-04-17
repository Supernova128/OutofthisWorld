package com.example.outofthisworld.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Parcel
public class APOD {

    // Public values
    public String explanation;
    public String title;
    public String url;
    public String date;
    public String copyright;
    public String type;

    // empty constructor For parcel library
    public APOD(){}

    // Method to get one apod from JSON object
    public APOD(JSONObject jsonObject) throws JSONException {
        APOD apod = new APOD();
        date = jsonObject.getString("date");
        explanation = jsonObject.getString("explanation");
        url = jsonObject.getString("url");
        title = jsonObject.getString("title");
        type = jsonObject.getString("media_type");
        if (jsonObject.has("copyright")) {
            copyright = jsonObject.getString("copyright");
        }
        else {
            copyright =  "Public Domain";
        }
        if ("video".equals(type)){
            url = getYoutubeVideoIdFromUrl(url);
        }

    }

    // Method to get multiple APODs from list of JSON objects
    public static List<APOD> fromJsonArray(JSONArray apodJsonArray) throws JSONException {
        List<APOD> apods = new ArrayList<>();
        for (int i = apodJsonArray.length() - 1; i >= 0; i--) {
            APOD apod = new APOD(apodJsonArray.getJSONObject(i));
            Log.i("APOD",apod.getDate());
            apods.add(apod);
        }
        return apods;
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
    public String getCopyright() {
        return copyright;
    }

    public String getType() {
        return type;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
