package edu.uc.bitcoinparkpay.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 6/30/2015.
 */

//Added base values
public class DBHelper extends SQLiteOpenHelper {

    //Table named "info" with columns: id, address, and apiKey
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wallet.db";
    public static final String INFO_TABLE_NAME = "info";
    public static final String INFO_COLUMN_ID = "id";
    public static final String INFO_COLUMN_ADDRESS = "address";
    public static final String INFO_COLUMN_KEY = "apiKey";
    public HashMap hp;

    // public final class InfoContract {
    //     // To prevent someone from accidentally instantiating the contract class,
    //     // give it an empty constructor.
    //     public InfoContract() {}

    //     /* Inner class that defines the table contents */
    // }

    // public static abstract class InfoEntry implements BaseColumns {
    //     public static final String TABLE_NAME = "entry";
    //     public static final String COLUMN_NAME_ID = "id";
    //     public static final String COLUMN_NAME_ADDRESS = "address";
    // }

    // private static final String TEXT_TYPE = "TEXT";
    // private static final String COMMA_SEP = ",";
    // private static final String SQL_CREATE_ENTRIES =
    //         "CREATE TABLE " + InfoEntry.TABLE_NAME + " (" +
    //                 InfoEntry._ID + " INTEGER PRIMARY KEY," +
    //                 InfoEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
    //                 InfoEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
    //                 " )";

    //Setting up Database
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //executes sql statement to create table
        db.execSQL(
                "create table info " +
                        "(id integer primary key, address text, apiKey text)"
        );
    }

    //UUpgrade Statement
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrades database version and drops old version
        db.execSQL("DROP TABLE IF EXISTS info");
        onCreate(db);
    }

    //Insert Statement
    public boolean insertInfo  (String addressLabel, String apiKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", addressLabel);
        contentValues.put("apiKey", apiKey);
        
        //insert values into table
        db.insert("info", null, contentValues);
        return true;
    }

    //Gets table data
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //get all values from the table on the id
        Cursor res =  db.rawQuery( "select * from info where id="+id+"", null );
        return res;
    }

    //Counts the number of rows
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INFO_TABLE_NAME);
        return numRows;
    }

    //Update Statement
    public boolean updateInfo (Integer id, String address, String apiKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("apiKey", apiKey);
        //Updates table to match new values
        db.update("info", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //Delete statement
    public Integer deleteInfo (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete values in the table on the id
        return db.delete("info",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    //Gets all values in the Table and return as an array list
    public ArrayList<String> getAllInfo()
    {
        ArrayList<String> array_list = new ArrayList<String>();
        
        hp = new HashMap();
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
