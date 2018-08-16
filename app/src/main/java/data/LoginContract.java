package data;

import android.net.Uri;
import android.provider.BaseColumns;

public class LoginContract {

    //authority which is how the code knows which Content Provider to access
    public static final String AUTHORITY="com.example.android.login";

    //The base content URI= "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    //Define the possible paths for accessing data in this contract
    //This is the path for the "tasks" directory
    public static final String PATH_TASKS="tasks";


    public static final class WaitlistEntry implements BaseColumns {

        //TaskEntry content URI=base content + path
        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "list";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";

    }

}
