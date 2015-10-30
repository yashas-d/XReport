package edu.osu.settyblue.xreport;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        ListView expenseItemsList;
        expenseItemsList = (ListView) findViewById(R.id.expenseItemsList);
        List<String> list = new ArrayList<>();
        list.add("Hotel Stay");
        list.add("Travel");
        list.add("Breakfast");
        list.add("Lunch");
        //ExpenseAdapter adapterExpense = new ExpenseAdapter(this,list);
        final ArrayAdapter adapterExpense = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        expenseItemsList.setAdapter(adapterExpense);
    }

}
