package com.example.outofthisworld.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

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
        apod.date = jsonObject.getString("date");
        apod.explanation = jsonObject.getString("explaination");
        apod.url = jsonObject.getString("url");
        apod.title = jsonObject.getString("title");
        apod.type = jsonObject.getString("media_type");
        if (jsonObject.has("copyright")) {
            apod.copyright = jsonObject.getString("copyright");
        }
        else {
            apod.copyright =  "Public Domain";
        }
    }

    // Method to get multiple APODs from list of JSON objects
    public static List<APOD> fromJsonArray(JSONArray apodJsonArray) throws JSONException {
        List<APOD> apods = new ArrayList<>();
        for (int i = 0; i <apodJsonArray.length(); i++) {
            apods.add(new APOD(apodJsonArray.getJSONObject(i)));
        }
        return apods;
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
