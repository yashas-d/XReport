package edu.osu.settyblue.xreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MakePhotoActivity extends Activity {
    final static String DEBUG_TAG = "MakePhotoActivity";
    private Camera camera;
    private int cameraId = 0;
    private CameraSurfaceView mCameraSurfaceView;
    private ImageButton mImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_surface_view);

        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            cameraId = findBackFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(this, "No back facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Opening Camera", Toast.LENGTH_LONG).show();
                try {
                    camera = Camera.open(cameraId);
                } catch (Exception e) {
                    // Camera is not available (in use or does not exist)
                    Log.e(DEBUG_TAG, "Camera not available!");
                    e.printStackTrace();
                }

                Log.d(DEBUG_TAG, "Camera opened");

                // Create our Preview view and set it as the content of our activity.
                mCameraSurfaceView = new CameraSurfaceView(this, camera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mCameraSurfaceView);

                // Get Record Button and set icon.
                mImageButton = (ImageButton) findViewById(R.id.button);
                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton imgButton = (ImageButton) v;

                        camera.takePicture(null, null,new PhotoHandler(getApplicationContext()));

                    }
                });
            }
        }
    }



    private int findBackFacingCamera() {
        int cameraId = -1;
        // Search for the back facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
}
