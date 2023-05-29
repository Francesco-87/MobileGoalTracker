package com.ciccone.mobilegoaltracker;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
                positionToday = i;
            }
        }
        if(positionToday == 2000){

            arrayList.add(new WorkoutData(today, "Today"));
            arrayList = arrayUtility.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (today.equals(temp.getDate())){
                    positionToday = i+1;
                }
            }
        }

        return positionToday;
    }

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
            arrayList = arrayUtility.sortArrayList(arrayList);

            for (int i = 0; i < arrayList.size(); i++) {
                WorkoutData temp = (WorkoutData) arrayList.get(i);

                if (endDate.equals(temp.getDate())){
                    startPosition = i+1;
                }
            }
        }



        return startPosition;
    }

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


//taking a String date and returning it as a long value
    public static long dateToLong(String date){

        SimpleDateFormat f = new SimpleDateFormat("d/M/yyyy");

         long temp = 0;

        try {
            Date first = f.parse(date);
            temp = first.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

         return temp;
    }

    public static int calculateDaysApart(long start, long end){
         int days;
         long temp = end - start;
        days = (int) (temp / (1000*60*60*24));


         return days;
    }


}
