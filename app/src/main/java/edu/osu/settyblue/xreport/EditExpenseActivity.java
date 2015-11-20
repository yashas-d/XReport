package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity {
    private static final String TAG = "EditExpenseActivity";
    private static String FILE = "c:/Users/raksh/Downloads/merged.pdf";

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    EditExpenseActivity mContext;
    private ExpenseItemDataSource expenseitemdatasource;
    private ExpenseDataSource expensedatasource;
    private ReportDataSource reportdatasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //Toast.makeText(mContext, "coming from on create " + fromCreate, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_edit_expense);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("ExpenseActivity", "onResume called.");

        //==========
        final boolean fromCreate = getIntent().getBooleanExtra("fromCreate", false);
        ListView expenseItemsList;
        int currentExpenseId = getIntent().getIntExtra("expenseId", 0);
        int expenseId = -1;
        expenseItemsList = (ListView) findViewById(R.id.expenseItemsList);
        //
        expensedatasource = new ExpenseDataSource(this);
        expensedatasource.open();
        expenseitemdatasource = new ExpenseItemDataSource(this);
        expenseitemdatasource.open();
        reportdatasource = new ReportDataSource(this);
        reportdatasource.open();
        //
        EditText expenseEventName = (EditText)findViewById(R.id.eventname);
        EditText expenseDate = (EditText)findViewById(R.id.expensedate);
        Button reportExpenseButton = (Button)findViewById(R.id.report_expense_button);

        if(!fromCreate){
            Expense expense = expensedatasource.queryExpense(currentExpenseId);
            expenseEventName.setText(expense.getName());
            expenseDate.setText(expense.getDate());
            if(expense.getSubmitStatus().equals("Submitted")){
                reportExpenseButton.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Expense newExpense = expensedatasource.createExpense("dummy", "dummy", 10,"Not Submitted", "dummy");
            expenseId = newExpense.getExpenseId();
        }
        final int xid = Math.max(expenseId, currentExpenseId);
        //

        //List<ExpenseItem> values = expenseitemdatasource.getAllExpenseItems();
        final List<ExpenseItem> values = expenseitemdatasource.getExpenseItems(xid);
        //
        final ArrayAdapter adapterExpense = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, values); //list,values
        expenseItemsList.setAdapter(adapterExpense);
        expenseItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Clicked item " + position, Toast.LENGTH_LONG).show();
                Intent editExpenseItemIntent = new Intent(mContext, EditExpenseItemActivity.class);
                editExpenseItemIntent.putExtra("fromCreate", false);
                editExpenseItemIntent.putExtra("expenseId", xid);
                //parent.getAdapter().getItem(position);
                //expenseItemsList.getItemAtPosition(position);

                editExpenseItemIntent.putExtra("expenseItemId",values.get(position).getExpenseItemId());
                startActivity(editExpenseItemIntent);
            }
        });

        //references to button.
        final ImageButton savebutton = (ImageButton) findViewById(R.id.save_expense);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText expenseEventName = (EditText) findViewById(R.id.eventname);
                EditText expenseDate = (EditText) findViewById(R.id.expensedate);
                EditText expenseComments = (EditText) findViewById(R.id.expense_comments);

                expensedatasource.updateExpense(xid, expenseEventName.getText().toString(), expenseDate.getText().toString(), 10,
                        "not submitted", expenseComments.getText().toString());
                mContext.finish();
            }
        });

        //references to button.
        final ImageButton addbutton = (ImageButton) findViewById(R.id.add_expense_item);
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent editExpenseItemIntent = new Intent(mContext, EditExpenseItemActivity.class);
                editExpenseItemIntent.putExtra("fromCreate", true);
                editExpenseItemIntent.putExtra("expenseId", xid);
                startActivity(editExpenseItemIntent);
            }
        });

        //total amount field.
        float totalExpenseAmount = expenseitemdatasource.getTotalExpenseAmount(xid);
        TextView totalAmountText = (TextView)findViewById(R.id.total_amount_text);
        totalAmountText.setText(getResources().getString(R.string.total_expense_amount) + " " + Float.toString(totalExpenseAmount));

        //expense report button.

        reportExpenseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //
                EditText expenseEventName = (EditText) findViewById(R.id.eventname);
                //write code to add it on reports table.
                reportdatasource.createReport(xid,"Rakshith","Larry Elison","No",expenseEventName.getText().toString(),"11/21/2015","file path");
                //

                //get the message body ready.
                StringBuffer messageBody = new StringBuffer();
                messageBody.append(expenseEventName.getText().toString()+'\n');
                messageBody.append("hello World");
                //TODO
                // Code below to send email.
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "settyblue@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Submitting Expense Report For "+expenseEventName.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_TEXT, messageBody.toString());

                /*create a pdf document here.
                PdfDocument report = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(new Rect(0, 0, 100, 100), 1).create();
                //start a page.
                PdfDocument.Page page = report.startPage(pageInfo);
                // draw something on the page
                ViewGroup view = (ViewGroup)getWindow().getDecorView();
                LinearLayout content = (LinearLayout)view.getChildAt(0);
                //View content = getContentView();
                content.draw(page.getCanvas());

                */

                try{
                    //
                    File sdDir = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File reportsFileDir = new File(sdDir, "Reports");
                    //File reportsFile = new File(sdDir, "expense_report.pdf");
                    String filename = reportsFileDir.getPath() + File.separator + "expense_report.pdf";
                    File reportsFile = new File(filename);
                    //
                    Document report = new Document();
                    PdfWriter.getInstance(report, new FileOutputStream(reportsFile));//reportsFile
                    report.open();
                    report.addAuthor("Rakshith Kunchum");
                    addTitlePage(report);
                    //addContent(report);
                    report.close();
                    Uri uri = Uri.fromFile(reportsFile);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                }catch (Exception ex){
                    ex.printStackTrace();
                    //
                    //
                }


                //addMetaData(report);
                //addTitlePage(report);
                //addContent(report);

                //finish creating a pdf document.

                //send email...
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
        //==========
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("ExpenseActivity", "onPause called.");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("ExpenseActivity", "onStop called.");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i("ExpenseActivity", "onRestart called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("ExpenseActivity", "onDestroy called.");
    }

    ///
    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " , //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
