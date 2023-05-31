package com.ciccone.mobilegoaltracker.utility;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class DateUtility {



    private static SimpleDateFormat tempDate = new SimpleDateFormat("d/M/yyyy");


    //Converting a calender long to a String date format
     public static String convertCalendarDate(long date){


        String dateString = tempDate.format(new Date(date));


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



         long temp = 0;

        try {
            Date first = tempDate.parse(date);
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
