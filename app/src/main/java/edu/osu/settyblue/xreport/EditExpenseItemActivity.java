package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseItemActivity extends AppCompatActivity {

    EditExpenseItemActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_item);
        mContext = this;
        //
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapterSortItems = ArrayAdapter.createFromResource(this,
                R.array.categories_list, android.R.layout.simple_spinner_item);
        adapterSortItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSortItems);
        //
        //
        Spinner spinner2 = (Spinner) findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapterSortItems2 = ArrayAdapter.createFromResource(this,
                R.array.currency_list, android.R.layout.simple_spinner_item);
        adapterSortItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterSortItems2);
        //

        //references to button.
        final Button savebutton = (Button) findViewById(R.id.save_expense_item);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                mContext.finish();
            }
        });
        //
    }

}
