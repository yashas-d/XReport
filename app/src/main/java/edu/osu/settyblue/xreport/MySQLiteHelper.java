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

    public static final String X_TABLE_NAME = "expense";
    public static final String X_COL_EXPENSE_ID = "expense_id";
    public static final String X_COL_EVENT_NAME = "event_name";
    public static final String X_COL_DATE = "date";
    public static final String X_COL_DAYS = "days";
    public static final String X_COL_SUBMITSTATUS = "submit_status";
    public static final String X_COL_COMMENTS = "comments";

    public static final String X_ITEMS_TABLE_NAME = "expenseitems";
    public static final String X_ITEMS_COL_ITEM_ID = "expense_item_id";
    public static final String X_ITEMS_COL_X_ID = "expense_id";
    public static final String X_ITEMS_COL_ITEM_NAME = "item_name";
    public static final String X_ITEMS_COL_CATEGORY = "category";
    public static final String X_ITEMS_COL_AMOUNT = "amount";
    public static final String X_ITEMS_COL_CURRENCY = "currency";
    public static final String X_ITEMS_COL_DATE = "date";
    public static final String X_ITEMS_COL_VENDOR = "vendor";
    public static final String X_ITEMS_COL_COMMENTS = "comments";
    public static final String X_ITEMS_COL_RECEIPT = "receipt";

    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COL_ID = "id";
    public static final String USERS_COL_USERNAME = "username";
    public static final String USERS_COL_PASSWORD = "password";
    public static final String USERS_COL_EMAIL = "email";
    public static final String USERS_COL_APPROVER = "approver_id";

    public static final String REPORTS_TABLE_NAME = "reports";
    public static final String REPORTS_COL_ID = "id";
    public static final String REPORTS_COL_REPORTED_BY = "reporter_id";
    public static final String REPORTS_COL_REPORTED_TO = "receiver_id";
    public static final String REPORTS_COL_APPROVAL_STATUS = "approval_status";
    public static final String REPORTS_COL_COMMENTS = "comments";
    public static final String REPORTS_COL_REPORTED_DATE = "date";
    public static final String REPORTS_COL_PDF = "pdf";

    private static final String USERS_TABLE_CREATE = "create table " + USERS_TABLE_NAME
            + "(" + USERS_COL_ID + " integer primary key autoincrement, " + USERS_COL_USERNAME
            + " text unique not null, " + USERS_COL_PASSWORD + " text not null, " + USERS_COL_EMAIL
            + " text unique not null, " + USERS_COL_APPROVER + " integer);";
    private static final String X_ITEMS_TABLE_CREATE = "create table " + X_ITEMS_TABLE_NAME
            + "(" + X_ITEMS_COL_ITEM_ID + " integer primary key autoincrement, " + X_ITEMS_COL_X_ID
            + " integer not null, " + X_ITEMS_COL_ITEM_NAME + " text not null, " + X_ITEMS_COL_CATEGORY
            + " text , " + X_ITEMS_COL_AMOUNT + " real not null, " + X_ITEMS_COL_CURRENCY
            + " text, " + X_ITEMS_COL_DATE + " date, " + X_ITEMS_COL_VENDOR
            + " text, " + X_ITEMS_COL_COMMENTS + " text, " + X_ITEMS_COL_RECEIPT + " blob); ";
    private static final String EXPENSE_TABLE_CREATE = "create table " + X_TABLE_NAME
            + "(" + X_COL_EXPENSE_ID + " integer primary key autoincrement, " + X_COL_EVENT_NAME
            + " text, " + X_COL_DATE + " date, " + X_COL_DAYS + " integer, " + X_COL_SUBMITSTATUS
            + " integer, " + X_COL_COMMENTS + " text);";
    private static final String REPORTS_TABLE_CREATE = "create table " + REPORTS_TABLE_NAME
            + "(" + REPORTS_COL_ID + " integer primary key autoincrement, " + REPORTS_COL_REPORTED_BY
            + " integer, " + REPORTS_COL_REPORTED_TO + " integer, " + REPORTS_COL_APPROVAL_STATUS
            + " integer, " + REPORTS_COL_COMMENTS + " text, " + REPORTS_COL_REPORTED_DATE
            + " date, " + REPORTS_COL_PDF + " blob);";

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
        db.execSQL(EXPENSE_TABLE_CREATE);
        db.execSQL(REPORTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ X_ITEMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ EXPENSE_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ REPORTS_TABLE_CREATE);
        onCreate(db);
    }
}
