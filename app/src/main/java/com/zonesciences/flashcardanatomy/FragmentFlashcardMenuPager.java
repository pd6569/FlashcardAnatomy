package com.zonesciences.flashcardanatomy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 17/09/2016.
 */

//Class which defines layout and function of each menu page fragment to include the RecyclerView displaying flashcard sets
public class FragmentFlashcardMenuPager extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_REGION = "ARG_REGION";
    public static final String ARG_REGION_SET = "ARG_REGION_SET";
    public static final String ARG_REGION_SET_SUBREGIONS = "ARG_REGION_SET_SUBREGIONS";
    public static final String ARG_REGION_SET_IMAGES = "ARG_REGION_SET_IMAGES";


    private int mPage;
    private String mRegion;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Flashcard object set containing flashcard object list
    Flashcard mMenuFlashcardSet = new Flashcard();

    //List of limited flashcard objects containing only structurename and region properties
    private List<Flashcard> mMenuCardAttribs;

    String[] mCardTitle;
    String[] mCardSubtitle;
    Integer[] mCardImages;

    Flashcard mRegionSet;


    public static FragmentFlashcardMenuPager newInstance(int page, Flashcard regionSet){
        String[] regions = Utils.REGIONS;
        Bundle args = new Bundle();

        args.putInt(ARG_PAGE, page);
        args.putString(ARG_REGION, regions[page]);
        Log.i(Utils.INFO, "Trying to set subregion array. Element[0]: " + regionSet.getArraySubregion()[0].toString());
        args.putStringArray(ARG_REGION_SET_SUBREGIONS, regionSet.getArraySubregion());
        args.putStringArray(ARG_REGION_SET_IMAGES, regionSet.getArrayStrImgLocation());

        FragmentFlashcardMenuPager fragment = new FragmentFlashcardMenuPager();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    //onCreate in a Fragment is called after onAttachFragment, but before onCreateView
    //Therefore it is used to attach the variables prior to View inflation. In this case, the
    //extra ARG_PAGE is assigned during onCreate
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);
        Log.i(Utils.INFO, "Creating page: " + mPage);

        mRegion = getArguments().getString(ARG_REGION);
        Log.i(Utils.INFO, "Creating page for region: " + mRegion);

        /*mMenuFlashcardSet = getArguments().getParcelable(ARG_REGION_SET);*/
        mCardTitle = getArguments().getStringArray(ARG_REGION_SET_SUBREGIONS);
        Log.i(Utils.INFO, "Checking card title array. Element [0]: " + mCardTitle[0].toString());

        mCardImages = getImagesResId(getArguments().getStringArray(ARG_REGION_SET_IMAGES));
        Log.i(Utils.INFO, "Checking card image ID, Element[0] " + mCardImages[0].toString());

        // FlashcardAccess fa = FlashcardAccess.getInstance(getActivity());
        /*mMenuFlashcardSet = fa.getFlashcardNames(mRegion);*/

        //use Flashcard constructor to set flashcard object set as property

        // removed this temporarily to try object method - see above
        //mMenuFlashcardSet = new Flashcard(fa.getFlashcardSetsForRegion(mRegion, "muscle"));


        //get List of flashcard objects from FlashcardSet object which are then passed to createCardAttributes
        //to generate arrays

        /*mMenuCardAttribs = mMenuFlashcardSet.getFlashcardSet();

        createCardAttributes();*/



        //Set the properties of the menu flashcard set object and then pass this to the recyclerview adapter


        mMenuFlashcardSet.setTitle(mCardTitle);
        mMenuFlashcardSet.setSubtitle(mCardSubtitle);
        mMenuFlashcardSet.setArrayIntImgLocation(mCardImages);
        mMenuFlashcardSet.setRegion(mRegion);

    }

    @Override
    //Includes recyclerview to display list of flashcard sets
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Inflate the layout for this fragment
        //Each fragment contains a recycler view with a set of flashcards
        View rootView = inflater.inflate(R.layout.fragment_menu_pager, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.menuRecyclerView);
        rv.setHasFixedSize(true);

        //Adapter is in separate class, not inner class. Takes string as argument to display information in recycler view.

        FragmentMenuRecyclerViewAdapter adapter = new FragmentMenuRecyclerViewAdapter(mMenuFlashcardSet);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(Utils.INFO, "onPause called");
        /*FlashcardAccess fa = FlashcardAccess.getInstance(getActivity());
        fa.close();*/
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(Utils.INFO, "onResume called");
        /*FlashcardAccess fa = FlashcardAccess.getInstance(getActivity());
        fa.open();*/
    }



    //Convert flashcard set attributes into arrays that can be passed to the recyclerview
    public void createCardAttributes(){
        //get the relevant properties from each flashcard object and add to an array to pass to
        // recyclerview by first creating lists.
        List<String> titleList = new ArrayList<>();
        List<String> imageList = new ArrayList<>();
        List<String> subtitleList = new ArrayList<>();

        for (int i = 0; i < mMenuCardAttribs.size(); i++){
            String subregion = mMenuCardAttribs.get(i).getSubregion();
            String location = mMenuCardAttribs.get(i).getImgLocation();
            String subtitle = mMenuCardAttribs.get(i).getNumFlashcards() + " muscles";
            titleList.add(subregion);
            imageList.add(location);
            subtitleList.add(subtitle);
        }

        //convert lists to array to pass to recyclerview
        int size = titleList.size();
        mCardTitle = titleList.toArray(new String[size]);
        mCardImages = getImagesResId(imageList.toArray(new String[size]));
        mCardSubtitle = subtitleList.toArray(new String[size]);
    }

    //Converts array of filenames to integer resource ID.
    public Integer[] getImagesResId(String[] structureNames){
        Integer[] imgIDarray;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < structureNames.length; i++){
            String structureName = structureNames[i];
            String fileName = Utils.getFilename(structureName);
            Log.i(Utils.INFO, "Attempt to convert to integer: " + fileName);
            int imgID = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
            list.add(imgID);
            Log.i(Utils.INFO, "Added imgID to list: " + imgID);
        }
        imgIDarray = list.toArray(new Integer[list.size()]);
        return imgIDarray;
    }
}
