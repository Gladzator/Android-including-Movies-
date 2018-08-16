package android.example.com.login;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import static data.LoginContract.WaitlistEntry.COLUMN_PASSWORD;
import static data.LoginContract.WaitlistEntry.COLUMN_USER_NAME;
import static data.LoginContract.WaitlistEntry.CONTENT_URI;

public class SignUpActivity extends AppCompatActivity {

int f;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        String projection[] = {
                COLUMN_USER_NAME,
                COLUMN_PASSWORD};
        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    public void signup(View view) {

        String m;
        String name = ((EditText) findViewById(R.id.username)).getText().toString();
        if (name.length() == 0) {
            return;
        }

        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if (password.length() == 0) {
            return;
        }

        f=1;
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            m = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
            if ((m.equals(name))) {
                Toast.makeText(SignUpActivity.this, "Already Exists!", Toast.LENGTH_SHORT).show();
                f = 0;
                recreate();
                break;
            } else {
                f=1;
            }
        }
        cursor.close();

        if (f ==1){
            //new content values object
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_USER_NAME, name);
            contentValues.put(COLUMN_PASSWORD, password);

            Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);


            if (uri != null) {
                Toast.makeText(getBaseContext(), "Created Successfully!", Toast.LENGTH_LONG).show();
            }
            SignUpActivity.this.finish();
            Intent i=new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(i);

        }
    }
}

