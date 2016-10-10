package com.zonesciences.flashcardanatomy;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;

/**
 * Created by Peter on 19/09/2016.
 */
public class FragmentMenuRecyclerViewAdapter extends RecyclerView.Adapter<FragmentMenuRecyclerViewAdapter.ViewHolder>  {

    public static final String FLASHCARD_POSITION = "Flashcard Position";
    public static final String FLASHCARD_REGION = "REGION";
    public static final String FLASHCARD_SUBREGION = "SUBREGION";

    public Flashcard mFlashcardSets;


    //Provide reference to the view for each data item
    //Complex data items may need more than one view per item, and
    //you can provide access to all the view for a data item in a view holder

    //Viewholder describes item view and metadata about its place within the RecyclerView
    //ViewHolders belong to the adapter.
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView mCardView;
        public TextView mTitle;
        public TextView mSubtitle;
        public TextView mRegion;
        public ImageView mCardImage;



        public ViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.menuCardView);
            mTitle = (TextView) v.findViewById(R.id.menuTitleText);
            mSubtitle = (TextView) v.findViewById(R.id.menuSubtitle);
            mCardImage = (ImageView) v.findViewById(R.id.menuImage);
            mRegion = (TextView) v.findViewById(R.id.cardRegion);

            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String subregion = mTitle.getText().toString();
            String region = mRegion.getText().toString();

            //Click listener used in ViewHolder as items know their position here
            Intent intent = new Intent(view.getContext(), DisplayFlashcardsPagerActivity.class);
            intent.putExtra(FLASHCARD_REGION, region);
            intent.putExtra(FLASHCARD_SUBREGION, subregion);
            view.getContext().startActivity(intent);

            Log.i(Utils.INFO, "Region: " + region + " Subregion: " + subregion);
        }
    }

    // Provide a suitable constructor
    public FragmentMenuRecyclerViewAdapter(Flashcard flashcardSetMenu){
        Log.i(Utils.INFO, "RecyclerViewAdapter constructor called");
        this.mFlashcardSets = flashcardSetMenu;
        Log.i(Utils.INFO, "mflashcard sets test. Element[0] of subregions: "  + mFlashcardSets.getTitle()[0].toString());
    }

    //create new views (invoked by layout manager)
    @Override
    public FragmentMenuRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.i(Utils.INFO, "onCreateViewHolder called");
        //create a new view for the list item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_item, parent, false);

        //set the view's size, margins, paddings and layout params
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Log.i(Utils.INFO, "onBindViewHolder called");
        holder.mTitle.setText(mFlashcardSets.getTitle()[position]);
        //holder.mSubtitle.setText(mFlashcardSets.getSubtitle()[position]);
        Log.i(Utils.INFO, "Attempt to set image from file ID: " + mFlashcardSets.getArrayIntImgLocation()[position]);
        try {
            holder.mCardImage.setImageResource(mFlashcardSets.getArrayIntImgLocation()[position]);
        } catch (Exception e){
            Log.i(Utils.INFO, "Error setting image resource: " + e);
        }
        holder.mRegion.setText(mFlashcardSets.getRegion());

        /*holder.mCardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){



                Log.i(Utils.INFO, "Adapter position: " + mCurrentPosition);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mFlashcardSets.getTitle().length;
    }


  /*  @Override
    public int getItemCount(){
        return ;
    }*/


}
