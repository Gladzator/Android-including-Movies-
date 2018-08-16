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

package com.example.android.todolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.android.todolist.data.TaskContract.TaskEntry.CONTENT_URI;
import static com.example.android.todolist.data.TaskContract.TaskEntry.TABLE_NAME;

public class TaskContentProvider extends ContentProvider {

    public static final int TASKS=100;
    public static final int TASKS_WITH_ID=101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
      UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

      //Add matches with addUri(String authority,String path,int code)
        //directory
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKS,TASKS);
        //single item
        uriMatcher.addURI(TaskContract.AUTHORITY,TaskContract.PATH_TASKS + "/#" ,TASKS_WITH_ID);

        return uriMatcher;
    }

    TaskDbHelper mTaskDbHelper;

    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {
        Context context=getContext();
        mTaskDbHelper=new TaskDbHelper(context);

        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case TASKS:
                //Inserting values into tasks table
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db=mTaskDbHelper.getReadableDatabase();

        int match=sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match){
            //Query for tasks directory
            case TASKS:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case TASKS_WITH_ID:
                //Get id from URI
                String id = uri.getPathSegments().get(1);

                //Selection is the _ID column=? and selection args =the row from the URI
                String mSelection="_id=?";
                String[] mSelectionArgs= new String[]{id};

                //Construct a query as you would normally,passing in the selection/args
                retCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            //Default exception
            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);
        }

        //notifying that change has occured
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        switch (match) {
            case TASKS_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted=db.delete(TABLE_NAME, "_id=?",new String[]{id});

                break;

            //Default exception
            default:
                throw new UnsupportedOperationException("Unkown uri:" + uri);
        }
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
