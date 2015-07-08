package edu.uc.bitcoinparkpay;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Andrew on 6/30/2015.
 */

//Added base values
public class DBHelper extends SQLiteOpenHelper {

    //Table info with columns, id, address, and apiKey
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wallet.db";
    public static final String INFO_TABLE_NAME = "info";
    public static final String INFO_COLUMN_ID = "id";
    public static final String INFO_COLUMN_ADDRESS = "address";
    public static final String INFO_COLUMN_KEY = "apiKey";
    public HashMap hp;

//    public final class InfoContract {
//        // To prevent someone from accidentally instantiating the contract class,
//        // give it an empty constructor.
//        public InfoContract() {}
//
//        /* Inner class that defines the table contents */
//    }
//
//    public static abstract class InfoEntry implements BaseColumns {
//        public static final String TABLE_NAME = "entry";
//        public static final String COLUMN_NAME_ID = "id";
//        public static final String COLUMN_NAME_ADDRESS = "address";
//    }
//
//    private static final String TEXT_TYPE = "TEXT";
//    private static final String COMMA_SEP = ",";
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + InfoEntry.TABLE_NAME + " (" +
//                    InfoEntry._ID + " INTEGER PRIMARY KEY," +
//                    InfoEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
//                    InfoEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
//                    " )";

    //Setting up Database
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table info " +
                        "(id integer primary key, address text, apiKey text)"
        );
    }

    //Updates the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS info");
        onCreate(db);
    }

    //Inserts info into Table
    public boolean insertInfo  (String address, String apiKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("apiKey", apiKey);
        db.insert("info", null, contentValues);
        return true;
    }

    //Gets Table data based on id
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from info where id="+id+"", null );
        return res;
    }

    //Counts the number of rows
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INFO_TABLE_NAME);
        return numRows;
    }

    //Updates value in table
    public boolean updateInfo (Integer id, String address, String apiKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("apiKey", apiKey);
        db.update("info", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //Deletes id
    public Integer deleteInfo (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("info",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    //Gets all values in the Table
    public ArrayList<String> getAllInfo()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from info", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(INFO_COLUMN_ADDRESS)));
            res.moveToNext();
        }
        return array_list;
    }
}