package com.ciccone.mobilegoaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Planning extends AppCompatActivity {

    //setting all the variables
    private ImageButton returnToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        //assigning the layout to variables
        returnToMain = findViewById(R.id.imgBtnReturnFromPlanning);

        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(Planning.this, MainActivity.class);
            startActivity(returnToMain);
        });
    }
}