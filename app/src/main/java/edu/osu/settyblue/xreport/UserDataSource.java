package edu.osu.settyblue.xreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rakshith on 10/30/2015.
 */
public class UserDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.USERS_COL_ID,MySQLiteHelper.USERS_COL_USERNAME,
                MySQLiteHelper.USERS_COL_PASSWORD, MySQLiteHelper.USERS_COL_EMAIL, MySQLiteHelper.USERS_COL_APPROVER};
    public UserDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public User createUser(String Username, String Password,String EmailId,String ApproverName){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.USERS_COL_USERNAME,Username);
        values.put(MySQLiteHelper.USERS_COL_PASSWORD,Password);
        values.put(MySQLiteHelper.USERS_COL_EMAIL,EmailId);
        values.put(MySQLiteHelper.USERS_COL_APPROVER,ApproverName);

        long insertId = database.insert(MySQLiteHelper.USERS_TABLE_NAME,null,values);
        Cursor cursor = database.query(MySQLiteHelper.USERS_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public User getUser(String Username, String Password){
        User user = new User();
        Cursor cursor;
        try{
            cursor = database.query(MySQLiteHelper.USERS_TABLE_NAME, allColumns, MySQLiteHelper.USERS_COL_USERNAME+" = '"+Username+"'",
                    null, null, null, null);
        }catch(Exception ex){
            return null;
        }
        if(cursor.moveToFirst()){
            user = cursorToUser(cursor);
            cursor.close();
            return user;
        }else{
            return null;
        }

    }

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setEmail(cursor.getString(3));
        user.setApprover(cursor.getString(4));
        return user;
    }
}
