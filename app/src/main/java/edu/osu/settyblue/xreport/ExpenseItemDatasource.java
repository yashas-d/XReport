package edu.osu.settyblue.xreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakshith on 10/30/2015.
 */
public class ExpenseItemDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.X_ITEMS_COL_ITEM_ID,MySQLiteHelper.X_ITEMS_COL_X_ID,
            MySQLiteHelper.X_ITEMS_COL_ITEM_NAME,MySQLiteHelper.X_ITEMS_COL_CATEGORY,MySQLiteHelper.X_ITEMS_COL_AMOUNT,
            MySQLiteHelper.X_ITEMS_COL_CURRENCY,MySQLiteHelper.X_ITEMS_COL_DATE,MySQLiteHelper.X_ITEMS_COL_VENDOR,
            MySQLiteHelper.X_ITEMS_COL_COMMENTS, MySQLiteHelper.X_ITEMS_COL_LAT, MySQLiteHelper.X_ITEMS_COL_LNG};

    public ExpenseItemDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public ExpenseItem createExpenseItem(int ExpenseId, String ExpenseItemName,String Category, float Amount,String Currency,
                                         String date, String Vendor, String Comments, Double Latitude, Double Longitude){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.X_ITEMS_COL_X_ID,ExpenseId);
        values.put(MySQLiteHelper.X_ITEMS_COL_ITEM_NAME, ExpenseItemName);
        values.put(MySQLiteHelper.X_ITEMS_COL_CATEGORY, Category);
        values.put(MySQLiteHelper.X_ITEMS_COL_AMOUNT, Amount);
        values.put(MySQLiteHelper.X_ITEMS_COL_CURRENCY, Currency);
        values.put(MySQLiteHelper.X_ITEMS_COL_DATE,date);
        values.put(MySQLiteHelper.X_ITEMS_COL_VENDOR, Vendor);
        values.put(MySQLiteHelper.X_ITEMS_COL_COMMENTS,Comments);
        values.put(MySQLiteHelper.X_ITEMS_COL_LAT,Latitude);
        values.put(MySQLiteHelper.X_ITEMS_COL_LNG,Longitude);
        long insertId = database.insert(MySQLiteHelper.X_ITEMS_TABLE_NAME,null,values);
        Cursor cursor= database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        ExpenseItem newExpenseItem = cursorToExpenseItem(cursor);
        cursor.close();
        return newExpenseItem;
    }

    public ExpenseItem queryExpenseItem(int ExpenseId, int ExpenseItemId){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.X_ITEMS_COL_X_ID,ExpenseId);
        values.put(MySQLiteHelper.X_ITEMS_COL_ITEM_ID,ExpenseItemId);

        Cursor cursor= database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns,
                MySQLiteHelper.X_ITEMS_COL_X_ID+" = "+ExpenseId+" AND "+ MySQLiteHelper.X_ITEMS_COL_ITEM_ID+" = "+ExpenseItemId,
                null,null,null,null);
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

    public List<ExpenseItem> getExpenseItems(int ExpenseId){
        List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
        Cursor cursor = database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns,
                MySQLiteHelper.X_ITEMS_COL_X_ID+" = "+ExpenseId,null, null, null,MySQLiteHelper.X_ITEMS_COL_DATE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ExpenseItem expenseItem = cursorToExpenseItem(cursor);
            expenseItems.add(expenseItem);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseItems;
    }

    public void updateLocation(int ExpenseId, int ExpenseItemId, double Latitude, double Longitude){
        ContentValues values = new ContentValues();
        //values.put(MySQLiteHelper.X_ITEMS_COL_X_ID,ExpenseId);
        //values.put(MySQLiteHelper.X_ITEMS_COL_ITEM_ID,ExpenseItemId);
        values.put(MySQLiteHelper.X_ITEMS_COL_LAT,Latitude);
        values.put(MySQLiteHelper.X_ITEMS_COL_LNG, Longitude);

        database.update(MySQLiteHelper.X_ITEMS_TABLE_NAME, values,
                MySQLiteHelper.X_ITEMS_COL_X_ID + " = " + ExpenseId + " AND " + MySQLiteHelper.X_ITEMS_COL_ITEM_ID + " = " + ExpenseItemId, null);

    }

    public float getTotalExpenseAmount(int ExpenseId){
        float totalAmount = 0;
        Cursor cursor = database.query(MySQLiteHelper.X_ITEMS_TABLE_NAME, allColumns,
                MySQLiteHelper.X_ITEMS_COL_X_ID+" = "+ExpenseId,null, null, null,MySQLiteHelper.X_ITEMS_COL_DATE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ExpenseItem expenseItem = cursorToExpenseItem(cursor);
            totalAmount += expenseItem.getAmount();
            cursor.moveToNext();
        }
        cursor.close();
        return totalAmount;
    }
    public void deleteAllExpenseItems(){
        database.execSQL("delete from "+MySQLiteHelper.X_ITEMS_TABLE_NAME);
    }
    private ExpenseItem cursorToExpenseItem(Cursor cursor){
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setExpenseItemId(cursor.getInt(0));
        expenseItem.setExpenseId(cursor.getInt(1));
        expenseItem.setItemName(cursor.getString(2));
        expenseItem.setCategory(cursor.getString(3));
        expenseItem.setAmount(cursor.getFloat(4));
        expenseItem.setCurrency(cursor.getString(5));
        expenseItem.setDate(cursor.getString(6));
        expenseItem.setVendor(cursor.getString(7));
        expenseItem.setComments(cursor.getString(8));
        expenseItem.setLattitude(cursor.getDouble(9));
        expenseItem.setLongitude(cursor.getDouble(10));
        return expenseItem;
    }
}
