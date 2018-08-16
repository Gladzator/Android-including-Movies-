package com.example.android.miwok;

import android.widget.ImageView;

/**
 * Created by Meena on 16-09-2017.
 */

public class Word {

    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mImageResourceId=NO_IMAGE_VIEW;

    public static final int NO_IMAGE_VIEW = -1;

    private int mAudioId;

    //layout without images
    public Word(String defaultTranslation,String miwokTranslation,int AudioId){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mAudioId=AudioId;
    }
    //layouts with images
    public Word(String defaultTranslation,String miwokTranslation,int image,int AudioId){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageResourceId=image;
        mAudioId=AudioId;
    }
    /*get translations*/

    public String getdefaultTranslation(){

        return mDefaultTranslation;
    }

    public  String getmiwokTranslation(){
        return mMiwokTranslation;
    }

    //adding image

    public int getImageResourceId(){

        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId !=NO_IMAGE_VIEW;
    }

    public int getAudioId(){

        return mAudioId;
    }


}
