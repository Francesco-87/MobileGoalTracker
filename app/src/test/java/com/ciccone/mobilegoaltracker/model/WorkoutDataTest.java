package com.ciccone.mobilegoaltracker.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkoutDataTest {

    String date = "30/5/2023";
    String workoutName = "Biking";

    WorkoutData workoutData = new WorkoutData(date, workoutName);

    @Test
    void getDate() {

        assertEquals("30/5/2023", workoutData.getDate());

    }

    @Test
    void setDate() {

        workoutData.setDate("5/6/2024");
        assertEquals("5/6/2024", workoutData.getDate());
    }

    @Test
    void getWorkoutName() {

        assertEquals("Biking", workoutData.getWorkoutName());
    }

    @Test
    void setWorkoutName() {
        workoutData.setWorkoutName("Running");
        assertEquals("Running", workoutData.getWorkoutName());

    }
}