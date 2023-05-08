package com.ciccone.mobilegoaltracker;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonConversion {

    static String convertingToJSON(ArrayList arrayList) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < arrayList.size() ; i++) {

            jsonObject.put(String.valueOf(i), arrayList.get(i) );
        }


        String workouts = jsonObject.toString();

        return workouts;

    }
    // Converting the loaded String to JSON -> returning Arraylist
    static ArrayList convertingFromJson(String fileContent) throws JSONException {

        ArrayList workouts = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(fileContent);


        for (int i = 0; i < jsonObject.length(); i++) {

            workouts.add(jsonObject.get(String.valueOf(i)));
            Log.d("readArray", workouts.get(i).toString());

        }


        return workouts;
    }

}
