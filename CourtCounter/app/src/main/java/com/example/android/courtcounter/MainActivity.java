package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int na=0,nb=0;
    public void A3(View view)
    {
        na=na+3;
        A3(na);
    }
    public void A2(View view)
    {
        na=na+2;
        A2(na);
    }
    public void A1(View view)
    {
        na=na+1;
        A1(na);
    }
    private void A3(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsa);
        quantityTextView.setText("" + number);
    }
    private void A2(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsa);
        quantityTextView.setText("" + number);
    }
    private void A1(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsa);
        quantityTextView.setText("" + number);
    }
    public void B3(View view)
    {
        nb=nb+3;
        B3(nb);
    }
    public void B2(View view)
    {
        nb=nb+2;
        B2(nb);
    }
    public void B1(View view)
    {
        nb=nb+1;
        B1(nb);
    }
    private void B3(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsb);
        quantityTextView.setText("" + number);
    }
    private void B2(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsb);
        quantityTextView.setText("" + number);
    }
    private void B1(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.pointsb);
        quantityTextView.setText("" + number);
    }
    public void Reset(View view)
    {
        na=0;
        nb=0;
        Reset(na);
        Reset1(nb);
    }
    private void Reset(int na){
        TextView quantityTextViewa = (TextView) findViewById(R.id.pointsa);
        quantityTextViewa.setText("" + na);
    }
    private void Reset1(int nb){
        TextView quantityTextViewa = (TextView) findViewById(R.id.pointsb);
        quantityTextViewa.setText("" + nb);
    }

}
