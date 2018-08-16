/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package android.example.com.login;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.LoginContract;
import data.LoginDbHelper;

import static android.provider.BaseColumns._ID;
import static data.LoginContract.WaitlistEntry.COLUMN_PASSWORD;
import static data.LoginContract.WaitlistEntry.COLUMN_USER_NAME;
import static data.LoginContract.WaitlistEntry.CONTENT_URI;
import static data.LoginContract.WaitlistEntry.TABLE_NAME;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    // Constants for logging and referring to a unique loader
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    Cursor cursor,cursoruser;
    // Member variables for the adapter and RecyclerView
    private ListAdapter mAdapter;
    RecyclerView mRecyclerView;

    int f;

    EditText mUsername;
    EditText mPassword;

    String usernameEntered;
    String passwordEntered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        mRecyclerView = (RecyclerView) findViewById(R.id.all_guests_list_view);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id=(int) viewHolder.itemView.getTag();

                String stringId=Integer.toString(id);
                Uri uri=CONTENT_URI;
                uri=uri.buildUpon().appendPath(stringId).build();

                getContentResolver().delete(uri,null,null);
                getSupportLoaderManager().restartLoader(TASK_LOADER_ID,null,MainActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         Ensure a loader is initialized and active. If the loader doesn't already exist, one is
         created, otherwise the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

         String projection[]={
                COLUMN_USER_NAME,
                COLUMN_PASSWORD};
        ContentResolver contentResolver=getContentResolver();

        cursor=contentResolver.query(CONTENT_URI,
                 projection,
                null,
                null,
                null);

    }


        public void loginbu(View view) {


            String m,p;
            mUsername=(EditText) findViewById(R.id.usernameet);
            usernameEntered=mUsername.getText().toString();

            mPassword=(EditText) findViewById(R.id.passwordet);
            passwordEntered=mPassword.getText().toString();

            Toast toast=null;

                for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                    m = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));

                    p = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                    if ((m.equals(usernameEntered))) {
                        if (((p.equals(passwordEntered)))) {
                            toast=Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT);
                            toast.show();
                            f = 1;
                            //pass username entered
                            Bundle user=new Bundle();
                            user.putString("username",usernameEntered);
                            Intent pass=new Intent(getApplication(),LogInActivity.class);
                            pass.putExtras(user);
                            startActivity(pass);

                            break;
                        }
                    } else {
                        continue;
                    }
                }
                if((cursor.isAfterLast()&& f!=1)){
                toast=Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT);
                toast.show();
            }
                cursor.moveToFirst();

        }


    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }


    public void signupbu(View view){
        Intent mIntent=new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(mIntent);
    }

    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID.
     * This loader will return task data as a Cursor or null if an error occurs.
     *
     * Implements the required callbacks to take care of loading data at all stages of loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {

                try{
                    return getContentResolver().query(CONTENT_URI,
                            null,
                            null,
                            null,
                             null);

                }catch(Exception e){
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;

                super.deliverResult(data);
            }
        };

    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        mAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}

