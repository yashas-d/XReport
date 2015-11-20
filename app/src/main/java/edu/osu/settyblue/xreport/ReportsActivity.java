package edu.osu.settyblue.xreport;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private ReportDataSource reportdatasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        reportdatasource = new ReportDataSource(this);
        reportdatasource.open();
        ListView reportsList;
        reportsList = (ListView) findViewById(R.id.reportsList);
        final List<Report> values = reportdatasource.getReports("Rakshith");
        //
        final ArrayAdapter adapterExpense = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, values); //list,values
        reportsList.setAdapter(adapterExpense);
    }

}
