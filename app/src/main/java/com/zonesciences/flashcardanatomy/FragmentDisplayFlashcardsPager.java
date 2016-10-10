package com.zonesciences.flashcardanatomy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class FragmentDisplayFlashcardsPager extends Fragment {

    public static final String ARG_FLASCHARD_PAGE = "FLASHCARD PAGE";
    public static final String ARG_REGION = "REGION";
    public static final String ARG_SUBREGION ="SUBREGION";
    public static final String ARG_NUM_FLASHCARDS ="NUM FLASHCARDS";
    public static final String ARG_STRUCTURE_NAME = "ARG_STRUCTURE_NAME";
    public static final String ARG_ORIGIN = "ARG_ORIGIN";
    public static final String ARG_INSERTION = "ARG_INSERTION";
    public static final String ARG_BLOODSUPPLY = "ARG_BLOODSUPPLY";
    public static final String ARG_INNERVATION = "ARG_INNERVATION";
    public static final String ARG_ACTION = "ARG_ACTION";
    public static final String ARG_COMPARTMENT = "ARG_COMPARTMENT";
    public static final String ARG_FILENAMES = "ARG_FILENAMES";

    String mRegion;
    String mSubregion;
    String mStructureName;
    String mOrigin;
    String mInsertion;
    String mAction;
    String mInnervation;
    String mBloodSupply;
    String mFileName;
    String mCompartment;


    int mImgID;
    int mPage;
    int mTotal;



    public static FragmentDisplayFlashcardsPager newInstance(int page, Flashcard flashcardSet){

        Bundle args = new Bundle();

        List<Flashcard> flashcards = new ArrayList<>();
        flashcards = flashcardSet.getFlashcardSet();

        args.putInt(ARG_FLASCHARD_PAGE, page);
        args.putInt(ARG_NUM_FLASHCARDS, flashcardSet.getNumFlashcards());
        args.putString(ARG_REGION, flashcardSet.getRegion());
        args.putString(ARG_SUBREGION, flashcardSet.getSubregion());
        args.putString(ARG_STRUCTURE_NAME, flashcards.get(page).getStructureName());
        args.putString(ARG_ORIGIN, flashcards.get(page).getOrigin());
        args.putString(ARG_INSERTION, flashcards.get(page).getInsertion());
        args.putString(ARG_ACTION, flashcards.get(page).getAction());
        args.putString(ARG_INNERVATION, flashcards.get(page).getInnervation());
        args.putString(ARG_BLOODSUPPLY, flashcards.get(page).getBloodSupply());
        args.putString(ARG_FILENAMES, flashcards.get(page).getImgLocation());
        args.putString(ARG_COMPARTMENT, flashcards.get(page).getCompartment());

        FragmentDisplayFlashcardsPager fragment = new FragmentDisplayFlashcardsPager();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_FLASCHARD_PAGE);
        mRegion = getArguments().getString(ARG_REGION);
        mSubregion = getArguments().getString(ARG_SUBREGION);
        mTotal = getArguments().getInt(ARG_NUM_FLASHCARDS);
        mStructureName = getArguments().getString(ARG_STRUCTURE_NAME);
        mOrigin = removeHyphen(getArguments().getString(ARG_ORIGIN));
        mInsertion = removeHyphen(getArguments().getString(ARG_INSERTION));
        mAction = removeHyphen(getArguments().getString(ARG_ACTION));
        mInnervation = removeHyphen(getArguments().getString(ARG_INNERVATION));
        mBloodSupply = removeHyphen(getArguments().getString(ARG_BLOODSUPPLY));
        mFileName = getArguments().getString(ARG_FILENAMES);
        mCompartment = getArguments().getString(ARG_COMPARTMENT);


        Log.i(Utils.INFO, "Compartment for this frag: " + mCompartment);
        //convert filename to resource ID
        mImgID = getActivity().getResources().getIdentifier(mFileName, "drawable", getActivity().getPackageName());

    }

    @Override
    //Includes recyclerview to display list of flashcard sets
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Inflate the layout for this fragment
        //Each fragment contains a recycler view with a set of flashcards
        View view = inflater.inflate(R.layout.fragment_display_flashcards_pager, container, false);

        if (mPage == 0){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Utils.capitalFirst(mCompartment));
        }

        //using dependency for scaling images 'com.davemorrissey.labs:subsampling-scale-image-view:3.5.0'
        SubsamplingScaleImageView mainImage = (SubsamplingScaleImageView) view.findViewById(R.id.flashcardImg);
        mainImage.setImage(ImageSource.resource(mImgID));

        // If the resource ID is blank due to misnaming etc. then use algorithm to find the closest match and supply image.
        // This is very resource heavy and needs improving.


        /*if (mImgID == 0){
            Log.i(Utils.INFO, "File name of fucked image = " + mFileName);

            Field[] drawables = com.zonesciences.flashcardanatomy.R.drawable.class.getFields();
            List<String> drawableList = new ArrayList<String>();
            int lowestLevDis = 0;
            String closestName = "";

            for(Field f: drawables){
                try{
                    String s = f.getName();
                    if (s.contains("abc") || (s.contains("design")) || (s.contains("skip") || (s.contains("$")) || (s.contains("icon")) || (s.contains("services")) || (s.contains("google")) || (s.contains("phone")))) {
                        // do nothing, do not add to list of drawable flashcard image resource names
                    } else {
                        drawableList.add(s);
                        // System.out.println("Added drawable to list: " + s);
                    }



                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            //Use Levenshtein distance from apache extended string utils package to calculate best match to misnamed file
            for(int i = 0; i < drawableList.size(); i++){
                String str = drawableList.get(i);
                int j = StringUtils.getLevenshteinDistance(str, mFileName);
                System.out.println("Comparing " + mFileName + " to " + str + " CompareTo() returns: " + j);
                if (i == 0){
                    lowestLevDis = j;
                } else {
                    if (j < lowestLevDis){
                        lowestLevDis = j;
                        closestName = drawableList.get(i);
                    }
                }

                mImgID = getActivity().getResources().getIdentifier(closestName, "drawable", getActivity().getPackageName());
                mainImage.setImage(ImageSource.resource(mImgID));
                System.out.println("Lowest distance = " + lowestLevDis + " and name of this muscle is : " + closestName);

            }

            System.out.println("Number of images: " + drawableList.size());

        }*/

        Log.i(Utils.INFO, "mImgId = " + mImgID);
        /*ImageView mainImage = (ImageView) view.findViewById(R.id.flashcardImg);
        mainImage.setImageResource(mImgID);*/

        TextView name = (TextView) view.findViewById(R.id.flashcardStructureName);
        name.setText(mStructureName);

        /*TextView originHeading = (TextView) view.findViewById(R.id.flashcardOriginHeading);
        originHeading.setText("Origin");*/
        TextView origin = (TextView) view.findViewById(R.id.flashcardOrigin);
        origin.setText(mOrigin);

        /*TextView insertionHeading = (TextView) view.findViewById(R.id.flashcardInsertionHeading);
        insertionHeading.setText("Insertion");*/
        TextView insertion = (TextView) view.findViewById(R.id.flashcardInsertion);
        insertion.setText(mInsertion);

        /*TextView actionHeading = (TextView) view.findViewById(R.id.flashcardActionHeading);
        actionHeading.setText("Action");*/
        TextView action = (TextView) view.findViewById(R.id.flashcardAction);
        action.setText(mAction);

        /*TextView innervationHeading = (TextView) view.findViewById(R.id.flashcardInnervationHeading);
        innervationHeading.setText("Innervation");*/
        TextView innervation = (TextView) view.findViewById(R.id.flashcardInnervation);
        innervation.setText(mInnervation);

        /*TextView bloodSupplyHeading = (TextView) view.findViewById(R.id.flashcardBloodSupplyHeading);
        bloodSupplyHeading.setText("Blood Supply");*/
        TextView bloodSupply = (TextView) view.findViewById(R.id.flashcardBloodSupply);
        bloodSupply.setText(mBloodSupply);

        return view;
    }

    //method to remove hyphen, and create a single string with the split strings contained on a new line
    public String removeHyphen (String attrib) {
        String attribs[] = attrib.split("- "); //note the space after hyphen (this limits to hyphens placed only BEFORE a new line, not hyphens between words which are enclosed either side by characters)
        String s = new String();
        for (int i = 0; i < attribs.length; i++) {
            if (i == 1) {
                s = attribs[i];

            } else {

                String append = "\n" + attribs[i];
                s += append;
            }
        }
        return s;
    }

}
