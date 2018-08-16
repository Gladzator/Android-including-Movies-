package android.example.com.data;

import android.net.Uri;
import android.provider.BaseColumns;



public class NoteContract {


    //authority which is how the code knows which Content Provider to access
    public static final String AUTHORITY="com.example.android.yournote";

    //The base content URI= "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    //Define the possibel paths for accessing data in this contract
    //This is the path for the "tasks" directory
    public static final String PATH_TASKS="notes";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class NoteEntry implements BaseColumns {

        //TaskEntry content URI=base content + path
        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        // Task table and column names
        public static final String TABLE_NAME = "notes";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_NOTE = "note";


        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is generated automatically

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Complete lesson   |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn guitar     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -

         */

    }
}
