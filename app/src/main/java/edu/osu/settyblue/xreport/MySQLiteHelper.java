package edu.osu.settyblue.xreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rakshith on 10/30/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "myschema.db";
    private static final int DATABASE_VERSION = 3;

    public static final String X_ITEMS_TABLE_NAME = "expenseitems";
    public static final String X_ITEMS_COL_ITEM_ID = "expense_item_id";
    public static final String X_ITEMS_COL_X_ID = "expense_id";
    public static final String X_ITEMS_COL_ITEM_NAME = "item_name";

    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COL_ID = "id";
    public static final String USERS_COL_USERNAME = "username";
    public static final String USERS_COL_PASSWORD = "password";

    private static final String USERS_TABLE_CREATE = "create table " + USERS_TABLE_NAME
            + "(" + USERS_COL_ID + " integer primary key autoincrement, " + USERS_COL_USERNAME
            + " text unique not null, " + USERS_COL_PASSWORD + " text not null);";
    private static final String X_ITEMS_TABLE_CREATE = "create table " + X_ITEMS_TABLE_NAME
            + "(" + X_ITEMS_COL_ITEM_ID + " integer primary key autoincrement, " + X_ITEMS_COL_X_ID
            + " integer not null, " + X_ITEMS_COL_ITEM_NAME + " text not null);";


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(X_ITEMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ X_ITEMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        onCreate(db);
    }
}
