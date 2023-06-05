package com.ciccone.mobilegoaltracker.model;

//this class contains the WorkoutData model and it´s methods
public class WorkoutData {

    //declaring the variables
    public String date;
    public String workoutName;

    //the constructors of the class
    public WorkoutData() {
    }

    public WorkoutData(String d, String wN){

        date = d;
        workoutName = wN;
    }

    //returning the date as a string
    public String getDate() {
        return date;
    }

    //setting the date
    public void setDate(String date) {
        this.date = date;
    }

    //returning the workouName as a string
    public String getWorkoutName() {
        return workoutName;
    }

    //setting the wourkoutName
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}