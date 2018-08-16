package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static data.UserContract.WaitlistEntry.COLUMN_EditText;
import static data.UserContract.WaitlistEntry.TABLE_USERNAME;


public class UserDbHelper extends SQLiteOpenHelper{
    // The database name
    private static final String DATABASE_USERNAME = "userlist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 6;

    // Constructor
    public UserDbHelper(Context context){
        super(context,DATABASE_USERNAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + LoginContract.WaitlistEntry.TABLE_NAME + " (" +
                LoginContract.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EditText+"TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoginContract.WaitlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData(SQLiteDatabase db){
        String[] p={
                "_id",
                COLUMN_EditText
        };
        String sortOrder ="_id" + " DESC";

        return db.query(
                TABLE_USERNAME,
                p,
                null,null,null,null,
                sortOrder
        );
    }
}
