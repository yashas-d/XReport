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
        int id;
        switch (ApproverName){
            case "Larry Ellison":id=1;break;
            case "Indra Nooyi":id=2;break;
            case "Elon Musk":id=3;break;
            case "Larry Page":id=4;break;
            case "Sheryl Sandberg":id=5;break;
            case "Bill Gates":id=6;break;
            default:id=1;break;
        }
        values.put(MySQLiteHelper.USERS_COL_APPROVER,id);
        long insertId = database.insert(MySQLiteHelper.USERS_TABLE_NAME,null,values);
        Cursor cursor= database.query(MySQLiteHelper.USERS_TABLE_NAME, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }
}
