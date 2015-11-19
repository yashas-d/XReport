package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
/*import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;*/
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditExpenseItemActivity extends AppCompatActivity {

    EditExpenseItemActivity mContext;
    private ExpenseItemDataSource datasource;
    String finalfilename = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_item);
        mContext = this;
        final int expenseId = getIntent().getIntExtra("expenseId", 1);
        final int expenseItemId = getIntent().getIntExtra("expenseItemId", 1);
        boolean fromCreate = getIntent().getBooleanExtra("fromCreate", false);
        //Toast.makeText(mContext, "created an expense with id : " + expenseId, Toast.LENGTH_LONG).show();
        datasource = new ExpenseItemDataSource(this);
        datasource.open();
        //
        if(!fromCreate){
            EditText expenseItemName = (EditText)findViewById(R.id.expenseItemName);
            Spinner categorySpinner=(Spinner) findViewById(R.id.category_spinner);
            EditText expenseItemAmount = (EditText)findViewById(R.id.expenseItemAmount);
            Spinner currencySpinner=(Spinner) findViewById(R.id.currency_spinner);
            EditText expenseItemDate = (EditText)findViewById(R.id.expenseItemDate);
            EditText expenseItemVendor = (EditText)findViewById(R.id.expenseItemVendor);
            EditText expenseItemComments = (EditText)findViewById(R.id.expenseItemComments);
            ExpenseItem  currentExpenseItem = datasource.queryExpenseItem(expenseId,expenseItemId);
            expenseItemName.setText(currentExpenseItem.getItemName());
            categorySpinner.setSelection(2);
            expenseItemAmount.setText(Float.toString(currentExpenseItem.getAmount()));
            currencySpinner.setSelection(0);
            expenseItemDate.setText(currentExpenseItem.getDate());
            expenseItemVendor.setText(currentExpenseItem.getVendor());
            expenseItemComments.setText(currentExpenseItem.getComments());
        }
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

        //references to button.
        final Button savebutton = (Button) findViewById(R.id.save_expense_item);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText expenseItemName = (EditText) findViewById(R.id.expenseItemName);
                Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
                EditText expenseItemAmount = (EditText) findViewById(R.id.expenseItemAmount);
                Spinner currencySpinner = (Spinner) findViewById(R.id.currency_spinner);
                EditText expenseItemDate = (EditText) findViewById(R.id.expenseItemDate);
                EditText expenseItemVendor = (EditText) findViewById(R.id.expenseItemVendor);
                EditText expenseItemComments = (EditText) findViewById(R.id.expenseItemComments);

                datasource.createExpenseItem(expenseId, expenseItemName.getText().toString(), categorySpinner.getSelectedItem().toString(),
                        Float.parseFloat(expenseItemAmount.getText().toString()), currencySpinner.getSelectedItem().toString(),
                        expenseItemDate.getText().toString(), expenseItemVendor.getText().toString(), expenseItemComments.getText().toString());
                mContext.finish();
            }
        });
        //
        //references to camera button.
        final ImageButton camerabutton = (ImageButton) findViewById(R.id.camera_snapper);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivityForResult(new Intent(mContext, MakePhotoActivity.class),0);
                //mContext.finish();
            }
        });
        //
        final ImageButton gpsbutton = (ImageButton) findViewById(R.id.gps_button);
        gpsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                // check if enabled and if not send user to the GSP settings
                // Better solution would be to display a dialog and suggesting to
                // go to the settings
                if (!enabled) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }

                int lat=0;
                int lng=0;

                try {
                    LocationManager locationManager;
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    String provider;
                    Criteria criteria = new Criteria();
                    provider = locationManager.getBestProvider(criteria, false);

                    Location location = locationManager.getLastKnownLocation(provider);
                    // Initialize the location fields
                    if (location != null) {
                        System.out.println("Provider " + provider + " has been selected.");
                         lat = (int) (location.getLatitude());
                         lng = (int) (location.getLongitude());

                    } else {

                    }
                }catch (SecurityException se)
                {

                }finally {
                    System.out.println("Lat = "+lat+" Long = "+lng);
                }

                //Save lat and lng to database

                //startActivity(new Intent(mContext, ShowLocationActivity.class));


                //Call to get the map activity
                //startActivity(new Intent(mContext, MapsActivity.class));



                //mContext.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File pictureFileDir = getDir();
        String photoFile = "Picture_Taken" + ".jpg";
        String filename = pictureFileDir.getPath() + File.separator + photoFile;
        onResumeModified(filename);
    }

    public void onResumeModified(String filename){
        Log.i("EditExpenseItemActivity", "onResume modified called.");
        File imgFile = new File(filename);
        if(imgFile.exists()){
            Uri uri = Uri.fromFile(imgFile);
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageURI(uri);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".jpg";
                File pictureFileDir = getDir();
                //This is the final fully qualified file name to be stored to the database.
                String finalfilename = pictureFileDir.getPath() + File.separator + photoFile;
                File pictureFile = new File(finalfilename);
                imgFile.renameTo(pictureFile);
            } catch (Exception error) {
                Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename + "not saved: "
                        + error.getMessage());
            }
            Log.i("EditExpenseItemActivity", "decoding");
        }
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CameraAPIDemo");
    }
}
