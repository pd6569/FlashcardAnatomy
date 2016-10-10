package com.zonesciences.flashcardanatomy;

import android.util.Log;

/**
 * Created by Peter on 19/09/2016.
 */
public class Utils {

    public static String[] REGIONS = new String[]{"Lower Limb", "Upper Limb", "Head", "Neck", "Abdomen", "Thorax", "Back", "Pelvis"};

    public static final String INFO = "Info";
    public static final String ERROR = "Error";
    public static final String WARNING = "Warning";
    public static final String DEBUG = "Debug";


    public static String capitalFirst (String string){
        String output;
        try {
             output = Character.toUpperCase(string.charAt(0)) + string.substring(1);
        } catch (Exception e) {
            Log.i(INFO, "CANNOT PERFORM CAPITALFIRST");
            output = "No title";
        }
        return output;
    }

    public static String getFilename(String structureName){
        String fileName = structureName.toLowerCase().trim().replace(' ','_');
        return fileName;
    }
}

