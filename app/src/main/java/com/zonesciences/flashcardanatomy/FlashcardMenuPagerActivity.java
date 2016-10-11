package com.zonesciences.flashcardanatomy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


//Opening screen of flashcard app, displaying menu of flashcard sets organised by region

public class FlashcardMenuPagerActivity extends AppCompatActivity {

    String[] mFlashcardSet;

    Flashcard mLowerLimbSet;
    Flashcard mUpperLimbSet;
    Flashcard mHeadSet;
    Flashcard mNeckSet;
    Flashcard mThoraxSet;
    Flashcard mAbdomenSet;
    Flashcard mPelvisSet;
    Flashcard mBackSet;

    Flashcard [] mFlashcardRegionSets;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_menu_pager);

        Log.i(Utils.INFO, "onCreate called");
        //Set up database connection
        /*FlashcardAccess flashcardAccess = FlashcardAccess.getInstance(getApplicationContext());
        flashcardAccess.removeEmptyRows();*/

        /*mLowerLimbSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Lower Limb", "muscle"));


        Log.i(Utils.INFO, "Lower limb set generated, num flashcards: " + mLowerLimbSet.getNumFlashcards());

        mUpperLimbSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Upper Limb", "muscle"));
        mHeadSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Head", "muscle"));
        mNeckSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Neck", "muscle"));
        mThoraxSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Thorax", "muscle"));
        mAbdomenSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Abdomen", "muscle"));
        mPelvisSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Pelvis", "muscle"));
        mBackSet = new Flashcard(flashcardAccess.getFlashcardSetsForRegion("Back", "muscle"));*/

        mLowerLimbSet = Flashcard.getMenuFlashcardSets("Lower Limb");
        mUpperLimbSet = Flashcard.getMenuFlashcardSets("Upper Limb");
        mHeadSet = Flashcard.getMenuFlashcardSets("Head");
        mNeckSet = Flashcard.getMenuFlashcardSets("Neck");
        mThoraxSet = Flashcard.getMenuFlashcardSets("Thorax");
        mAbdomenSet = Flashcard.getMenuFlashcardSets("Abdomen");
        mPelvisSet = Flashcard.getMenuFlashcardSets("Pelvis");
        mBackSet = Flashcard.getMenuFlashcardSets("Back");

        mFlashcardRegionSets = new Flashcard[]{mLowerLimbSet, mUpperLimbSet, mBackSet, mThoraxSet, mHeadSet, mNeckSet, mAbdomenSet, mPelvisSet};

        // Create arrays of flashcard sets for each Region

        ViewPager viewPager = (ViewPager) findViewById(R.id.pagerMainMenu);

        FragmentPagerAdapter pagerAdapter = new FragmentMenuPagerAdapter(getSupportFragmentManager(), FlashcardMenuPagerActivity.this, mFlashcardRegionSets);
        viewPager.setAdapter(pagerAdapter);


        //Give the TabLayout for region selection the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*Resources res = getesources();*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.menuPagerToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Flashcard Anatomy");




        //TabLayout for refining by subnav
        /*TabLayout tabLayoutStructures = (TabLayout) findViewById(R.id.sliding_tabs_subnav);
        tabLayoutStructures.addTab(tabLayoutStructures.newTab().setText("Sets"));
        tabLayoutStructures.addTab(tabLayoutStructures.newTab().setText("All"));*/
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i(Utils.INFO, "onResume called");


    }

    //FragmentPagerAdapater represents each page as a fragment that is persistently kept in the fragment
    //manager.
    //Must implement getItem(int) and getCount()

    class FragmentMenuPagerAdapter extends FragmentPagerAdapter {


        String tabTitles[] = Utils.REGIONS;
        Flashcard [] regionSets;
        Context context;

        public FragmentMenuPagerAdapter(FragmentManager fm, Context context, Flashcard [] flashcardRegionSets){
            //call superclass public constructor which takes FragmentManager as single argument
            super(fm);
            this.context = context;
            this.regionSets = flashcardRegionSets;

        }


        @Override
        public int getCount(){
            return regionSets.length;
        }

        @Override
        //Required method. Returns the fragment associated with a specified position
        public Fragment getItem(int position){
            // Use new instance method of fragment to create new fragment object which takes position as argument
            Flashcard flashcard = regionSets[position];
            return FragmentFlashcardMenuPager.newInstance(position, flashcard);
        }

        @Override
        public CharSequence getPageTitle(int position){
            //Generate title based on item position
            return tabTitles[position];
        }

        /*
        public View getTabView(int position){
            View tab = LayoutInflater.from(FlashcardMenuPagerActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }
        */
    }

}
