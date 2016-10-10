package com.zonesciences.flashcardanatomy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zonesciences.flashcardanatomy.Database.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 16/09/2016.
 */
public class FlashcardAccess {
    private static FlashcardAccess sFlashcardAccess;

    private SQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;



    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static FlashcardAccess getInstance(Context context) {
        if (sFlashcardAccess == null) {
            sFlashcardAccess = new FlashcardAccess(context);
        }
        return sFlashcardAccess;
    }

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private FlashcardAccess(Context context) {
        mContext = context.getApplicationContext();
        mOpenHelper = new DatabaseOpenHelper(mContext);
        open();
    }


    /**
     * Open the database connection.
     */
    public void open() {
        Log.i(Utils.INFO, "Database Connection Open");
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        Log.i(Utils.INFO, "Database Connection Closed");
        if (mDatabase != null) {
            mDatabase.close();
        }
    }


    public List<String> getStructureNames() {
        List<String> list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT Name FROM 'Lower Limb' ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public List<Flashcard> getFlashcardSet(String region, String subregion, String structure){
        List<Flashcard> flashcardSet = new ArrayList<>();
        String query;
        Cursor cursor;
        Log.i(Utils.INFO, "getflashcardset called");
        if (structure == null && subregion == null) {
            query = "SELECT * FROM '" + region + "' ";
            Log.i(Utils.INFO, "query executed: " + query);
            cursor = mDatabase.rawQuery(query, null);
        } else if(subregion == null) {
            query = "SELECT * FROM '" + region + "' WHERE  Structure = ?";
            Log.i(Utils.INFO, "query executed: " + query);
            cursor = mDatabase.rawQuery(query, new String[] {structure});
        } else if(structure == null) {
            query = "SELECT * FROM '" + region + "' WHERE  Subregion = ?";
            Log.i(Utils.INFO, "query executed: " + query);
            cursor = mDatabase.rawQuery(query, new String[] {subregion});
        } else {
            query = "SELECT * FROM '" + region + "'";
            Log.i(Utils.INFO, "query executed: " + query);
            cursor = mDatabase.rawQuery(query, null);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Flashcard flashcard = new Flashcard();
            flashcard.setStructureName(cursor.getString(cursor.getColumnIndex("Name")));
            flashcard.setAction(cursor.getString(cursor.getColumnIndex("Action")));
            flashcard.setOrigin(cursor.getString(cursor.getColumnIndex("Origin")));
            flashcard.setInsertion(cursor.getString(cursor.getColumnIndex("Insertion")));
            flashcard.setInnervation(cursor.getString(cursor.getColumnIndex("Nerve")));
            flashcard.setBloodSupply(cursor.getString(cursor.getColumnIndex("BloodSupply")));
            flashcard.setCompartment(cursor.getString(cursor.getColumnIndex("Compartment")));
            flashcard.setImgLocation(Utils.getFilename(flashcard.getStructureName()));
            flashcardSet.add(flashcard);
            cursor.moveToNext();
        }
        cursor.close();
        return flashcardSet;
    }

    //Create flashcard set object using above list method
    public Flashcard getObjFlashcardSet(String region, String subregion){
        List<Flashcard> list = getFlashcardSet(region, subregion, null);
        Flashcard flashcard = new Flashcard(list);
        Log.i(Utils.INFO, "getObjFlashcardSet executed with parameters:" + region + "," + subregion + ".");
        return flashcard;
    }

    public String[] getFlashcardNames(String region){
        List<String> list = new ArrayList<>();
        String s[];
        Cursor cursor = mDatabase.rawQuery("SELECT Name FROM '" + region + "' ", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        s = list.toArray(new String[list.size()]);
        return s;
    }

    //Get Flashcard set title and image location for main menu. Works by taking subregion from query to be used as the title
    //for the flashcard set, taking the first result in the cursor name to be used as image to be displayed.
    //Iterates down the cursor skipping Subregions that are the same, creating small flashcard object containing only
    //Subregion and image location.

    //Sets flashcards with following properties only: subregion, filename, image for the set, num flashcards for set
    public List<Flashcard> getFlashcardSetsForRegion (String region, String structure){
        List<Flashcard> list = new ArrayList<>();

        String query = "SELECT Name, Subregion FROM '" + region + "' WHERE Structure = ? ORDER BY Subregion ASC";
        String currentSubregion = new String();
        int numFlashcards;
        Cursor cursor;
        cursor = mDatabase.rawQuery(query, new String[] {structure});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {

            if (cursor.isFirst()){
                currentSubregion = cursor.getString(cursor.getColumnIndex("Subregion"));
            }

            if (currentSubregion.equals(cursor.getString(cursor.getColumnIndex("Subregion"))) && !cursor.isFirst()) {
                currentSubregion = cursor.getString(cursor.getColumnIndex("Subregion"));
                cursor.moveToNext();
            } else {
                currentSubregion = cursor.getString(cursor.getColumnIndex("Subregion"));
                numFlashcards = getNumFlashcards(region, currentSubregion);
                Flashcard flashcard = new Flashcard();
                String filename = cursor.getString(0);
                flashcard.setSubregion(Utils.capitalFirst(currentSubregion));
                flashcard.setImgLocation(filename);
                flashcard.setNumFlashcards(numFlashcards);
                Log.i(Utils.INFO, "There are " + numFlashcards + " flashcards in the " + currentSubregion + " set.");
                list.add(flashcard);
                cursor.moveToNext();
            }
        }
        cursor.close();

        int size = list.size();
        for (int i = 0; i < list.size(); i++){
            String s = list.get(i).getSubregion();
            Log.i(Utils.INFO, "Region from object: " + s);
        }
        Log.i(Utils.INFO, "Number of flashcard objects in the list:  " + list.size());
        Log.i(Utils.INFO, "getFlashcardSetsForRegion called. Number of objects in list: " + size );
        return list;
    }



    public int getNumFlashcards(String region, String subregion){
        String query = "SELECT * FROM '" + region + "' WHERE Subregion = ?";
        Cursor cursor;
        cursor = mDatabase.rawQuery(query, new String[]{subregion});
        return cursor.getCount();
    }

    public void removeEmptyRows(){
        String deleteQuery = "DELETE FROM 'Lower Limb' WHERE Name IS NULL OR TRIM(Name) ='' ";
        mDatabase.execSQL(deleteQuery);
    }

}
