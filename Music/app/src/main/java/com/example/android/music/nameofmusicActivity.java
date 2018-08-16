package com.example.android.music;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Meena on 22-12-2017.
 */

public class nameofmusicActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new nameofmusic())
                .commit();
    }
}
