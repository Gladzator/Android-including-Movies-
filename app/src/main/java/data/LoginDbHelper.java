package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static data.LoginContract.WaitlistEntry.COLUMN_PASSWORD;
import static data.LoginContract.WaitlistEntry.COLUMN_USER_NAME;
import static data.LoginContract.WaitlistEntry.TABLE_NAME;


public class LoginDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "list.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 6;

    // Constructor
    public LoginDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + LoginContract.WaitlistEntry.TABLE_NAME + " (" +
                LoginContract.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LoginContract.WaitlistEntry.COLUMN_USER_NAME + " TEXT NOT NULL UNIQUE, " +
                LoginContract.WaitlistEntry.COLUMN_PASSWORD + " TEXT NOT NULL); ";

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
                COLUMN_USER_NAME,
                COLUMN_PASSWORD
        };
        String sortOrder ="_id" + "DESC";

        return db.query(
                TABLE_NAME,
                p,
                null,null,null,null,
                sortOrder
        );
    }
}
