package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity {
    private static final String TAG = "EditExpenseActivity";
    EditExpenseActivity mContext;
    private ExpenseItemDataSource expenseitemdatasource;
    private ExpenseDataSource expensedatasource;
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
            Expense newExpense = expensedatasource.createExpense("dummy", "dummy", 10,"not submitted", "dummy");
            expenseId = newExpense.getExpenseId();
        }
        final int xid = Math.max(expenseId, currentExpenseId);
        //

        //List<ExpenseItem> values = expenseitemdatasource.getAllExpenseItems();
        final List<ExpenseItem> values = expenseitemdatasource.getExpenseItems(xid);
        for(int i = 0; i < values.size(); i++){
            Log.i(TAG, values.get(i).toString());
        }
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
                //get the message body ready.
                StringBuffer messageBody = new StringBuffer();
                messageBody.append(expenseEventName.getText().toString()+'\n');
                messageBody.append("hello World");
                //
                // Code below to send email.
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "settyblue@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Submitting Expense Report For "+expenseEventName.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_TEXT, messageBody.toString());
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
}
