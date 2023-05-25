package com.ciccone.mobilegoaltracker;

import static com.ciccone.mobilegoaltracker.DateUtility.convertCalendarDate;
import static com.ciccone.mobilegoaltracker.DateUtility.findEndDatePosition;
import static com.ciccone.mobilegoaltracker.DateUtility.findPositionToday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

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

    private Date date;
    private ArrayUtility arrayUtility;



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
        lastThreeWorkoutsList = new ArrayList<String>();
        arrayUtility = new ArrayUtility();



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
        countWorkouts(workoutDataList, "Week");




        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Performance.this, MainActivity.class);
            startActivity(returnToMain);
        });
    }


    //A filter for the different Performance and the Last three workouts
    private ArrayList filterArrayList(ArrayList aList){

        ArrayList temp = new ArrayList<>();
        int positionToday = findPositionToday(aList);

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


        arrayList = arrayUtility.sortArrayList(arrayList);
        int count = 0;
        int positionToday =findPositionToday(arrayList);
        int positionEnd =  findEndDatePosition(arrayList, "endDate");
        int positionStart;
        int positionEndCurrent = findEndDatePosition(arrayList, );

        int arrayListMaxMonth = positionToday + 30;


        switch (countType){
            case "Month":
                if(arrayList.size() >= arrayListMaxMonth){
                    for (int i = positionToday; i < arrayListMaxMonth; i++) {
                        count ++;
                    }
                }else{
                    for (int i = positionToday; i < arrayList.size(); i++) {
                        count ++;
                    }

                }

                break;
            case "Week":
                    for (int i = positionToday; i <= positionEnd; i++) {
                        count ++;

                    }

                    Log.d("COUNTW", String.valueOf(count));
                break;
            case "Current":

                break;

            default:
                // code block
        }
                    for (int i = positionToday; i <= positionEnd; i++) {
                        count ++;

                    }

        return count;
    }

}