package com.ciccone.mobilegoaltracker.model;

//this class contains the GoalObject model and it´s methods
public class GoalObject {

    //declaring the GoalObject Variables
    public String startOfGoal;
    public String endOfGoal;
    public int goalAmount;


    //the constructors for the GoalObject
    public GoalObject(String startOfGoal, String endOfGoal, int goalAmount) {
        this.startOfGoal = startOfGoal;
        this.endOfGoal = endOfGoal;
        this.goalAmount = goalAmount;
    }

    public GoalObject() {

    }

    //returning the starOfGoal string
    public String getStartOfGoal() {
        return startOfGoal;
    }

    //setting the startOfGoal
    public void setStartOfGoal(String startOfGoal) {
        this.startOfGoal = startOfGoal;
    }

    //returning the endOfGoal
    public String getEndOfGoal() {
        return endOfGoal;
    }

    //setting the endOfGoal
    public void setEndOfGoal(String endOfGoal) {
        this.endOfGoal = endOfGoal;
    }

    //returning the goalAmount
    public int getGoalAmount() { return goalAmount; }
    //setting the goalAmount
    public void setGoalAmount(int goalAmount) { this.goalAmount = goalAmount; }
}
