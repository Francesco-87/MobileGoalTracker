package com.ciccone.mobilegoaltracker.model;

public class GoalObject {

    public String startOfGoal;
    public String endOfGoal;
    public int goalAmount;


    public GoalObject(String startOfGoal, String endOfGoal, int goalAmount) {
        this.startOfGoal = startOfGoal;
        this.endOfGoal = endOfGoal;
        this.goalAmount = goalAmount;
    }

    public GoalObject() {

    }

    public String getStartOfGoal() {
        return startOfGoal;
    }

    public void setStartOfGoal(String startOfGoal) {
        this.startOfGoal = startOfGoal;
    }

    public String getEndOfGoal() {
        return endOfGoal;
    }

    public void setEndOfGoal(String endOfGoal) {
        this.endOfGoal = endOfGoal;
    }

    public int getGoalAmount() { return goalAmount; }

    public void setGoalAmount(int goalAmount) { this.goalAmount = goalAmount; }
}
