package com.ciccone.mobilegoaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //setting all the variables
    private Button planning;
    private Button performance;
    private Button manageWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding all the buttons by id
        planning = findViewById(R.id.btnPlanning);
        performance = findViewById(R.id.btnPerformance);
        manageWorkouts = findViewById(R.id.btnWorkouts);

        //setting listeners for buttons, Individual intends from each button to the corresponding activity
        planning.setOnClickListener(view ->{
            Intent toPlanning = new Intent(MainActivity.this, Planning.class);
            startActivity(toPlanning);
        });

        performance.setOnClickListener(view ->{
            Intent toPerformance = new Intent(MainActivity.this, Performance.class);
            startActivity(toPerformance);
        });

        manageWorkouts.setOnClickListener(view ->{
            Intent toManageWorkouts = new Intent(MainActivity.this, ManageWorkouts.class);
            startActivity(toManageWorkouts);
        });
    }
}