package com.ciccone.mobilegoaltracker.utility;

import static com.ciccone.mobilegoaltracker.utility.DateUtility.convertCalendarDate;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import androidx.annotation.NonNull;

import com.ciccone.mobilegoaltracker.model.WorkoutData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ArrayUtility {



    //method to sort the WorkData ArrayList so that the Dates are in descending order
    public static ArrayList sortArrayList(ArrayList arrayList){

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
    //A filter for the different Performance and the Last three workouts
    public static ArrayList filterArrayList(ArrayList aList){

        ArrayList temp = new ArrayList<>();
        int positionToday = findPositionToday(aList);

        int aListMax = positionToday + 3;
        int aListTwo = positionToday + 2;
        int aListOne = positionToday + 1;


        if (aList.size() >= aListMax){
            for (int i = positionToday; i < positionToday + 3; i++) {

                WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
            }
        } else if (aList.size() >= aListTwo) {
            for (int i = positionToday; i < positionToday + 2; i++) {

                WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
            }
        } else if (aList.size() >= aListOne) {
            for (int i = positionToday; i < positionToday + 1; i++) {

                WorkoutData lastThreeWorkouts = (WorkoutData) aList.get(i);
                temp.add(lastThreeWorkouts.getDate() + "\n" + lastThreeWorkouts.getWorkoutName() );
            }
        }


        return temp;
    }

    //finding today's Date in an Arraylist
    public static int findPositionToday(@NonNull ArrayList arrayList){

        int positionToday = 2000;
        String today = convertCalendarDate(new Date().getTime());

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (today.equals(temp.getDate())){
                positionToday = i;
            }
        }
        if(positionToday == 2000){

            arrayList.add(new WorkoutData(today, "Today"));
            arrayList = sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (today.equals(temp.getDate())){
                    positionToday = i+1;
                }
            }
        }

        return positionToday;
    }
    //finding the end date position of the last 7 days in an arraylist
    public static int findEndDatePosition(ArrayList arrayList){
        int positionEndDate = 2000;

        //It creates a calender instance and checks 7 days before today (for performance week)

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String endDate = convertCalendarDate(cal.getTimeInMillis());

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (endDate.equals(temp.getDate())){
                positionEndDate = i;
            }
        }

        // If date does not exist add the date temporarily
        if(positionEndDate == 2000){

            arrayList.add(new WorkoutData(endDate, "EndDate"));
            arrayList = sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    positionEndDate = i;
                }
            }
        }


        return positionEndDate;
    }

    //finding the starting position in an Array for the Goal calc
    public static int findStartPositionGoal(ArrayList arrayList, String endDate) {

        //it takes the passed in startDate string (for performance current)
        int startPosition = 2000;


        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (endDate.equals(temp.getDate())){
                startPosition = i;
            }
        }
        if(startPosition == 2000){

            arrayList.add(new WorkoutData(endDate, "EndDateGoal"));
            arrayList = sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    startPosition = i+1;
                }
            }
        }



        return startPosition;
    }

    //finding the end position in an Array for the Goal calc
    public static int findEndPositionGoal(ArrayList arrayList, String endDate){
        int positionEndDate = 2000;

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (endDate.equals(temp.getDate())){
                positionEndDate = i;
            }
        }

        // If date does not exist add the date temporarily
        if(positionEndDate == 2000){

            arrayList.add(new WorkoutData(endDate, "EndDate"));
            arrayList = sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    positionEndDate = i;
                }
            }
        }

        return positionEndDate;
    }


}
