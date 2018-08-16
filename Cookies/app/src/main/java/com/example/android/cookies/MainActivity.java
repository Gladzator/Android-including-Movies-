package com.example.android.cookies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.drawable;
import static android.R.attr.id;
import static com.example.android.cookies.R.drawable.after_cookie;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void eat(View view)
    {
        String after="I'm so full";
        eatCookie(after);
    }
    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(String after) {
        // TODO: Find a reference to the ImageView in the layout. Change the image.
        ImageView afterea=(ImageView) findViewById(R.id.android_cookie_image_view);
        afterea.setImageResource(R.drawable.after_cookie);
        // TODO: Find a reference to the TextView in the layout. Change the text.
        TextView aftereat=(TextView) findViewById(R.id.status_text_view);
        aftereat.setText(""+after);
    }
}
