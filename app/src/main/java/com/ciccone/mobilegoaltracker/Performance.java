package com.ciccone.mobilegoaltracker;

import static com.ciccone.mobilegoaltracker.utility.DateUtility.calculateDaysApart;
import static com.ciccone.mobilegoaltracker.utility.DateUtility.dateToLong;
import static com.ciccone.mobilegoaltracker.utility.ArrayUtility.findEndDatePosition;
import static com.ciccone.mobilegoaltracker.utility.ArrayUtility.findEndPositionGoal;
import static com.ciccone.mobilegoaltracker.utility.ArrayUtility.findPositionToday;
import static com.ciccone.mobilegoaltracker.utility.ArrayUtility.findStartPositionGoal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ciccone.mobilegoaltracker.model.GoalObject;
import com.ciccone.mobilegoaltracker.model.WorkoutData;
import com.ciccone.mobilegoaltracker.utility.ArrayUtility;
import com.ciccone.mobilegoaltracker.utility.FileManager;
import com.ciccone.mobilegoaltracker.utility.JsonConversion;

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
    private ArrayList workoutDataList;
    private ArrayAdapter lastThreeWorkoutsAdapter;
    private ArrayList workoutGoal;
    private GoalObject goalObject;

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

                //setting the progressbar for current to the max days in the goal and setting the goal

                    current.setMax(calculateDaysApart(dateToLong(goalObject.getStartOfGoal()), dateToLong(goalObject.getEndOfGoal()))+1);
                    current.setProgress(countWorkouts(workoutDataList, CURRENT_COUNT));



            } else {
                workoutGoal = new ArrayList<GoalObject>();

            }
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //calling the counts and setting the progress in the progress bar
        lastMonth.setProgress(countWorkouts(workoutDataList, MONTH_COUNT));
        lastMonth.setMax(30);
        lastWeek.setProgress(countWorkouts(workoutDataList, LAST_WEEK_COUNT));

        //calling the filterArray method to display the last 3 Workouts and setting an adapter
        lastThreeWorkoutsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ArrayUtility.filterArrayList(workoutDataList));
        listLastWorkouts.setAdapter(lastThreeWorkoutsAdapter);

        //implementing the return to mainActivity function via intent
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Performance.this, MainActivity.class);
            startActivity(returnToMain);
        });




    }




    //a switch that takes in an Arraylist and a string (for differentiating the case) and returns a method
    private int countWorkouts(ArrayList arrayList, String countType){


        int count = 0;
        int positionToday;
        int positionEnd;
        int positionStartCurrent;
        int positionEndCurrent;

        //sorting the arrayList for the first count and getting the original size
        arrayList = arrayUtility.sortArrayList(arrayList);
        int arrayListSizeOriginal = arrayList.size();
        int arrayListSize;

        switch (countType){
            //this case is for the month progress
            case MONTH_COUNT:

                    //assigning position by calling the findPositionToday in ArrayUtilities and sorting it (might have new entries)
                positionToday = findPositionToday(arrayList);
                arrayList = arrayUtility.sortArrayList(arrayList);
                int arrayListMaxMonth = positionToday + 30;
                arrayListSize = arrayList.size();
                //if the positionToday is far in the back a null pointer might occur, that's why the maxMonth as it has to get always 30 days
                if(arrayList.size() >= arrayListMaxMonth){
                    for (int i = positionToday; i < arrayListMaxMonth; i++) {
                        count ++;
                    }
                }else{
                    for (int i = positionToday; i < arrayList.size(); i++) {
                        count ++;

                    }
                }
                Log.d("DATETODAY", String.valueOf(count));
                //TODO might need fixing because only one date might have been added, but two are being removed
                //if the original arraylist is changed, entries where added and those need to be removed again
                if(arrayListSize != arrayListSizeOriginal){
                    arrayList.remove(findEndDatePosition(arrayList));
                    arrayList.remove(findPositionToday(arrayList));
                }

                break;
            case LAST_WEEK_COUNT:

                //same as MONTH_COUNT just with two positions
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


                //same as MONTH_COUNT just with two positions
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