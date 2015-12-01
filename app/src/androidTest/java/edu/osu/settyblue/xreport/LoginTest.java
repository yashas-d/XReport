package edu.osu.settyblue.xreport;

import edu.osu.settyblue.xreport.LoginActivity;
import edu.osu.settyblue.xreport.RegisterActivity;
import edu.osu.settyblue.xreport.UserDataSource;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rakshith on 11/29/2015.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    private LoginActivity mLoginActivity;
    private TextView mView;
    private EditText username;
    private EditText password;
    private MySQLiteHelper myDB;
    private Button camera_button;

    public LoginTest(){
        super("edu.osu.settyblue.xreport",LoginActivity.class);
        //super();
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLoginActivity = this.getActivity();
        RenamingDelegatingContext context = new RenamingDelegatingContext(this.getActivity().mContext, "test_");
        myDB = new MySQLiteHelper(context);
        camera_button = (Button) mLoginActivity.findViewById(R.id.login_button);
        //this.getInstrumentation().callActivityOnStart(mLoginActivity);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mView = (TextView) mLoginActivity.findViewById(R.id.login_button);
                username = (EditText) mLoginActivity.findViewById(R.id.username);
                password = (EditText) mLoginActivity.findViewById(R.id.password);
                username.setText("Ashish");
                password.setText("HelloWorld");
            }
        });

    }

    @Test
    public void testInsertion() {
        myDB.getWritableDatabase().execSQL(" insert into users values ('20000','Ashish','HelloWorld','ashish@gmail.com','Elon Musk')");
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(HomeActivity.
                class.getName(), null, false);
        String[] columns = {"username"};
        int count = myDB.getReadableDatabase().query("users",columns," username = 'Ashish'",null,null,null,null).getCount();
        assertEquals("Ashish", (String) username.getText().toString());
        assertEquals("HelloWorld", (String) password.getText().toString());
        assertEquals(1, count);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                camera_button.performClick();
            }
        });
        Activity activity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        if(activity instanceof RegisterActivity){
            activity.finish();
        }else{
            assertNotNull("Home Activity not launched.",activity);
        }
    }

    @Test
    public void testDuplicteUser(){
        try{
            myDB.getWritableDatabase().execSQL(" insert into users values ('20000','Ashish','HelloWorld','ashish@gmail.com','Elon Musk')");
            myDB.getWritableDatabase().execSQL(" insert into users values ('20001','Ashish','HelloWorld','ashish1@gmail.com','Elon Musk')");
        }catch (Exception ex){
            //org.junit.Assert.assertTrue("condition passed : "+ex.toString(),true);
            //org.junit.Assert.fail("failed");
        }
        //org.junit.Assert.fail("failed");
    }


}
