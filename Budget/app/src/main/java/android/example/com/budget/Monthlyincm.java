package android.example.com.budget;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Monthlyincm extends AppCompatActivity {

    private static final String TAG="Monthlyincm";

    MonthlyincmHelper mMonthlyincmHelper;
    private ImageButton monthlyincm;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthincm);

        editText = (EditText) findViewById(R.id.monthincm);
        monthlyincm = (ImageButton) findViewById(R.id.monthdne);
        mMonthlyincmHelper = new MonthlyincmHelper(this);

        monthlyincm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String income=editText.getText().toString();
                if(editText.length()!=0&& income.matches("[0-9]+")){
                    AddData(income);
                    editText.setText("");
                    Intent intent=new Intent(Monthlyincm.this,MainActivity.class);
                    startActivity(intent);
                }else if(editText.length()==0){
                    toastmessage("You must put something!");
                }else{
                    toastmessage("Enter valid data");
                }

            }
        });

    }

        public void AddData(String income){
            boolean insertData=mMonthlyincmHelper.addData(income);

            if(insertData){
                toastmessage("Data successfully inserted!");
            }else{
                toastmessage("Something went wrong");
            }
        }


        private void toastmessage(String message){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }

}