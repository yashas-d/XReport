package edu.osu.settyblue.xreport;
import edu.osu.settyblue.xreport.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rakshith on 11/30/2015.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private RegisterActivity mRegisterActivity;
    private TextView mView;
    private EditText username;
    private EditText password;
    private EditText repeatpassword;
    private MySQLiteHelper myDB;

    public RegisterTest(){
        super("edu.osu.settyblue.xreport",RegisterActivity.class);
        //super();
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mRegisterActivity = this.getActivity();
        RenamingDelegatingContext context = new RenamingDelegatingContext(this.getActivity().mContext, "test_");
        myDB = new MySQLiteHelper(context);
        //this.getInstrumentation().callActivityOnStart(mLoginActivity);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mView = (TextView) mLoginActivity.findViewById(R.id.login_button);
                username = (EditText) mRegisterActivity.findViewById(R.id.register_username);
                password = (EditText) mRegisterActivity.findViewById(R.id.register_password);
                repeatpassword = (EditText)mRegisterActivity.findViewById(R.id.register_password);
                username.setText("Ashish");
                password.setText("HelloWorld");
            }
        });

    }
}

