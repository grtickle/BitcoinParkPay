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

    //Table info with columns, id, address, and apiKey
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wallet.db";
    public static final String INFO_TABLE_NAME = "info";
    public static final String INFO_COLUMN_ID = "id";
    public static final String INFO_COLUMN_KEY = "apiKey";
    public static final String INFO_COLUMN_ADDRESS = "address";
    public static final String INFO_COLUMN_LABEL = "addressLabel";
    public static final String INFO_COLUMN_BALANCE = "balance";
    public HashMap hp;

    public final class InfoContract {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public InfoContract() {}

        /* Inner class that defines the table contents */
    }

    public static abstract class InfoEntry implements BaseColumns {
        public static final String TABLE_NAME_ADDRESSES = "TAddress";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_LABEL = "addressLabel";
        public static final String COLUMN_NAME_BALANCE = "balance";

        public static final String TABLE_NAME_KEYS = "TKeys";
        public static final String COLUMN_NAME_KEYS_ID = "id";
        public static final String COLUMN_NAME_KEYS = "keys";
        public static final String COLUMN_NAME_KEYS_DESCRIPTION = "description";
    }

    private static final String TEXT_TYPE = "TEXT";
    private static final String DOUBLE_TYPE = "DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InfoEntry.TABLE_NAME_ADDRESSES + " (" +
                    InfoEntry._ID + " INTEGER PRIMARY KEY," +
                    InfoEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    InfoEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    InfoEntry.COLUMN_NAME_LABEL + TEXT_TYPE + COMMA_SEP +
                    InfoEntry.COLUMN_NAME_BALANCE + DOUBLE_TYPE + COMMA_SEP +
                    " )" +
                    "CREATE TABLE " + InfoEntry.TABLE_NAME_KEYS + " (" +
                    InfoEntry._ID + " INTEGER PRIMARY KEY," +
                    InfoEntry.COLUMN_NAME_KEYS_ID + TEXT_TYPE + COMMA_SEP +
                    InfoEntry.COLUMN_NAME_KEYS + TEXT_TYPE + COMMA_SEP +
                    InfoEntry.COLUMN_NAME_KEYS_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    " )";

    //Setting up Database
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //Updates the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS TAddress");
        onCreate(db);
    }

    //Inserts info into Table
    public boolean insertInfo  (String tableName, ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, values);
        return true;
    }

    //Gets Table data based on id
    public Cursor getData(String tableName, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + tableName + " where id=" + id + "", null );
        return res;
    }

    //Counts the number of rows
    public int numberOfRows(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        return numRows;
    }

    //Updates value in table
    public boolean updateInfo (String tableName, Integer id, String address, String apiKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("apiKey", apiKey);
        db.update(tableName, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //Deletes id
    public Integer deleteInfo (String tableName, Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,
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