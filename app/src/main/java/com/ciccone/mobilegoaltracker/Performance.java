package com.ciccone.mobilegoaltracker;

import static com.ciccone.mobilegoaltracker.DateUtility.calculateDaysApart;
import static com.ciccone.mobilegoaltracker.DateUtility.dateToLong;
import static com.ciccone.mobilegoaltracker.DateUtility.findEndDatePosition;
import static com.ciccone.mobilegoaltracker.DateUtility.findEndPositionGoal;
import static com.ciccone.mobilegoaltracker.DateUtility.findPositionToday;
import static com.ciccone.mobilegoaltracker.DateUtility.findStartPositionGoal;

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
    private String FILE_NAME_GOAL = "Goal.txt";
    private Context context = this;
    private ImageButton returnToMain;
    private ProgressBar current;
    private ProgressBar lastWeek;
    private ProgressBar lastMonth;
    private ListView listLastWorkouts;
    private WorkoutData workoutData;
    private ArrayList workoutDataList;
    private ArrayAdapter lastThreeWorkoutsAdapter;
    private ArrayList workoutGoal;
    private GoalObject goalObject;

    private Date date;
    private ArrayUtility arrayUtility;

    private final String MONTH_COUNT = "Month";
    private final String CURRENT_COUNT = "Current";
    private final String LAST_WEEK_COUNT = "LastWeek";



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

        //loading the Goal from storage and initiating it
        try {
            if (FileManager.fileExist(context, FILE_NAME_GOAL)) {
                workoutGoal = new ArrayList<GoalObject>(
                        JsonConversion.convertingFromJsonArray(FileManager.readFromStorage(context, FILE_NAME_GOAL),"GoalObject"));

                goalObject = (GoalObject) workoutGoal.get(0);


                current.setMax(calculateDaysApart(dateToLong(goalObject.getStartOfGoal()), dateToLong(goalObject.getEndOfGoal()))+1);
                int d = calculateDaysApart(dateToLong(goalObject.getStartOfGoal()), dateToLong(goalObject.getEndOfGoal()));
                Log.d("DATECHECK", String.valueOf(d));
                current.setProgress(countWorkouts(workoutDataList, CURRENT_COUNT));
                int c = countWorkouts(workoutDataList, CURRENT_COUNT);
                Log.d("DATECHECK", String.valueOf(c));

            } else {
                workoutGoal = new ArrayList<GoalObject>();

            }
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

       //calling the filterArray method to display the last 3 Workouts and setting an adapter
        lastThreeWorkoutsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, filterArrayList(workoutDataList));
        listLastWorkouts.setAdapter(lastThreeWorkoutsAdapter);

        //calling the counts and setting the progress in the progress bar
        lastMonth.setProgress(countWorkouts(workoutDataList, MONTH_COUNT));
        lastWeek.setProgress(countWorkouts(workoutDataList, LAST_WEEK_COUNT));



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

        int count = 0;
        int positionToday;
        int positionEnd;
        int positionStartCurrent;
        int positionEndCurrent;


        arrayList = arrayUtility.sortArrayList(arrayList);
        int arrayListSizeOriginal = arrayList.size();
        int arrayListSize;

        switch (countType){
            case MONTH_COUNT:

                positionToday = findPositionToday(arrayList);
                arrayList = arrayUtility.sortArrayList(arrayList);
                int arrayListMaxMonth = positionToday + 30;
                arrayListSize = arrayList.size();
                if(arrayList.size() >= arrayListMaxMonth){
                    for (int i = positionToday; i < arrayListMaxMonth; i++) {
                        count ++;
                    }
                }else{
                    for (int i = positionToday; i < arrayList.size(); i++) {
                        count ++;

                    }
                }

                if(arrayListSize != arrayListSizeOriginal){
                    arrayList.remove(findEndDatePosition(arrayList));
                    arrayList.remove(findPositionToday(arrayList));
                }

                break;
            case LAST_WEEK_COUNT:

                positionEnd =  findEndDatePosition(arrayList);
                positionToday = findPositionToday(arrayList);
                arrayList = arrayUtility.sortArrayList(arrayList);
                arrayListSize = arrayList.size();

                    for (int i = positionToday; i < positionEnd; i++) {
                        count ++;

                    }
                    if(arrayListSize != arrayListSizeOriginal){
                        arrayList.remove(findEndDatePosition(arrayList));
                        arrayList.remove(findPositionToday(arrayList));
                    }

                break;
            case CURRENT_COUNT:

                positionStartCurrent = findStartPositionGoal(arrayList, goalObject.getEndOfGoal());
                positionEndCurrent =  findEndPositionGoal(arrayList, goalObject.getStartOfGoal());
                arrayList = arrayUtility.sortArrayList(arrayList);
                arrayListSize = arrayList.size();
                for (int i = positionStartCurrent; i <= positionEndCurrent; i++) {
                    count ++;

                }
                if(arrayListSize != arrayListSizeOriginal){
                    arrayList.remove(findStartPositionGoal(arrayList, goalObject.getEndOfGoal()));
                    arrayList.remove(findEndPositionGoal(arrayList, goalObject.getStartOfGoal()));
                }

                break;

            default:
                // code block
        }

        return count;
    }

}