package com.zonesciences.flashcardanatomy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class DisplayFlashcardsPagerActivity extends AppCompatActivity {

    String mRegion;
    String mSubregion;

    Flashcard flashcardSet;

    ViewPager flashcardsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flashcards_pager);

        Intent intent = getIntent();
        mRegion = intent.getStringExtra(FragmentMenuRecyclerViewAdapter.FLASHCARD_REGION);
        mSubregion = (intent.getStringExtra(FragmentMenuRecyclerViewAdapter.FLASHCARD_SUBREGION)).toLowerCase();

        Log.i(Utils.INFO, "Region is: " + mRegion + "Subregion is: " + mSubregion);

        FlashcardAccess fa = FlashcardAccess.getInstance(getApplicationContext());
        fa.open();
        fa.removeEmptyRows();

        //create flashcard set to be passed to fragment via adapter once in this main activity
        flashcardSet = fa.getObjFlashcardSet(mRegion, mSubregion);
        flashcardSet.setRegion(mRegion);
        flashcardSet.setSubregion(mSubregion);


        Log.i(Utils.INFO, "Num flashcards = " + Integer.toString(flashcardSet.getNumFlashcards()));


        //Get toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.flashcardInfoToolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.skip_ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        getSupportActionBar().setSubtitle(Utils.capitalFirst(mRegion + " / " + Utils.capitalFirst(mSubregion)));




        //Get the ViewPage and set its PageAdapter so that it can display items
        flashcardsViewPager = (ViewPager) findViewById(R.id.pagerFlashcards);
        FragmentPagerAdapter pagerAdapter = new FragmentDisplayFlashcardsPagerAdapter(getSupportFragmentManager(), DisplayFlashcardsPagerActivity.this, flashcardSet);
        flashcardsViewPager.setAdapter(pagerAdapter);

        //add listener in order to update page title based flashcard that is currently being viewed
        flashcardsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do fuck all
            }

            @Override
            public void onPageSelected(int position) {

                String title = Utils.capitalFirst(flashcardSet.getFlashcardSet().get(position).getCompartment());
                Log.i(Utils.INFO, "PageChangeListener called, current title: " + title);
                if (title == "No title") {
                    title = flashcardSet.getFlashcardSet().get(position).getStructureName();
                }
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do fuck all
            }
        });

    }



    class FragmentDisplayFlashcardsPagerAdapter extends FragmentPagerAdapter {

        Context context;
        int mNumFlashcards;
        Flashcard flashcardSet;

        public FragmentDisplayFlashcardsPagerAdapter (FragmentManager fm, Context context, Flashcard flashcardSet){
            //call superclass public constructor which takes FragmentManager as single argument
            super(fm);
            this.context = context;
            this.flashcardSet = flashcardSet;
            this.mNumFlashcards = flashcardSet.getNumFlashcards();
        }


        @Override
        public int getCount(){
            return mNumFlashcards;
        }

        @Override
        //Required method. Returns the fragment associated with a specified position
        public Fragment getItem(int position){
            // Use new instance method of fragment to create new fragment object which takes position as argument
            return FragmentDisplayFlashcardsPager.newInstance(position, flashcardSet);
        }


        /*@Override
        public CharSequence getPageTitle(int position){
            //Generate title based on item position
        }*/
    }
}
