package com.ciccone.mobilegoaltracker;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.Date;

public class DateConversion {
    //Converting a calender long to a String date format
     public String convertCalendarDate(long date){
        String dateString = new SimpleDateFormat("dd/M/yyyy").format(new Date(date));

        return dateString;
    }


    //method to check if it is the default date or a new date
    public String checkDate(int dateYear, int dateMonth, int dateDay, Long today){

        String dateString;


        if(dateYear == 0){
                        dateString = new SimpleDateFormat("dd/M/yyyy").format(new Date(today));
        }else{
            dateString = dateDay + "/" + dateMonth +  "/" + dateYear;
            Log.d("DATESTRING", dateString);
        }
        return dateString;
    }


}
