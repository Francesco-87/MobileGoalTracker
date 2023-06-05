package com.ciccone.mobilegoaltracker.utility;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// a class for managing files
public class FileManager  {


    //the block size is set to 1000, as it is a small app
    static final int READ_BLOCK_SIZE = 1000;

    // Saving the file in the internal storage with FileOutputStream, using the context
    public static void saveToStorage(String s, Context context, String fileName) {
        try{
            FileOutputStream fOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
            outputStreamWriter.write(s);
            outputStreamWriter.close();

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    //reading text from file, using the context
    public static String readFromStorage(Context context, String fileName){

        //Empty string variable
        String fileContent = "";
        //trying to read a specific file (fileName) via InputStream and adding it to fileContent with a while loop
        try{
            FileInputStream fIn = context.openFileInput(fileName);
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


    //a method to check the existence of a file in the internal storage (context)
    public static boolean fileExist(Context context, String filename){
        File file = context.getFileStreamPath(filename);
        return file.exists();
    }

    //a method to delete the existence of a file in the internal storage (context)
    public static boolean fileDelete(Context context, String filename){
        File file = context.getFileStreamPath(filename);
        return file.delete();
    }

}
