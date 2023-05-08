package com.ciccone.mobilegoaltracker;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileManager  {


    static final int READ_BLOCK_SIZE = 100;

    // Saving the file in the internal storage with FileOutputStream
    static void saveToStorage(String workouts, Context context) {
        try{
            FileOutputStream fOut = context.openFileOutput("Workouts.txt", Context.MODE_PRIVATE);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
            outputStreamWriter.write(workouts);
            outputStreamWriter.close();


        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //reading text from file
    static String readFromStorage(Context context){

        //Empty string variable
        String fileContent = "";

        //trying to read a specific "Workout.txt" file via InputStream and adding it to fileContent with a while loop
        try{
            FileInputStream fIn = context.openFileInput("Workouts.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(fIn);


            char[] inputBuffer = new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead = inputStreamReader.read(inputBuffer)) != -1) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                fileContent += readstring;
            }
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }
    static boolean fileExist( Context context){
        File file = context.getFileStreamPath("Workouts.txt");
        return file.exists();
    }

}
