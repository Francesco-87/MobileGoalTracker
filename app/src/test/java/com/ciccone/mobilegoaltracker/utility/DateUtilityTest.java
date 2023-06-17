package com.ciccone.mobilegoaltracker.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DateUtilityNewTest {

    @InjectMocks
    DateUtility dateUtility;

    SimpleDateFormat simpleDateFormat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    @Test
    void checkDateTest() {
        String date = dateUtility.checkDate(2023, 06, 14, 0l);
        assertThat(date).isNotNull();
        assertThat(date).isEqualTo("14/6/2023");
    }

    @Test
    void checkDateWithoutYearTest() {
        String date = dateUtility.checkDate(0, 06, 14, new Date().getTime());
        assertThat(date).isNotNull();
        assertThat(date).isEqualTo(simpleDateFormat.format(new Date()));
    }

    @Test
    void dateToLongTest() {
        try (MockedStatic<DateUtility> utilities = Mockito.mockStatic(DateUtility.class)) {
            utilities.when(() -> DateUtility.dateToLong("14/6/2023"))
                    .thenReturn(123l);
            assertThat(DateUtility.dateToLong("14/6/2023")).isEqualTo(123l);
        }
    }

    @Test
    void convertCalendarDateTest() {
        try (MockedStatic<DateUtility> utilities = Mockito.mockStatic(DateUtility.class)) {
            utilities.when(() -> DateUtility.convertCalendarDate(123l))
                    .thenReturn("14/6/2023");
            assertThat(DateUtility.convertCalendarDate(123l)).isEqualTo("14/6/2023");
        }
    }

    @Test
    void convertCalendarDateWithSdfTest() {
        String convertedDate = DateUtility.convertCalendarDate(new Date().getTime());
        assertThat(convertedDate).isNotNull();
        assertThat(convertedDate).isEqualTo(simpleDateFormat.format(new Date()));
    }

    @Test
    void dateToLongWithSDFTest() {
        Long convertedDate = DateUtility.dateToLong(simpleDateFormat.format(new Date()));
        assertThat(convertedDate).isNotNull();
    }
}
