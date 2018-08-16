package com.example.android.music;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            // Find the View
            TextView numbers = (TextView) findViewById(R.id.name);

            // Set a click listener on that View
            numbers.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the numbers category is clicked on.
                @Override
                public void onClick(View view) {
                    Intent numbersIntent = new Intent(MainActivity.this, nameofmusicActivity.class);

                    // Start the new activity
                    startActivity(numbersIntent);
                }
            });
        }
    }

