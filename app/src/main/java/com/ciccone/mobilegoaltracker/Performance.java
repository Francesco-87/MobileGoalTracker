package com.ciccone.mobilegoaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

public class Performance extends AppCompatActivity {

    //setting all the variables
    private String FILE_NAME = "Planning.txt";
    private Context context = this;
    private ImageButton returnToMain;
    private ProgressBar current;
    private ProgressBar lastWeek;
    private ProgressBar lastMonth;
    private ListView listLastWorkouts;
    private WorkoutData workoutData;
    private ArrayList workoutDataList;
    private ArrayList lastThreeWorkoutsList;
    private ArrayAdapter lastThreeWorkoutsAdapter;
    private DateConversion dateConversion;
    private Date date;
    private Planning p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        //assigning the layout to variables
        returnToMain = findViewById(R.id.imgBtnReturnFromPerformance);
        current = findViewById(R.id.progressBarCurrent);
        lastWeek = findViewById(R.id.progressBarLastWeek);
        lastMonth = findViewById(R.id.progressBarLastMonth);
        listLastWorkouts = findViewById(R.id.listLastWorkouts);

        //initiating variables
        date = new Date();
        dateConversion = new DateConversion();
        lastThreeWorkoutsList = new ArrayList<String>();
        p = new Planning();

        //loading the workoutDataList from storage and initiating it
        try {

            if(FileManager.fileExist(context, FILE_NAME)){
                workoutDataList = new ArrayList<WorkoutData>(
                        JsonConversion.convertingFromJsonArray(FileManager.readFromStorage(context, FILE_NAME),"WorkoutData"));
            }else {
                workoutDataList = new ArrayList<WorkoutData>();

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

       //calling the filterArray method to display the last 3 Workouts and setting an adapter
        lastThreeWorkoutsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, filterArrayList(workoutDataList));
        listLastWorkouts.setAdapter(lastThreeWorkoutsAdapter);

        //TODO the assessment of the workouts
        countWorkouts(workoutDataList, "Month");



        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Performance.this, MainActivity.class);
            startActivity(returnToMain);
        });
    }
    //finding todays Date in an Arraylist
    private int findPositionToday(@NonNull ArrayList arrayList, String d){

        int positionToday = 2000;

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (d.equals(temp.getDate())){
                positionToday = i+1;
            }
        }
        if(positionToday == 2000){


            arrayList.add(new WorkoutData(dateConversion.convertCalendarDate(date.getTime()), "Today"));
            arrayList = p.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (d.equals(temp.getDate())){
                    positionToday = i;
                }
            }
        }
        
        return positionToday;
    }

    //A filter for the differend Performance and the Last three workouts
    private ArrayList filterArrayList(ArrayList aList){

        ArrayList temp = new ArrayList<>();
        int positionToday =findPositionToday(aList, dateConversion.convertCalendarDate(date.getTime()));

        int aListMax = positionToday + 3;
        int aListTwo = positionToday + 2;
        int aListOne = positionToday + 1;


            if (aList.size() >= aListMax){
                for (int i = positionToday; i < positionToday + 3; i++) {

                    WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                    temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
                }
            } else if (aList.size() >= aListTwo) {
                for (int i = positionToday; i < positionToday + 2; i++) {

                    WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                    temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
                }
            } else if (aList.size() >= aListOne) {
                for (int i = positionToday; i < positionToday + 1; i++) {

                    WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                    temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
                }
            }


        return temp;
    }

    private int countWorkouts(ArrayList arrayList, String countType){



        int count = 0;
        int positionToday =findPositionToday(arrayList, dateConversion.convertCalendarDate(date.getTime()));
        int aListMax = positionToday + 30;
//TODO check the array count also in the filterArrayList and getPosition today
        switch (countType){
            case "Month":
                if(arrayList.size() >= aListMax){
                    for (int i = positionToday; i < aListMax; i++) {
                        count ++;
                    }
                }else{
                    for (int i = positionToday; i < arrayList.size(); i++) {
                        count ++;
                    }

                }
                Log.d("COUNTwork", String.valueOf(count));
                break;
            case "Week":

                break;
            case "Current":

                break;

            default:
                // code block
        }


        return count;
    }

}