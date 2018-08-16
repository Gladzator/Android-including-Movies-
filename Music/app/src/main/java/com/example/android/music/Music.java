package com.example.android.music;

public class Music {


    private int mImageResourceId=NO_IMAGE_VIEW;
    public static final int NO_IMAGE_VIEW = -1;
    private int mname;
    private int mAudioID;

    public Music(int name, int ImageResourceId, int AudioID){
        mname=name;
        mImageResourceId=ImageResourceId;
        mAudioID=AudioID;

    }
    public int getImageResourceId(){

        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId !=NO_IMAGE_VIEW;
    }

    public int getAudioId(){

        return mAudioID;
    }
    public int getName() {
        return mname;
    }

}
