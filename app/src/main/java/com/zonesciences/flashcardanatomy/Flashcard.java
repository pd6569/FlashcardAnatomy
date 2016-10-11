package com.zonesciences.flashcardanatomy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 16/09/2016.
 */
public class Flashcard implements Parcelable {

    //Variables for single flashcard objects
    String mStructureName;
    String mOrigin;
    String mInsertion;
    String mAction;
    String mBloodSupply;
    String mInnervation;
    String mImgLocation;
    String mSubregion;
    String mCompartment;
    String mRegion;

    //Variables for flashcard set objects
    List<Flashcard> mFlashcardSet;
    int mNumFlashcards;

    //Arrays of individual properties for individual flashcards in the flashcard set
    String[] mTitle;
    String [] mSubtitle;
    String[] mArrayStructureName;
    String [] mArrayOrigin;
    String [] mArrayInsertion;
    String [] mArrayAction;
    String [] mArrayBloodSupply;
    String [] mArrayInnervation;
    String [] mArraySubregion;
    String [] mArrayStrImgLocation; //this will often need to be converted to int (see getImageResId method in fragmenupager class)
    Integer [] mArrayIntImgLocation;


    //Constructor, getter and setters for single flashcard objects

    //Constructor
    public Flashcard(){

    }

    public static Flashcard getMenuFlashcardSets (String region){
        Flashcard flashcard = new Flashcard();

        switch(region){
            case "Lower Limb":
                Flashcard lowerLimb = new Flashcard();
                lowerLimb.setArraySubregion(new String[]{"Gluteal", "Thigh", "Leg", "Foot"});
                lowerLimb.setArrayStrImgLocation(new String[]{"gluteus_maximus_thumb", "rectus_femoris_thumb", "gastrocnemius_thumb", "flexor_digitorum_brevis_thumb"});
                Log.i(Utils.INFO, "lower limb flashcard set created. Subregion array element 1: " + lowerLimb.getArraySubregion()[0].toString() + " element 2 " + lowerLimb.getArraySubregion()[1].toString());
                flashcard = lowerLimb;
                break;
            case "Upper Limb":
                Flashcard upperLimb = new Flashcard();
                upperLimb.setArraySubregion(new String[]{"Shoulder", "Rotator Cuff", "Arm", "Forearm", "Hand"});
                upperLimb.setArrayStrImgLocation(new String[]{"deltoid_thumb", "infraspinatus_thumb", "biceps_brachii_thumb", "extensor_digitorum_thumb", ""});
                flashcard = upperLimb;
                break;
            case "Head":
                Flashcard head = new Flashcard();
                head.setArraySubregion(new String[]{"Facial Expression", "Mastication"});
                head.setArrayStrImgLocation(new String[]{"", ""});
                flashcard = head;
                break;
            case "Neck":
                Flashcard neck = new Flashcard();
                neck.setArraySubregion(new String[]{"Anterior triangle", "Posterior Triangle", "Paravertebral"});
                neck.setArrayStrImgLocation(new String[]{"", "", ""});
                flashcard = neck;
                break;
            case "Thorax":
                Flashcard thorax= new Flashcard();
                thorax.setArraySubregion(new String[]{"Pectoral", "Thoracic wall", "Diaphragm"});
                thorax.setArrayStrImgLocation(new String[]{"", "", ""});
                flashcard = thorax;
                break;
            case "Back":
                Flashcard back = new Flashcard();
                back.setArraySubregion(new String[]{"Superficial", "Intermediate", "Deep", "Suboccipital"});
                back.setArrayStrImgLocation(new String[]{"", "", "", ""});
                flashcard = back;
                break;
            case "Abdomen":
                Flashcard abdomen = new Flashcard();
                abdomen.setArraySubregion(new String[]{"Anterior wall", "Posterior wall"});
                abdomen.setArrayStrImgLocation(new String[]{"", ""});
                flashcard = abdomen;
                break;
            case "Pelvis":
                Flashcard pelvis = new Flashcard();
                pelvis.setArraySubregion(new String[]{"Superficial perineal pouch", "Deep perineal pouch", "Anal triangle"});
                pelvis.setArrayStrImgLocation(new String[]{"", "", ""});
                flashcard = pelvis;
                break;
            default:
                Flashcard defaultSet = new Flashcard();
                flashcard = defaultSet;

        }

        return flashcard;
    }

    //Getters and setters for individual flashcards
    public String getStructureName() {
        return mStructureName;
    }

