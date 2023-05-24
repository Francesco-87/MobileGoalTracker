package com.ciccone.mobilegoaltracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Planning extends AppCompatActivity  {

    //declaring all the variables
    private String FILE_NAME = "Planning.txt";
    private String FILE_NAME_GOAL = "Goal.txt";
    final private String choiceAddWorkout = "Add a new Workout";
    final private String choiceDeleteWorkout = "Delete planned Workout";
    final private String choiceAddGoal = "Add a new Goal";
    final private String choiceDeleteGoal = "Delete Goal";
    private ImageButton returnToMain;
    private ImageButton btnWorkoutPlanning;
    private CalendarView calendarView;
    private TextView textViewWorkout;
    private TextView textViewGoal;
    private Spinner spinnerPlanningChoice;

    private ArrayList choiceList;
    private ArrayList workoutDataList;
    private WorkoutData workoutData;

    private String dateString;
    private String itemTxt;
    private Long today;
    private ArrayList workoutGoal;
    private GoalObject goalObject;



    private String spinnerChoice;
    private TextView userInput;
    private Context context = this;

    private  CalendarConstraints constraintsBuilder;
    private MaterialDatePicker<androidx.core.util.Pair<Long, Long>> materialDatePicker;


    int dateYear;
    int dateDay;
    int dateMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);


        //assigning the layout to variables
        returnToMain = findViewById(R.id.imgBtnReturnFromPlanning);
        btnWorkoutPlanning = findViewById(R.id.fABworkoutPlanning);
        calendarView = findViewById(R.id.calendarView);
        textViewWorkout = findViewById(R.id.textViewCalenderActivity);
        textViewGoal = findViewById(R.id.textViewGoal);
        spinnerPlanningChoice = findViewById(R.id.spinnerPlanningChoice);
        goalObject = new GoalObject();


        //Creating the List and adapter for the spinner
        choiceList = new ArrayList<>();
        choiceList.add(choiceAddWorkout);
        choiceList.add(choiceDeleteWorkout);
        choiceList.add(choiceAddGoal);
        choiceList.add(choiceDeleteGoal);

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choiceList);
        spinnerPlanningChoice.setAdapter(spinnerAdapter);

        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Planning.this, MainActivity.class);
            startActivity(returnToMain);
        });

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
                //setting the textView for the goal
                String text = goalObject.getStartOfGoal() + "  -  " + goalObject.getEndOfGoal() + "\n" + goalObject.getGoalAmount() + "  -  " + "Workouts";
                textViewGoal.setText(text);
                textViewGoal.setVisibility(View.VISIBLE);

            } else {
                workoutGoal = new ArrayList<GoalObject>();

            }
        }catch (JSONException e) {
                throw new RuntimeException(e);
            }

        //Creating constrains for the date-range picker and building the MaterialDatePicker for

        constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .build();


        materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setCalendarConstraints(constraintsBuilder).build();




        //setting up a on view listener for calender to fetch dates for planned workouts
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            dateYear = year;
            dateMonth = month +1;
            dateDay = dayOfMonth;

            today = calendarView.getDate();

            dateString  = new DateConversion().checkDate(dateYear,dateMonth,dateDay,today);
            updateTextViewCalenderActivity();

        });
        // Creating and saving workouts
        btnWorkoutPlanning.setOnClickListener(v ->{


//TODO implement Toast methods
            switch(spinnerChoice) {
                case choiceAddWorkout:

                    addWorkout();
                    break;
                case choiceDeleteWorkout:
                    
                    deleteWorkout();
                    break;
                case choiceAddGoal:
                    callNameInputGoal();

                case choiceDeleteGoal:

                    deleteGoal();
                default:
                    // code block
            }
        });
        //Setting an onItemSelectListener for the spinner and the choice
        spinnerPlanningChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // in on selected listener we are displaying a toast message
                spinnerChoice = choiceList.get(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void addWorkout() {
        LayoutInflater li= LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_name, null);

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

                            workoutDataList = sortArrayList(workoutDataList);
                            try {

                                FileManager.saveToStorage(JsonConversion.convertingToJsonArray(workoutDataList, "WorkoutData"), context, FILE_NAME);
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

        textViewWorkout.setText("No Plans yet");

    for(int  i=0;i<workoutDataList.size();i++)
        {
            workoutData = (WorkoutData) workoutDataList.get(i);
            if (dateString.equals(workoutData.getDate())){
                textViewWorkout.setText(workoutData.getWorkoutName());
            }
        }
        textViewWorkout.setVisibility(View.VISIBLE);


    }


    //method to retrieve the date range and save the Goal
    private void getGoalDate(){

    //Calling the datepicker for the Goal
        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");


        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive; button that is ok button update the; selected date

                    String startDate = new DateConversion().convertCalendarDate(materialDatePicker.getSelection().first);
                    String endDate = new DateConversion().convertCalendarDate(materialDatePicker.getSelection().second);

                   goalObject.setStartOfGoal(startDate);
                   goalObject.setEndOfGoal(endDate);

                   workoutGoal.add(goalObject);
                // saving the data to a file; after JSON conversion
                    try {
                        FileManager.saveToStorage(JsonConversion.convertingToJsonArray(workoutGoal, "GoalObject"), context, FILE_NAME_GOAL);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                });


    }

    private void callNameInputGoal() {
        LayoutInflater li= LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_goal, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //set the prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        Spinner spinnerWorkout = promptsView.findViewById(R.id.spinnerWorkout);
        ArrayList arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i+1);
        }

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arrayList);
        spinnerWorkout.setAdapter(spinnerAdapter);

        spinnerWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // in on selected listener we are displaying a toast message

                goalObject.setGoalAmount(Integer.parseInt(arrayList.get(position).toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("next",
                        (dialog, id) -> {
                            // get user input and set it to result
                            // edit text
                            // result.setText(userInput.getText());
                            getGoalDate();

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

    //Method to delete goal, calls the delete function from FileManager and sets the textViewGoal to INVISIBLE
    private void deleteGoal(){

        FileManager.fileDelete(context, FILE_NAME_GOAL);
        textViewGoal.setVisibility(View.INVISIBLE);

    }

    private void deleteWorkout(){

        for(int  i=0;i<workoutDataList.size();i++)
        {
            workoutData = (WorkoutData) workoutDataList.get(i);
            if (dateString.equals(workoutData.getDate())){
                workoutDataList.remove(i);
            }
        }
        workoutDataList = sortArrayList(workoutDataList);
        try {

            FileManager.saveToStorage(JsonConversion.convertingToJsonArray(sortArrayList(workoutDataList), "WorkoutData"), context, FILE_NAME);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

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