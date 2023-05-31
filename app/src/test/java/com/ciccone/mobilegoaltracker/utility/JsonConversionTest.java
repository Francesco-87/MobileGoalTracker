package com.ciccone.mobilegoaltracker.utility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

class JsonConversionTest {

    @Mock
    JSONObject jsonObject;

    @BeforeEach
    public void initMocks() throws JSONException {

        jsonObject = new JSONObject();
        //when(jsonObject.put(any(), any())).thenReturn();
    }

    @Test
    void convertingToJSON() throws JSONException {

        ArrayList arrayList = new ArrayList<>();
        arrayList.add("object1");

        assertEquals("object1", JsonConversion.convertingToJSON(arrayList));
    }

    @Test
    void convertingToJsonArray() {
    }

    @Test
    void convertingFromJson() {
    }

    @Test
    void convertingFromJsonArray() {
    }
}