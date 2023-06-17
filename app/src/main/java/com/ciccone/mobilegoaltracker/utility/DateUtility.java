package com.ciccone.mobilegoaltracker.utility;


import java.text.SimpleDateFormat;
//import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

//a class for date utilities
public class DateUtility {

    private static SimpleDateFormat tempDate = new SimpleDateFormat("d/M/yyyy");


    //Converting a calender long to a String date format
     public static String convertCalendarDate(long date){


        String dateString = tempDate.format(new Date(date));


        return dateString;
    }


    //method to check if it is the default date or a new date, returning a string
    public String checkDate(int dateYear, int dateMonth, int dateDay, Long today){

        String dateString;

        //if the passed in year, there was no date selected on the calenderView and today´s date is used
        if(dateYear == 0){
                        dateString = tempDate.format(new Date(today));
        }else{
            dateString = dateDay + "/" + dateMonth +  "/" + dateYear;

        }
        return dateString;
    }

//taking a String date and returning it as a long value
    public static long dateToLong(String date){

         long temp = 0;

        try {
            Date first = tempDate.parse(date);
            temp = first.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

         return temp;
    }

    // a method to calculate days apart between dates, returning an integer
    public static int calculateDaysApart(long start, long end){
         int days;
         long temp = end - start;

         //converting from long to integer
        days = (int) (temp / (1000*60*60*24));


         return days;
    }


}
