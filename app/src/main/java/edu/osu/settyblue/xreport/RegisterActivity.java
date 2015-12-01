package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Context mContext;
    private UserDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i("RegisterActivity", "onCreate called.");
        mContext = this;
        datasource = new UserDataSource(this);
        datasource.open();
        //references to button.
        final Button registerButton = (Button) findViewById(R.id.confirm_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click.
                EditText username = (EditText)findViewById(R.id.register_username);
                EditText password = (EditText)findViewById(R.id.register_password);
                EditText confirmpassword = (EditText)findViewById(R.id.register_confirmPassword);
                EditText emailid = (EditText)findViewById(R.id.register_emailid);
                Spinner mySpinner=(Spinner) findViewById(R.id.approver_spinner);
                if(!(password.getText().toString().equals(confirmpassword.getText().toString()))){
                    Toast.makeText(mContext, "Passwords don't match.", Toast.LENGTH_LONG).show();
                    return;
                }

                datasource.createUser(username.getText().toString(),password.getText().toString(),
                        emailid.toString(),mySpinner.getSelectedItem().toString());

            }
        });

        //
        Spinner spinner = (Spinner) findViewById(R.id.approver_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSortItems = ArrayAdapter.createFromResource(this,
                R.array.approvers_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSortItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterSortItems);
        //
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
        datasource.close();
        super.onDestroy();
        Log.i("RegisterActivity", "onDestroy called.");
    }
}
