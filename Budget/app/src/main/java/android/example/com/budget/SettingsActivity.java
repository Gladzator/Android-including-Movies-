package android.example.com.budget;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity{

    MonthlyincmHelper mMonthlyincmHelper;
    ListView listView;
    //dialog values
    String incm;
    EditText mNewIncm;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        TextView txt=(TextView)findViewById(R.id.income);
        listView=(ListView)findViewById(R.id.editicnm);
        mMonthlyincmHelper=new MonthlyincmHelper(this);
        populateview();
    }

    private void populateview(){

        Cursor data =mMonthlyincmHelper.getData();
        ArrayList<String> listData=new ArrayList<>();
        itemId=-1;

        while (data.moveToNext()){
            itemId=data.getInt(0);
            listData.add(data.getString(1));
        }
        ListAdapter listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                incm=adapterView.getItemAtPosition(i).toString();
                Cursor data =mMonthlyincmHelper.getItemID(incm);//get the id associated with that value
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(SettingsActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.dialog_incomeeditor,null);
                mNewIncm=(EditText) mView.findViewById(R.id.newIncm);
                Button mCancel=(Button)mView.findViewById(R.id.cancelbtn);
                Button mConfirm=(Button)mView.findViewById(R.id.confirmbtn);


                mConfirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String income=mNewIncm.getText().toString();
                        if (mNewIncm.length() != 0 && income.matches("[0-9]+")) {
                             mMonthlyincmHelper.updateIncm(income, 1, incm);
                            finish();
                        }else if(mNewIncm.length()==0){
                            toastmessage("You must put something!");
                        }else{
                            toastmessage("Enter valid data");
                        }
                    }
                });
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog=mBuilder.create();
                dialog.show();
            }
        });

    }

    private void toastmessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
