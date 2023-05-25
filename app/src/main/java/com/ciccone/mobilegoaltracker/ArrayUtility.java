package com.ciccone.mobilegoaltracker;

import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ArrayUtility {



    //method to sort the WorkData ArrayList so that the Dates are in descending order
    public ArrayList sortArrayList(ArrayList arrayList){

        //creating a temporary ArrayList, a Long variable, a SimpleDateFormat object and a WorkoutData Object
        ArrayList tempArrayList = new ArrayList<Long>();
        ArrayList tempWorkoutList = new ArrayList<WorkoutData>();

        Long time;

        SimpleDateFormat f = new SimpleDateFormat("d/M/yyyy");

        WorkoutData workoutData1;
//looping through the original ArrayList to get the date and forming it to Long in a tempArrayList
        for (int i = 0; i < arrayList.size(); i++) {
            workoutData1 = (WorkoutData) arrayList.get(i);

            try {
                Date first = f.parse(workoutData1.getDate());

                time = first.getTime();
                tempArrayList.add(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //Sorting the tempArrayList ascending
        tempArrayList.sort(Comparator.reverseOrder());

        //Looping through the tempArraylist to format it back to a date and compare it to the original ArrayList for sorting
        for (int i = 0; i < tempArrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {

                workoutData1 = (WorkoutData) arrayList.get(j);
                time = (Long) tempArrayList.get(i);

                String d1 = f.format(new Date(time));

                if(d1.equals(workoutData1.getDate())){
                    tempWorkoutList.add(arrayList.get(j));
                }
            }


        }

        return tempWorkoutList;
    }


}
