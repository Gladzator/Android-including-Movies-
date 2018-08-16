package com.example.android.music;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;



public class MusicAdapter extends ArrayAdapter<Music> {


    /**
     * Create a new {@link MusicAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param musics is the list of {@link Music}s to be displayed.
     */
    public MusicAdapter(Context context, ArrayList<Music> musics) {
        super(context, 0, musics);

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_music, parent, false);
        }

        Music currentMusic = getItem(position);

        TextView musicTextView = (TextView) listItemView.findViewById(R.id.music_text_view);



        musicTextView.setText(currentMusic.getName());
        // Get the {@link Word} object located at this position in the list


        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.



        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Check if an image is provided for this word or not
        if (currentMusic.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentMusic.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
