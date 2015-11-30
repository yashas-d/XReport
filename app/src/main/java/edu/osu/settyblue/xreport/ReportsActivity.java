package edu.osu.settyblue.xreport;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private ReportDataSource reportdatasource;
    ReportsActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        mContext = this;
        reportdatasource = new ReportDataSource(this);
        reportdatasource.open();
        ListView reportsList;
        reportsList = (ListView) findViewById(R.id.reportsList);
        final List<Report> values = reportdatasource.getReports("Rakshith");
        //
        final ArrayAdapter adapterReports = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, values); //list,values
        reportsList.setAdapter(adapterReports);
        reportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Clicked item " + position, Toast.LENGTH_LONG).show();
                /*
                WebView mWebView=new WebView(ReportsActivity.this);
                Uri uri = Uri.fromFile(new File(values.get(position).getPdflocation()));
                mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+uri.toString());
                setContentView(mWebView);
                */
                File reportsFile = new File(values.get(position).getPdflocation());
                Uri uri = Uri.fromFile(reportsFile);//Uri uri = Uri.parse();
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setType("application/pdf");
                PackageManager pm = getPackageManager();
                List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
                if (activities.size() > 0) {
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Clicked item ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
