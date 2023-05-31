package com.ciccone.mobilegoaltracker.utility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.icu.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


class DateUtilityTest {

//TODO TESTS in this test class do not work need working on.
    long today = System.currentTimeMillis();
    Date dateToday = new Date(today);

    @Mock
    SimpleDateFormat simpleDateFormat;
    @Mock
    public Date mockDate;
    @Mock
    private AutoCloseable closeable;



    @BeforeEach
    public void initMocks() throws ParseException {

        closeable = MockitoAnnotations.openMocks(this);
// Mock the behavior of the format method

       mockDate =mock(Date.class);

        when(mockDate.getTime()).thenReturn(today);
        when(mockDate.toString()).thenReturn("Wed May 31 16:49:59 CEST 2023");
        System.out.print("mockDATE" + mockDate);
        System.out.print("dateToday" + dateToday);
        System.out.print("TODAY" + today);
        simpleDateFormat = mock(SimpleDateFormat.class);

        when(simpleDateFormat.parse(any())).thenReturn(mockDate);








    }
    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }


    @Test
    void convertCalendarDate() {

        when(simpleDateFormat.format(mockDate.getTime())).thenReturn("2017-02-06");


        assertEquals("30/5/2023", DateUtility.convertCalendarDate(today));

    }

    @Test
    void checkDate() {

    }

    @Test
    void dateToLong() throws ParseException {


        when(mockDate.getTime()).thenReturn(30L);

        assertEquals(30L, DateUtility.dateToLong("26/5/2023"));

    }

    @Test
    void calculateDaysApart() {

    }
}