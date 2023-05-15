package com.ciccone.mobilegoaltracker;

public class WorkoutData {

    public String date;
    public String workoutName;


    public WorkoutData() {
    }



    public WorkoutData(String d, String wN){

        date = d;
        workoutName = wN;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}