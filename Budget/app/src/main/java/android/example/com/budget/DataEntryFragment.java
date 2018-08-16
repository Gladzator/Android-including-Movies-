package android.example.com.budget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DataEntryFragment extends Fragment {


    EditText home;
    EditText daily;
    EditText transport;
    EditText entertainment;
    EditText personal;
    EditText financial;
    MonthlyincmHelper mMonthlyincmHelper;

    Button done;
    int checker,itemId;
    String year,month,day,home1,daily1,transport1,entertainment1,personal1,financial1;
    public DataEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_data_entry, container, false);
        home = (EditText) v.findViewById(R.id.costofhome);
        daily = (EditText) v.findViewById(R.id.costofliving);
        transport = (EditText) v.findViewById(R.id.costoftransportation);
        entertainment = (EditText) v.findViewById(R.id.costofentertainment);
        personal = (EditText) v.findViewById(R.id.costofpersonal);
        financial = (EditText) v.findViewById(R.id.costofobligations);

        done = (Button) v.findViewById(R.id.done);

        // Inflate the layout for this fragment
        year = getArguments().getString("year");
        month = getArguments().getString("month");
        day = getArguments().getString("day");
        mMonthlyincmHelper = new MonthlyincmHelper(getContext());

        checker = 0;
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //home
                home1 = home.getText().toString();
                checking(home, home1);
                //daily
                daily1 = daily.getText().toString();
                checking(daily, daily1);
                //transportation
                transport1 = transport.getText().toString();
                checking(transport, transport1);
                //entertainment
                entertainment1 = entertainment.getText().toString();
                checking(entertainment, entertainment1);//
                //personal
                personal1 = personal.getText().toString();
                checking(personal, personal1);
                //financial obligations
                financial1 = financial.getText().toString();
                checking(financial, financial1);

                if (checker == 0 || checker == 1) {
                    success();
                } else {
                    toastmessage("Something went wrong");
                }

            }
        });
        return v;
    }

    public void checking(EditText text, String value) {
        if (text.length() != 0 && value.matches("[0-9]+")) {
            checker = 0;
        } else if (text.length() == 0) {
            checker = 1;
            toastmessage("You must put something!");
        } else {
            checker = 1;
            toastmessage("Enter valid data");
        }

    }

    public void success() {
            AddData(day, month, year, home1, daily1, transport1, entertainment1, personal1, financial1);
        }

    public void AddData(String day, String month, String year, String home, String daily, String transport, String entertainment, String personal, String financial) {

        Cursor data = mMonthlyincmHelper.getDetailsData();

        if(checker==0) {
            boolean insertData = mMonthlyincmHelper.addDetailsData(day, month, year, home, daily, transport, entertainment, personal, financial);
            if (insertData) {
                toastmessage("Data successfully inserted!");
                BlankFragment fragment = new BlankFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, fragment); // give your fragment container id in first parameter
                transaction.commit();
            } else {
                toastmessage("Something went wrong");
            }
        }
        data.moveToFirst();
        itemId=data.getInt(2);
    }
    private void toastmessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
