package android.example.com.login;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static data.UserContract.WaitlistEntry.COLUMN_EditText;
import static data.UserContract.WaitlistEntry.CONTENT_URI_USER;

public class LogInActivity extends AppCompatActivity {

    TextView name ;
    EditText text ;
    TextView output;


    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logedin);

        name = (TextView) findViewById(R.id.username);
        text = (EditText) findViewById(R.id.text);
        output = (TextView) findViewById(R.id.output);

        String r = getIntent().getExtras().getString("username");

        name.setText(r);


        String projection[]={
                COLUMN_EditText};
        ContentResolver contentResolver=getContentResolver();

        cursor=contentResolver.query(CONTENT_URI_USER,
                projection,
                null,
                null,
                null);

        String textenter= String.valueOf(text.getText());

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EditText, textenter);
        Uri uri = getContentResolver().insert(CONTENT_URI_USER, contentValues);
    }
    public void TextEditDone(View view) {

        String textenter= String.valueOf(text.getText());
        output.setText(textenter);
    }


}
