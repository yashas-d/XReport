package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    Context mContext;
    ListView expenseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        mContext = this;

        expenseList = (ListView) findViewById(R.id.expenseList);
        List<String> list = new ArrayList<>();
        list.add("HiPC Conference at California");
        list.add("Guest Lecture on Columbus Day");
        list.add("Hackathon Event at OSU");
        list.add("Industrial Visit");
        list.add("Computer Science Department Anniversary Celebrations");
       //ExpenseAdapter adapterExpense = new ExpenseAdapter(this,list);
        final ArrayAdapter adapterExpense = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        expenseList.setAdapter(adapterExpense);
        expenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Clicked item " + position, Toast.LENGTH_LONG).show();
                Intent editExpenseIntent = new Intent(mContext,EditExpenseActivity.class);
                startActivity(editExpenseIntent);
            }
        });

        //
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSortItems = ArrayAdapter.createFromResource(this,
                R.array.sort_items_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSortItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterSortItems);
        //
    }


}
