package edu.osu.settyblue.xreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rakshith on 11/4/2015.
 */
public class ExpenseDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.X_COL_EXPENSE_ID,MySQLiteHelper.X_COL_EVENT_NAME,MySQLiteHelper.X_COL_DATE,
                                MySQLiteHelper.X_COL_DAYS,MySQLiteHelper.X_COL_SUBMITSTATUS,MySQLiteHelper.X_COL_COMMENTS};
    public ExpenseDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Expense createExpense(String EventName, String date, int days, String SubmitStatus, String Comments){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.X_COL_EVENT_NAME,EventName);
        values.put(MySQLiteHelper.X_COL_DATE,date);
        values.put(MySQLiteHelper.X_COL_DAYS,days);
        values.put(MySQLiteHelper.X_COL_SUBMITSTATUS,SubmitStatus);
        values.put(MySQLiteHelper.X_COL_COMMENTS,Comments);

        long insertId = database.insert(MySQLiteHelper.X_TABLE_NAME,null,values);
        Cursor cursor = database.query(MySQLiteHelper.X_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();
        return newExpense;
    }

    public Expense updateExpense(int ExpenseId, String EventName, String date, int days, String SubmitStatus, String Comments){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.X_COL_EVENT_NAME,EventName);
        values.put(MySQLiteHelper.X_COL_DATE,date);
        values.put(MySQLiteHelper.X_COL_DAYS,days);
        values.put(MySQLiteHelper.X_COL_SUBMITSTATUS,SubmitStatus);
        values.put(MySQLiteHelper.X_COL_COMMENTS,Comments);
        database.update(MySQLiteHelper.X_TABLE_NAME, values, MySQLiteHelper.X_COL_EXPENSE_ID + " = " + ExpenseId, null);
        Cursor cursor = database.query(MySQLiteHelper.X_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();
        return newExpense;
    }

    public Expense cursorToExpense(Cursor cursor){
        Expense newExpense = new Expense();
        newExpense.setExpenseId(cursor.getInt(0));
        newExpense.setName(cursor.getString(1));
        newExpense.setDate(cursor.getString(2));
        newExpense.setDays(cursor.getInt(3));
        newExpense.setSubmitStatus(cursor.getString(4));
        newExpense.setComments(cursor.getString(5));
        return newExpense;
    }
}
