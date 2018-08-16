package android.example.com.budget;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataFragment  extends Fragment {

    public EditDataFragment() {
        // Required empty public constructor
    }

    EditText home;
    EditText daily;
    EditText transport;
    EditText entertainment;
    EditText personal;
    EditText financial;
    MonthlyincmHelper mMonthlyincmHelper;
    Button done;
    int checker, itemId;
    String days,months,years;

    String s, year, month, day, home1, daily1, transport1, entertainment1, personal1, financial1;
    String oldhome, olddaily, oldtransport, oldentertainment, oldpersonal, oldfinancial;

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

        Cursor data = mMonthlyincmHelper.getDetailsData();
        data.moveToFirst();
        itemId=0;
        do {
            days=data.getString(1);
            months=data.getString(2);
            years=data.getString(3);
            if((days.equals(day))&&(months.equals(month))&&(years.equals(year))){
                itemId=data.getPosition();
                break;
            }

        } while (data.moveToNext());
        data.moveToPosition(itemId);
        oldhome = (data.getString(4));
        home.setText(data.getString(4));

        olddaily = (data.getString(5));
        daily.setText(data.getString(5));

        oldtransport = (data.getString(6));
        transport.setText(data.getString(6));

        oldentertainment = (data.getString(7));
        entertainment.setText(data.getString(7));

        oldpersonal = (data.getString(8));
        personal.setText(data.getString(8));

        oldfinancial = (data.getString(9));
        financial.setText(data.getString(9));
        data.close();


        done.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
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
            UpdateData(itemId,day, month, year, home1, daily1, transport1, entertainment1, personal1, financial1);
    }
    public  void UpdateData(int itemId,String day, String month, String year, String home, String daily, String transport, String entertainment, String personal, String financial) {

        mMonthlyincmHelper.updateDetails((itemId+1), home, daily, transport, entertainment, personal, financial);
        toastmessage("Update was success!");

        BlankFragment fragment = new BlankFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment); // give your fragment container id in first parameter
        transaction.commit();
    }
    private void toastmessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}




