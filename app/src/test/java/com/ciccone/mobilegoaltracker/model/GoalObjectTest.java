package com.ciccone.mobilegoaltracker.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GoalObjectTest  {

    @Test
    public void getStartOfGoal() {
      GoalObject goalObject = new GoalObject("hallo", "bye", 5);
      assertEquals("hallo", goalObject.getStartOfGoal());
    }

    @Test
    public void setStartOfGoal() {
    }

    @Test
    public void getEndOfGoal() {
    }

    @Test
    public void setEndOfGoal() {
    }

    @Test
    public void getGoalAmount() {
    }

    @Test
    public void setGoalAmount() {
    }
}