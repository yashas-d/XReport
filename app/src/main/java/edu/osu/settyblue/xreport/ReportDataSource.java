package edu.osu.settyblue.xreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakshith on 11/20/2015.
 */
public class ReportDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.REPORTS_COL_ID, MySQLiteHelper.REPORTS_COL_X_ID, MySQLiteHelper.REPORTS_COL_REPORTED_BY,
            MySQLiteHelper.REPORTS_COL_REPORTED_TO, MySQLiteHelper.REPORTS_COL_APPROVAL_STATUS, MySQLiteHelper.REPORTS_COL_COMMENTS, MySQLiteHelper.REPORTS_COL_REPORTED_DATE,
            MySQLiteHelper.REPORTS_COL_PDF};

    public ReportDataSource (Context context){ dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Report createReport(int ExpenseId, String ReportedBy, String ReportedTo, String ApprovalStatus, String Comments, String date, String pdfLocation){

        Report newReport =  new Report();
        Cursor cursor1 = database.query(MySQLiteHelper.REPORTS_TABLE_NAME, allColumns,
                MySQLiteHelper.REPORTS_COL_X_ID + " = " + ExpenseId+ " AND "+MySQLiteHelper.REPORTS_COL_REPORTED_BY+" = \""+ReportedBy+"\"",
                null, null, null,MySQLiteHelper.REPORTS_COL_REPORTED_DATE);
        if (!cursor1.moveToFirst()){
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.REPORTS_COL_X_ID,ExpenseId);
            values.put(MySQLiteHelper.REPORTS_COL_REPORTED_BY,ReportedBy);
            values.put(MySQLiteHelper.REPORTS_COL_REPORTED_TO,ReportedTo);
            values.put(MySQLiteHelper.REPORTS_COL_APPROVAL_STATUS, ApprovalStatus);
            values.put(MySQLiteHelper.REPORTS_COL_COMMENTS,Comments);
            values.put(MySQLiteHelper.REPORTS_COL_REPORTED_DATE, date);
            values.put(MySQLiteHelper.REPORTS_COL_PDF,pdfLocation);
            long insertId = database.insert(MySQLiteHelper.REPORTS_TABLE_NAME,null,values);
            Cursor cursor= database.query(MySQLiteHelper.REPORTS_TABLE_NAME, allColumns, null,
                    null, null, null, null);
            cursor.moveToFirst();
            newReport = cursorToReport(cursor);
            cursor.close();
        }
        return newReport;
    }

    public List<Report> getReports(String ReportedBy){
        List<Report> Reports = new ArrayList<Report>();
        Cursor cursor = database.query(MySQLiteHelper.REPORTS_TABLE_NAME, allColumns,
                MySQLiteHelper.REPORTS_COL_REPORTED_BY+" = \""+ReportedBy+"\"",null, null, null,MySQLiteHelper.REPORTS_COL_REPORTED_DATE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Report report = cursorToReport(cursor);
            Reports.add(report);
            cursor.moveToNext();
        }
        cursor.close();
        return Reports;
    }

    private Report cursorToReport(Cursor cursor){
        Report report = new Report();
        report.setId(cursor.getInt(0));
        report.setExpenseid(cursor.getInt(1));
        report.setReportedby(cursor.getString(2));
        report.setReportedto(cursor.getString(3));
        report.setApprovalstatus(cursor.getString(4));
        report.setComments(cursor.getString(5));
        report.setDate(cursor.getString(6));
        report.setPdflocation(cursor.getString(7));
        return report;
    }
}
