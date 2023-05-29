package com.ciccone.mobilegoaltracker.utility;

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
    static Date date = new Date();

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
