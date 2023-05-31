package com.ciccone.mobilegoaltracker.utility;

import com.ciccone.mobilegoaltracker.model.GoalObject;
import com.ciccone.mobilegoaltracker.model.WorkoutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonConversion {

    //Todo write proper description and tidy up
    public static String convertingToJSON(ArrayList arrayList) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < arrayList.size() ; i++) {

            jsonObject.put(String.valueOf(i), arrayList.get(i) );
        }


        String workouts = jsonObject.toString();

        return workouts;

    }
    // Converting the ArrayList from Planning to a JSONArray -> returning a String
    public static String convertingToJsonArray(ArrayList arrayList, String object) throws JSONException {

        JSONObject root = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        if ("WorkoutData".equals(object)){

            WorkoutData workoutData;
            for (int i = 0; i < arrayList.size() ; i++) {

                workoutData = (WorkoutData) arrayList.get(i);

                JSONObject workoutDataObj = new JSONObject();

                workoutDataObj.put(String.valueOf(i), workoutData.getDate());
                workoutDataObj.put(String.valueOf(i+1), workoutData.getWorkoutName());

                jsonArray.put(workoutDataObj);

            }
        } else if ("GoalObject".equals(object) ) {
            GoalObject goalObject;

            for (int i = 0; i < arrayList.size() ; i++) {

                goalObject = (GoalObject) arrayList.get(i);

                JSONObject goalDataObj = new JSONObject();

                goalDataObj.put(String.valueOf(i), goalObject.startOfGoal);
                goalDataObj.put(String.valueOf(i+1), goalObject.endOfGoal);
                goalDataObj.put(String.valueOf(i+2), goalObject.goalAmount);


                jsonArray.put(goalDataObj);

            }
        }

        root.put("root", jsonArray);

        String workouts = root.toString();

        return workouts;

    }




    // Converting the loaded String to JSON -> returning Arraylist
    public static ArrayList convertingFromJson(String fileContent) throws JSONException {

        ArrayList workouts = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(fileContent);


        for (int i = 0; i < jsonObject.length(); i++) {

            workouts.add(jsonObject.get(String.valueOf(i)));

        }


        return workouts;
    }

    // Converting the loaded String to JSONArray for Planning -> returning Arraylist
    public static ArrayList convertingFromJsonArray(String fileContent, String object) throws JSONException {



        ArrayList workouts = new ArrayList<>();

        JSONObject root = new JSONObject(fileContent);

        JSONArray array= root.getJSONArray("root");

        if ("WorkoutData".equals(object)){

            workouts = new ArrayList<WorkoutData>();
            for (int i = 0; i < array.length(); i++) {

                JSONObject workoutDataObj = array.getJSONObject(i);
                workouts.add(new WorkoutData(workoutDataObj.getString(String.valueOf(i)), workoutDataObj.getString(String.valueOf(i+1))));

            }
        } else if ("GoalObject".equals(object)) {

            workouts = new ArrayList<GoalObject>();
            for (int i = 0; i < array.length(); i++) {

                JSONObject goalDataObj = array.getJSONObject(i);
                workouts.add(new GoalObject(goalDataObj.getString(String.valueOf(i)), goalDataObj.getString(String.valueOf(i+1)), goalDataObj.getInt(String.valueOf(i+2))));

            }
        }

        return workouts;
    }

}


