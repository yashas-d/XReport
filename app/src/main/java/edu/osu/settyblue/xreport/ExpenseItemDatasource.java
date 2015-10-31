package edu.osu.settyblue.xreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakshith on 10/30/2015.
 */
public class ExpenseItemDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.X_ITEMS_COL_ITEM_ID,MySQLiteHelper.X_ITEMS_COL_X_ID,
            MySQLiteHelper.X_ITEMS_COL_ITEM_NAME};

    public ExpenseItemDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public ExpenseItem createExpenseItem(String ExpenseItemName){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.X_ITEMS_COL_X_ID,10);
        values.put(MySQLiteHelper.X_ITEMS_COL_ITEM_NAME, ExpenseItemName);

        long insertId = database.insert(MySQLiteHelper.X_ITEMS_TABLE_NAME,null,values);
        Cursor cursor= database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        ExpenseItem newExpenseItem = cursorToExpenseItem(cursor);
        cursor.close();
        return newExpenseItem;
    }

    public List<ExpenseItem> getAllExpenseItems(){
        List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
        Cursor cursor = database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ExpenseItem expenseItem = cursorToExpenseItem(cursor);
            expenseItems.add(expenseItem);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseItems;
    }

    public void deleteAllExpenseItems(){
        database.execSQL("delete from "+MySQLiteHelper.X_ITEMS_TABLE_NAME);
    }
    private ExpenseItem cursorToExpenseItem(Cursor cursor){
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setExpenseItemId(cursor.getInt(0));
        expenseItem.setExpenseId(cursor.getInt(1));
        expenseItem.setItemName(cursor.getString(2));
        return expenseItem;
    }
}
