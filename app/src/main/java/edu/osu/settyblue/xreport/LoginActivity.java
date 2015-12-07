package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    private UserDataSource mUserDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        super.onResume();
        Log.i("LoginActivity", "onCreate called.");

        mUserDataSource = new UserDataSource(this);
        mUserDataSource.open();

        /*
        if(!isNetworkAvailable()){
            Toast.makeText(mContext, "Please switch on the Internet. ", Toast.LENGTH_LONG).show();
        }*/
        //references to userid and password fields.
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        //references to button.
        final Button loginbutton = (Button) findViewById(R.id.login_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Intent homeIntent = new Intent(mContext, HomeActivity.class);
                //startActivity(homeIntent);

                if(mUserDataSource.getUser(username.getText().toString(),password.getText().toString()) != null){
                    Intent homeIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(homeIntent);
                }else{
                    Toast.makeText(mContext, "Username and password doesnt match. ", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        final Button registerbutton = (Button) findViewById(R.id.register_button);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent registerIntent = new Intent(mContext,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("LoginActivity", "onResume called.");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("LoginActivity", "onPause called.");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("LoginActivity", "onStop called.");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i("LoginActivity", "onRestart called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("LoginActivity", "onDestroy called.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
