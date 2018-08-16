package android.example.com.yournote;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.com.data.NoteDbHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.example.com.data.NoteContract.NoteEntry.COLUMN_NOTE;
import static android.example.com.data.NoteContract.NoteEntry.CONTENT_URI;
import static android.example.com.data.NoteContract.NoteEntry.TABLE_NAME;

public class AddNoteActivity extends AppCompatActivity {

    private String mNote;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText myNote=(EditText) findViewById(R.id.editTextNote);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mNote = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                myNote.setText(mNote);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_note, menu);
        MenuItem menuItem = menu.findItem(R.id.action_done);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            String input = ((EditText) findViewById(R.id.editTextNote)).getText().toString();
            if(input.length()==0){
                return false;
            }

            //new content values object
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_NOTE,input);

            Uri uri = getContentResolver().insert(CONTENT_URI,contentValues);

            if(uri!=null){
                Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();
            }

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
