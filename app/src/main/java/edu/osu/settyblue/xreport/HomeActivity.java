package edu.osu.settyblue.xreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i("HomeActivity", "onCreate called.");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("HomeActivity", "onResume called.");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("HomeActivity", "onPause called.");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("HomeActivity", "onStop called.");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i("HomeActivity", "onRestart called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("HomeActivity", "onDestroy called.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
