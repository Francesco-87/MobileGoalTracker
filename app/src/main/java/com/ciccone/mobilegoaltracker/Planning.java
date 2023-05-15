package com.ciccone.mobilegoaltracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class Planning extends AppCompatActivity {

    //setting all the variables
    private ImageButton returnToMain;
    private ImageButton addWorkoutPlanning;
    private CalendarView calendarView;
    private TextView textViewWorkout;
    private TextView textViewGoal;

    private ArrayList workoutDataList;
    private WorkoutData workoutData;

    private String dateString;
    private String itemTxt;
    private String FILENAME = "Planning.txt";
    private TextView userInput;
    private Context context = this;
    int dateYear;
    int dateDay;
    int dateMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

//Todo implement the json filesaving and assign all views, Initiate Arraylist and workoutData class
        //assigning the layout to variables
        returnToMain = findViewById(R.id.imgBtnReturnFromPlanning);
        addWorkoutPlanning = findViewById(R.id.fABaddWorkoutPlanning);
        calendarView = findViewById(R.id.calendarView);
        textViewWorkout = findViewById(R.id.textViewCalenderActivity);
        textViewGoal = findViewById(R.id.textViewGoal);



        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Planning.this, MainActivity.class);
            startActivity(returnToMain);
        });

        //loading the workoutDataList from storage and initiating it
        try {

            if(FileManager.fileExist(context, FILENAME)){
                workoutDataList = new ArrayList<WorkoutData>(
                        JsonConversion.convertingFromJsonArray(FileManager.readFromPlanningStorage(context)));
            }else {
                workoutDataList = new ArrayList<WorkoutData>();


            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //setting up a on view listener for calender to fetch dates for planned workouts

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            dateYear = year;
            dateMonth = month +1;
            dateDay = dayOfMonth;

            dateString  = checkDate(dateYear);
            updateTextViewCalenderActivity();

        });
        // Creating and saving workouts
        addWorkoutPlanning.setOnClickListener(v ->{

            dateString  = checkDate(dateYear);
            addWorkout();

        });
    }
    //method to check if it is the default date or a new date
    private String checkDate(int dateYear){

         String dateString;
        if(dateYear == 0){
            calendarView.getDate();
            dateString = new SimpleDateFormat("dd/M/yyyy").format(new Date(calendarView.getDate()));
        }else{
            dateString = dateDay + "/" + dateMonth +  "/" + dateYear;
        }
        return dateString;
    }
    private void addWorkout() {
        LayoutInflater li= LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_workout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //set the prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        userInput = promptsView.findViewById(R.id.editTxtInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Create",
                        (dialog, id) -> {
                            // get user input and set it to result
                            // edit text
                            // result.setText(userInput.getText());
                            itemTxt = userInput.getText().toString();
                            workoutDataList.add(new WorkoutData(dateString, itemTxt));

                            try {
                                //Todo change methods, adjust to workout data
                                FileManager.savePlanningToStorage(JsonConversion.convertingToJsonArray(workoutDataList), context);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            //display file saved message
                            Toast.makeText(getBaseContext(), "Workout created!",
                                    Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //method to find the date in the arraylist for display in the planningTextView
    private void updateTextViewCalenderActivity(){

        for(int  i=0;i<workoutDataList.size();i++)
        {
            workoutData = (WorkoutData) workoutDataList.get(i);
            if (workoutData.getDate().equals(dateString)){
                textViewWorkout.setText(workoutData.getWorkoutName());

            }
            else{
                textViewWorkout.setText("");
            }
        }
        textViewWorkout.setVisibility(View.VISIBLE);
    }
}