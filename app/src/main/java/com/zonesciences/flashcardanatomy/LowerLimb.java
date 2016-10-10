package com.zonesciences.flashcardanatomy;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.List;

public class LowerLimb extends AppCompatActivity {

    public static final String LOG_MESSAGE = "Error: ";
    private static final String IMAGE_PATH_ROOT = "R.drawable.";

    //Flashcard image and navigation panel
    ImageView mFlashcardHolder;
    TextView mFlashcardSubtitle;
    Button mButtonNext;
    Button mButtonPrevious;
    Button mButtonDetails;



    //Flashcard details
    TextView mStructureName;
    TextView mOrigin;
    TextView mInsertion;
    TextView mAction;
    TextView mFileLocation;

    //Set of flashcards
    List<Flashcard> mFlashcards;

    public int mCurrentIndex = 0;
    public boolean mNextPressed = false;
    String mCurrentImage;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_limb);

        //Set up database connection
        FlashcardAccess flashcardAccess = FlashcardAccess.getInstance(getApplicationContext());
        flashcardAccess.removeEmptyRows();

        Resources res = getResources();


        mFlashcardHolder = (ImageView) findViewById(R.id.flashcardHolder);
        mFlashcardSubtitle = (TextView) findViewById(R.id.flashcardSubtitle);
        mButtonNext = (Button) findViewById(R.id.buttonNext);
        mButtonPrevious = (Button) findViewById(R.id.buttonPrevious);
        mButtonDetails = (Button) findViewById(R.id.buttonDetails);

        //Muscle details
        mStructureName = (TextView) findViewById(R.id.structureName);
        mOrigin = (TextView) findViewById(R.id.origin);
        mInsertion = (TextView) findViewById(R.id.insertion);
        mAction = (TextView) findViewById(R.id.muscleAction);
        mFileLocation = (TextView) findViewById(R.id.fileLocation);

        mFlashcards = flashcardAccess.getFlashcardSet("Lower Limb", null, null);

        updateDetails();

    }


    public void nextFlashcard(View view) {
        mNextPressed = true;
        changeIndex();
        updateDetails();
    }

    public void prevFlashcard(View view) {
        mNextPressed = false;
        changeIndex();
        updateDetails();
    }

    public void details(View view) {
        Toast.makeText(this, "Details", Toast.LENGTH_SHORT).show();
    }

    private void changeIndex() {
        int numFlashcards = mFlashcards.size();
        int pos = (numFlashcards - 1);

        if (mNextPressed == true) {
            if (mCurrentIndex == pos) {
                mCurrentIndex = 0;
            } else {
                mCurrentIndex++;
            }
        } else if (mNextPressed == false) {
            if (mCurrentIndex == 0) {
                mCurrentIndex = pos;
            } else {
                mCurrentIndex--;
            }
        }

        Log.i(LOG_MESSAGE, "Current Index: " + mCurrentIndex);
    }

    private void updateDetails(){
        mFlashcardSubtitle.setText((mFlashcards.get(mCurrentIndex)).getStructureName());
        mStructureName.setText((mFlashcards.get(mCurrentIndex)).getStructureName());
        mOrigin.setText((mFlashcards.get(mCurrentIndex)).getOrigin());
        mInsertion.setText((mFlashcards.get(mCurrentIndex)).getInsertion());
        mAction.setText((mFlashcards.get(mCurrentIndex)).getAction());

        String fileName = (mFlashcards.get(mCurrentIndex)).getImgLocation();
        mFileLocation.setText(fileName);

        int imgID = getResources().getIdentifier(fileName, "drawable", getPackageName());
        mFlashcardHolder.setImageResource(imgID);
        Log.i(LOG_MESSAGE, "Image ID: " + imgID + " For file path: " + fileName);

    }
}
