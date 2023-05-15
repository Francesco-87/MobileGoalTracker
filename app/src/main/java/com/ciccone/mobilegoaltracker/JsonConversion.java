package com.ciccone.mobilegoaltracker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonConversion {

    //Todo write proper description and tidy up
    static String convertingToJSON(ArrayList arrayList) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < arrayList.size() ; i++) {

            jsonObject.put(String.valueOf(i), arrayList.get(i) );
        }


        String workouts = jsonObject.toString();

        return workouts;

    }
    // Converting the ArrayList from Planning to a JSONArray -> returning a String
    static String convertingToJsonArray(ArrayList arrayList) throws JSONException {

        JSONObject root = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        WorkoutData workoutData;

        for (int i = 0; i < arrayList.size() ; i++) {

            workoutData = (WorkoutData) arrayList.get(i);

            JSONObject workoutDataObj = new JSONObject();

            workoutDataObj.put(String.valueOf(i), workoutData.getDate());
            workoutDataObj.put(String.valueOf(i+1), workoutData.getWorkoutName());

            jsonArray.put(workoutDataObj);

        }

        root.put("root", jsonArray);

        String workouts = root.toString();

        return workouts;

    }




    // Converting the loaded String to JSON -> returning Arraylist
    static ArrayList convertingFromJson(String fileContent) throws JSONException {

        ArrayList workouts = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(fileContent);


        for (int i = 0; i < jsonObject.length(); i++) {

            workouts.add(jsonObject.get(String.valueOf(i)));

        }


        return workouts;
    }

    // Converting the loaded String to JSONArray for Planning -> returning Arraylist
    static ArrayList convertingFromJsonArray(String fileContent) throws JSONException {


        ArrayList workouts = new ArrayList<WorkoutData>();

        JSONObject root = new JSONObject(fileContent);

        JSONArray array= root.getJSONArray("root");

        for (int i = 0; i < array.length(); i++) {

            JSONObject workoutDataObj = array.getJSONObject(i);
            workouts.add(new WorkoutData(workoutDataObj.getString(String.valueOf(i)), workoutDataObj.getString(String.valueOf(i+1))));


        }
        return workouts;
    }

}


