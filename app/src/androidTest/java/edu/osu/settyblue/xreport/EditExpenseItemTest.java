package edu.osu.settyblue.xreport;

import android.app.Instrumentation;
import android.hardware.camera2.CameraDevice;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.widget.Button;
import android.widget.EditText;

import junit.framework.TestResult;

import org.junit.Test;

/**
 * Created by rakshith on 11/30/2015.
 */
public class EditExpenseItemTest extends ActivityInstrumentationTestCase2<EditExpenseItemActivity> {

    private EditExpenseItemActivity mActivity;
    private Button camera_button;


    public EditExpenseItemTest(){
        super("edu.osu.settyblue.xreport",EditExpenseItemActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testCameraAction() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CameraDevice.class.getName(), null, false);
    }
}
