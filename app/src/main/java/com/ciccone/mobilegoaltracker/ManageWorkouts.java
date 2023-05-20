package com.ciccone.mobilegoaltracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

public class ManageWorkouts extends AppCompatActivity {

    //setting all the variables
    private ImageButton returnToMain;
    private FloatingActionButton addWorkout;

    private ListView itemList;
    private TextView userInput;
    private String itemTxt;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private Context context = this;
    private final String FILE_NAME = "Workouts.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_workouts);

        //initiating variables
        returnToMain = findViewById(R.id.imgBtnReturnFromWorkouts);
        addWorkout = findViewById(R.id.floatingActionButtonAddWokout);

//calling the list from storage and initiating it
        itemList = findViewById(R.id.itemList);
        try {

            if(FileManager.fileExist(context, FILE_NAME)){
                items = new ArrayList<>(
                        JsonConversion.convertingFromJson(FileManager.readFromStorage(context, FILE_NAME)));
            }else {
                items = new ArrayList<>();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        itemList.setAdapter(itemsAdapter);

        //implementing the return to mainActivity function
        returnToMain.setOnClickListener(View ->{
            Intent returnToMain = new Intent(ManageWorkouts.this, MainActivity.class);
            startActivity(returnToMain);
        });

        setupListViewListener();

        addWorkout.setOnClickListener(view ->{

            addWorkout();
        });
    }
    //Inflating the input_workout.xml, building the alertDialog and getting the input for new Workout; Calling the JSON conversion for saving to storage
    private void addWorkout() {
        LayoutInflater li= LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_name, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //set the prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        userInput = promptsView.findViewById(R.id.editTxtInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Create",
                        (dialog, id) -> {
                            // get user input and set it to result
                            // edit text
                            // result.setText(userInput.getText());
                            itemTxt = userInput.getText().toString();
                            itemsAdapter.add(itemTxt);
                            try {
                                 FileManager.saveToStorage(JsonConversion.convertingToJSON(items), context, FILE_NAME);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            //display file saved message
                            Toast.makeText(getBaseContext(), "Workout created!",
                                    Toast.LENGTH_SHORT).show();
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    // Setting a View Listener for deleting workouts, and converting to JSON for saving to storage
    private void setupListViewListener() {
        itemList.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    // Remove the item within array at position
                    items.remove(pos);
                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    try {
                        FileManager.saveToStorage(JsonConversion.convertingToJSON(items), context, FILE_NAME);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    //display file saved message
                    Toast.makeText(getBaseContext(), "Workout deleted!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                });
    }
}