package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i("RegisterActivity", "onCreate called.");
        mContext = this;
        //references to button.
        final Button button = (Button) findViewById(R.id.confirm_register_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i("RegisterActivity", "onResume called.");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("RegisterActivity", "onPause called.");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("RegisterActivity", "onStop called.");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i("RegisterActivity", "onRestart called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("RegisterActivity", "onDestroy called.");
    }
}
