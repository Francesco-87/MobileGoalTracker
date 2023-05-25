package com.ciccone.mobilegoaltracker;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class DateUtility {

    //initializing variables and instances
    private static ArrayUtility arrayUtility = new ArrayUtility();
    private static Date date = new Date();

    //Converting a calender long to a String date format
     public static String convertCalendarDate(long date){
        String dateString = new SimpleDateFormat("d/M/yyyy").format(new Date(date));

        return dateString;
    }


    //method to check if it is the default date or a new date
    public String checkDate(int dateYear, int dateMonth, int dateDay, Long today){

        String dateString;


        if(dateYear == 0){
                        dateString = new SimpleDateFormat("d/M/yyyy").format(new Date(today));
        }else{
            dateString = dateDay + "/" + dateMonth +  "/" + dateYear;

        }
        return dateString;
    }

    //finding today's Date in an Arraylist
    public static int findPositionToday(@NonNull ArrayList arrayList){

        int positionToday = 2000;
        String today = convertCalendarDate(DateUtility.date.getTime());

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (today.equals(temp.getDate())){
                positionToday = i+1;
            }
        }
        if(positionToday == 2000){

            arrayList.add(new WorkoutData(today, "Today"));
            arrayList = arrayUtility.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (today.equals(temp.getDate())){
                    positionToday = i;
                }
            }
        }

        return positionToday;
    }
    //TODO finish this
    public static int findEndDatePosition(ArrayList arrayList, String endDate){
        int positionEndDate = 2000;

        if(endDate.equals("endDate")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            endDate = convertCalendarDate(cal.getTimeInMillis());
        }



        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (endDate.equals(temp.getDate())){
                positionEndDate = i+1;
            }
        }
        if(positionEndDate == 2000){

            arrayList.add(new WorkoutData(endDate, "EndDate"));
            arrayList = arrayUtility.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    positionEndDate = i;
                }
            }
        }


         return positionEndDate;
    }

    //TODO start position for workoutGoal, modify findEndDatePosition for reuse in this here
    public static int findStartPosition(ArrayList arrayList, String startDate){
        int startPosition = 2000;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String endDate = convertCalendarDate(cal.getTimeInMillis());
        String today = convertCalendarDate(DateUtility.date.getTime());

        for (int i = 0; i < arrayList.size(); i++) {
            WorkoutData temp = (WorkoutData) arrayList.get(i);

            if (endDate.equals(temp.getDate())){
                startPosition = i+1;
            }
        }
        if(startPosition == 2000){

            arrayList.add(new WorkoutData(endDate, "EndDate"));
            arrayList = arrayUtility.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    startPosition = i;
                }
            }
        }


        return startPosition;
    }

}
