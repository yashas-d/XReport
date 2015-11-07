package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
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
        boolean fromCreate = getIntent().getBooleanExtra("fromCreate",false);
        Toast.makeText(mContext, "coming from on create " + fromCreate, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_edit_expense);
        ListView expenseItemsList;
        expenseItemsList = (ListView) findViewById(R.id.expenseItemsList);
        List<String> list = new ArrayList<>();
        list.add("Hotel Stay");
        list.add("Travel");
        list.add("Breakfast");
        list.add("Lunch");

        //
        expensedatasource = new ExpenseDataSource(this);
        expensedatasource.open();
        expenseitemdatasource = new ExpenseItemDataSource(this);
        expenseitemdatasource.open();
        //datasource.deleteAllExpenseItems();

        //datasource.createExpenseItem("Hotel Stay");
        //datasource.createExpenseItem("Lunch");
        //datasource.createExpenseItem("Travel");
        //datasource.createExpenseItem("Dinner");

        //
        Expense newExpense = expensedatasource.createExpense("dummy","dummy",10,"dummy","dummy");
        final int expenseId = newExpense.getExpenseId();
        //
        List<ExpenseItem> values = expenseitemdatasource.getAllExpenseItems();
        for(int i = 0; i < values.size(); i++){
            Log.i(TAG, values.get(i).toString());
        }
        //
        //ExpenseAdapter adapter/Expense = new ExpenseAdapter(this,list);
        final ArrayAdapter adapterExpense = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, values); //list,values
        expenseItemsList.setAdapter(adapterExpense);
        expenseItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Clicked item " + position, Toast.LENGTH_LONG).show();
                Intent editExpenseItemIntent = new Intent(mContext, EditExpenseItemActivity.class);
                editExpenseItemIntent.putExtra("fromCreate", false);
                editExpenseItemIntent.putExtra("expenseId",expenseId);
                editExpenseItemIntent.putExtra("expenseItemId",position+1);
                startActivity(editExpenseItemIntent);
            }
        });

        //references to button.
        final ImageButton addbutton = (ImageButton) findViewById(R.id.add_expense_item);
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent editExpenseItemIntent = new Intent(mContext, EditExpenseItemActivity.class);
                editExpenseItemIntent.putExtra("fromCreate", true);
                editExpenseItemIntent.putExtra("expenseId",expenseId);
                startActivity(editExpenseItemIntent);
            }
        });

        //references to button.
        final ImageButton savebutton = (ImageButton) findViewById(R.id.save_expense);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                mContext.finish();
            }
        });
        //

        if(fromCreate){

        }
    }

}
