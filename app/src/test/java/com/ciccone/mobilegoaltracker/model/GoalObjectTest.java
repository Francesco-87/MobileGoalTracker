package com.ciccone.mobilegoaltracker.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GoalObjectTest  {

    int goalAmount = 5;
    String startOfGoal = "25/5/2023";
    String endOfGoal = "30/5/2023";

    GoalObject goalObject = new GoalObject(startOfGoal, endOfGoal, goalAmount);
    @Test
    public void getStartOfGoal() {

      assertEquals(startOfGoal, goalObject.getStartOfGoal());
    }

    @Test
    public void setStartOfGoal() {

        goalObject.setStartOfGoal("26/5/2023");

        assertEquals("26/5/2023",goalObject.getStartOfGoal());
    }

    @Test
    public void getEndOfGoal() {

        assertEquals(endOfGoal, goalObject.getEndOfGoal());

    }

    @Test
    public void setEndOfGoal() {

        goalObject.setEndOfGoal("5/6/2023");
        assertEquals("5/6/2023", goalObject.getEndOfGoal());
    }

    @Test
    public void getGoalAmount() {

        assertEquals(goalAmount, goalObject.getGoalAmount() );
    }

    @Test
    public void setGoalAmount() {
        goalObject.setGoalAmount(10);
        assertEquals(10, goalObject.getGoalAmount());
    }
}