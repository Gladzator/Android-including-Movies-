package android.example.com.budget;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MonthlyincmHelper extends SQLiteOpenHelper {

    private static final String TAG="MonthlyincmHelper";
    private static final String TABLE_NAME_DETAILS="details";
    private static final String TABLE_NAME="monthlyincm";
    private static final String COL_0="ID";
    private static final String COL_1="income";
    private static final String COL_2="day";
    private static final String COL_3="month";
    private static final String COL_4="year";
    private static final String COL_5="home";
    private static final String COL_6="daily";
    private static final String COL_7="transport";
    private static final String COL_8="entertainment";
    private static final String COL_9="personal";
    private static final String COL_10="financial";
    private static final String ID="ID";


    public MonthlyincmHelper(Context context) {
        super(context,TABLE_NAME,null,9);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String incmTable="CREATE TABLE "+TABLE_NAME+" ("+COL_0+" INTEGER PRIMARY KEY, "+
            COL_1+" INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(incmTable);
        String detailsTable="CREATE TABLE "+TABLE_NAME_DETAILS+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_2+" INTEGER NOT NULL, "+COL_3+" INTEGER NOT NULL, "+
                COL_4+" INTEGER NOT NULL, "+COL_5+" INTEGER NOT NULL, "+
                COL_6+" INTEGER NOT NULL, "+COL_7+" INTEGER NOT NULL, "+
                COL_8+" INTEGER NOT NULL, "+COL_9+" INTEGER NOT NULL, "+
                COL_10+" INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(detailsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DETAILS);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,item);

        Log.d(TAG,"addData:Adding "+item +"to "+TABLE_NAME);

        long result=db.insert(TABLE_NAME,null,contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor data=db.rawQuery(query,null);
        return data;
    }

    public Cursor getItemID(String incm){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT "+COL_0+" FROM "+TABLE_NAME+
                " WHERE "+COL_1+" = '"+incm+"'";
        Cursor data=db.rawQuery(query,null);
        return data;

    }

    public void updateIncm(String newIncm,int id,String oldIncm){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+COL_1+
                " = '"+newIncm +"' WHERE "+COL_0+" = '"+id+"'"+
                " AND "+COL_1+" = '"+oldIncm+"'";
        db.execSQL(query);
    }

    public Cursor getDetailsID(String day,String month,String year,String home,String daily,String transport,String entertainment,String personal,String financial) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID + " FROM " + TABLE_NAME_DETAILS +
                " WHERE " + COL_2 + " = '" + day + "',"+COL_3+" = '"+ month
                +"',"+COL_4+" = '"+year+"',"+ COL_5 + " = '" + home + "',"+COL_6+" = '"+ daily
                +"',"+COL_7+" = '"+transport+"',"+ COL_8 + " = '" + entertainment + "',"+COL_9+" = '"+ personal
                +"',"+COL_10+" = '"+financial+"'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }
    public Cursor getDetailsData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME_DETAILS;
        Cursor data=db.rawQuery(query,null);
        return data;
    }
    public boolean addDetailsData(String day,String month,String year,String home,String daily,String transport,String entertainment,String personal,String financial){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues1=new ContentValues();

        contentValues1.put(COL_2,day);
        contentValues1.put(COL_3,month);
        contentValues1.put(COL_4,year);
        contentValues1.put(COL_5,home);
        contentValues1.put(COL_6,daily);
        contentValues1.put(COL_7,transport);
        contentValues1.put(COL_8,entertainment);
        contentValues1.put(COL_9,personal);
        contentValues1.put(COL_10,financial);

        long result=db.insert(TABLE_NAME_DETAILS,null,contentValues1);

        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public void updateDetails(int id, String home,String daily,String transport,String entertainment,String personal,String financial) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update="UPDATE details SET home = ?,daily = ?,transport = ?,entertainment = ?,personal = ?,financial = ? WHERE id= ?";
        db.execSQL(update,new String[]{home,daily,transport,entertainment,personal,financial,String.valueOf(id)});
    }
    public void delete(int id,String day,String month, String year,String oldhome,String olddaily,String oldtransport,String oldentertainment,String oldpersonal,String oldfinancial
            ,String home,String daily,String transport,String entertainment,String personal,String financial){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="DELETE FROM "+TABLE_NAME_DETAILS+" WHERE "+ID+"='"+id+"'";
        Log.d(TAG,query);
        db.execSQL(query);
    }
    public void update(){

    }
}
