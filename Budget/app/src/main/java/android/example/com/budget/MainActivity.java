package android.example.com.budget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    MonthlyincmHelper mMonthlyincmHelper;
    ImageButton calenderbtn,analysisbtn;
    //Calender
    static final int dialog_id=1;
    TextView dateText;
    int year1,month1,day1;

    //checking which button clicked and opening the required data entry fragmnet
    String s;

    //data entry
    Button dataentrybtn;
    Button editDatabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateText=(TextView)findViewById(R.id.date);
        mMonthlyincmHelper=new MonthlyincmHelper(this);
        calenderbtn= (ImageButton) findViewById(R.id.calender);

        analysisbtn=(ImageButton)findViewById(R.id.analysis);

        //dataentry
        dataentrybtn=(Button)findViewById(R.id.enterdata);

        editDatabtn=(Button)findViewById(R.id.editdata);

        calenderbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        //analysis
        analysisbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMonthlyincmHelper = new MonthlyincmHelper(MainActivity.this);
                Cursor data = mMonthlyincmHelper.getDetailsData();
            if(data.getCount()==0){
                toastmessage("No data!");
            }else {
                if (day1 == 0 || month1 == 0 || year1 == 0) {
                    toastmessage("Please select a date!");
                }  else {
                    PieChartFragment fragment = new PieChartFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("year", String.valueOf(year1));
                    bundle.putString("month", String.valueOf(month1));
                    bundle.putString("day", String.valueOf(day1));

                    fragment.setArguments(bundle);

                    transaction.replace(R.id.fragment, fragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();

                }
            }
            }

        });

        //dataentry
        dataentrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(day1==0||month1==0||year1==0){
                    toastmessage("Please select a date!");
                }else if(!valid()){
                    DataEntryFragment fragment = new DataEntryFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("year", String.valueOf(year1));
                    bundle.putString("month", String.valueOf(month1));
                    bundle.putString("day", String.valueOf(day1));

                    fragment.setArguments(bundle);


                    transaction.replace(R.id.fragment, fragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }else{
                    toastmessage("Data exists!");
                }

            }
        });

        editDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (day1 == 0 || month1 == 0 || year1 == 0) {
                    toastmessage("Please select a date!");
                }
                else if (valid()) {
                    EditDataFragment fragment = new EditDataFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("year", String.valueOf(year1));
                    bundle.putString("month", String.valueOf(month1));
                    bundle.putString("day", String.valueOf(day1));
                    fragment.setArguments(bundle);


                    transaction.replace(R.id.fragment, fragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                } else {
                    toastmessage("No data found!");
                }


            }
        });


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, Monthlyincm.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        display();

    }
    public boolean valid(){
        Cursor data = mMonthlyincmHelper.getDetailsData();
        if(data.getCount()==0){
            return false;
        }
        data.moveToFirst();
        do {
            String days=data.getString(1);
            String months=data.getString(2);
            String years=data.getString(3);

            if((days.equals(String.valueOf(day1)))&&(months.equals(String.valueOf(month1)))&&(years.equals(String.valueOf(year1)))){
                return true;

            }

        } while (data.moveToNext());
        return false;
    }
    public void display() {
        Cursor data = mMonthlyincmHelper.getData();
        while (data.moveToNext()) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
               Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
               startActivity(intent);
            default:return super.onOptionsItemSelected(item);
        }
    }
    //Calender


    Calendar myCalendar = Calendar.getInstance();
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            year1=year;
            month1=monthOfYear;
            day1=dayOfMonth;

            updateLabel();

        }

    };

    private void toastmessage(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}