    public void setStructureName(String structureName) {
        mStructureName = structureName;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public String getInsertion() {
        return mInsertion;
    }

    public void setInsertion(String insertion) {
        mInsertion = insertion;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public String getBloodSupply() {
        return mBloodSupply;
    }

    public void setBloodSupply(String bloodSupply) {
        mBloodSupply = bloodSupply;
    }

    public String getInnervation() {
        return mInnervation;
    }

    public void setInnervation(String innervation) {
        mInnervation = innervation;
    }

    public String getImgLocation() {
        return mImgLocation;
    }

    public void setImgLocation(String imgLocation) {
        mImgLocation = imgLocation;
    }

    public String getSubregion() {
        return mSubregion;
    }

    public void setSubregion(String subregion) {
        mSubregion = subregion;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getCompartment() {
        return mCompartment;
    }

    public void setCompartment(String compartment) {
        mCompartment = compartment;
    }

    //Constructors, getters and setters for flashcard set objects
    public Flashcard(List<Flashcard> flashcardSet){
        setFlashcardSet(flashcardSet);
        setNumFlashcards(flashcardSet.size());
    }

    //Getters and setters
    public int getNumFlashcards() {
        return mNumFlashcards;
    }

    public void setNumFlashcards(int numFlashcards) {
        mNumFlashcards = numFlashcards;
    }

    public List<Flashcard> getFlashcardSet() {
        return mFlashcardSet;
    }

    public void setFlashcardSet(List<Flashcard> flashcardSet) {
        mFlashcardSet = flashcardSet;
    }

    public String[] getArrayStructureName() {
        return mArrayStructureName;
    }

    public void setArrayStructureName(String[] arrayStructureName) {
        mArrayStructureName = arrayStructureName;
    }

    public String[] getArrayOrigin() {
        return mArrayOrigin;
    }

    public void setArrayOrigin(String[] arrayOrigin) {
        mArrayOrigin = arrayOrigin;
    }

    public String[] getArrayInsertion() {
        return mArrayInsertion;
    }

    public void setArrayInsertion(String[] arrayInsertion) {
        mArrayInsertion = arrayInsertion;
    }

    public String[] getArrayAction() {
        return mArrayAction;
    }

    public void setArrayAction(String[] arrayAction) {
        mArrayAction = arrayAction;
    }

    public String[] getArrayBloodSupply() {
        return mArrayBloodSupply;
    }

    public void setArrayBloodSupply(String[] arrayBloodSupply) {
        mArrayBloodSupply = arrayBloodSupply;
    }

    public String[] getArrayInnervation() {
        return mArrayInnervation;
    }

    public void setArrayInnervation(String[] arrayInnervation) {
        mArrayInnervation = arrayInnervation;
    }

    public String[] getArraySubregion() {
        return mArraySubregion;
    }

    public void setArraySubregion(String[] arraySubregion) {
        mArraySubregion = arraySubregion;
    }

    public String[] getArrayStrImgLocation() {
        return mArrayStrImgLocation;
    }

    public void setArrayStrImgLocation(String[] arrayStrImgLocation) {
        mArrayStrImgLocation = arrayStrImgLocation;
    }

    public Integer[] getArrayIntImgLocation() {
        return mArrayIntImgLocation;
    }

    public void setArrayIntImgLocation(Integer[] arrayIntImgLocation) {
        mArrayIntImgLocation = arrayIntImgLocation;
    }

    public String[] getTitle() {
        return mTitle;
    }

    public void setTitle(String[] title) {
        mTitle = title;
    }

    public String[] getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String[] subtitle) {
        mSubtitle = subtitle;
    }

    //Methods for flashcard sets

    //Methods to set arrays for individual anatomical properties based on the flashcards within the set.
    public void setArrayFromSubregions (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getSubregion();
            list.add(s);
        }
        mArraySubregion = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromStructureNames (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getStructureName();
            list.add(s);
        }
        mArrayStructureName = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromOrigins (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getOrigin();
            list.add(s);
        }
        mArrayOrigin = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromInsertions (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getInsertion();
            list.add(s);
        }
        mArrayInsertion = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromActions (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getAction();
            list.add(s);
        }
        mArrayAction = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromBloodSupplys (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getBloodSupply();
            list.add(s);
        }
        mArrayBloodSupply = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromInnervations (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getInnervation();
            list.add(s);
        }
        mArrayInnervation = list.toArray(new String[mNumFlashcards]);
    }

    public void setArrayFromImgLocations (){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mNumFlashcards; i++) {
            String s = mFlashcardSet.get(i).getImgLocation();
            list.add(s);
        }
        mArrayStrImgLocation = list.toArray(new String[mNumFlashcards]);
    }


    // End of methods for setting individual array properties


    //Parcelable methods

    // "De-parcel object"
    protected Flashcard(Parcel in) {
        mStructureName = in.readString();
        mOrigin = in.readString();
        mInsertion = in.readString();
        mAction = in.readString();
        mBloodSupply = in.readString();
        mInnervation = in.readString();
        mImgLocation = in.readString();
        mSubregion = in.readString();
        mCompartment = in.readString();
        mRegion = in.readString();
        if (in.readByte() == 0x01) {
            mFlashcardSet = new ArrayList<Flashcard>();
            in.readList(mFlashcardSet, Flashcard.class.getClassLoader());
        } else {
            mFlashcardSet = null;
        }
        mNumFlashcards = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStructureName);
        dest.writeString(mOrigin);
        dest.writeString(mInsertion);
        dest.writeString(mAction);
        dest.writeString(mBloodSupply);
        dest.writeString(mInnervation);
        dest.writeString(mImgLocation);
        dest.writeString(mSubregion);
        dest.writeString(mCompartment);
        dest.writeString(mRegion);
        if (mFlashcardSet == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mFlashcardSet);
        }
        dest.writeInt(mNumFlashcards);
    }

    //Creator
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Flashcard> CREATOR = new Parcelable.Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };

}

