package com.locationservice.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.locationservice.myapplication.services.AppConstants;
import com.locationservice.myapplication.services.Constants;
import com.locationservice.myapplication.services.CurrentLocation;
import com.locationservice.myapplication.services.ForegroundServiceForLocation;
import com.locationservice.myapplication.services.ForegroundServiceForLocationUpdate;
import com.locationservice.myapplication.services.GpsUtils;
import com.locationservice.myapplication.utility.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnServices;
    private Button btnServicesForground;
    private Button btnStopForground;
    private Button btnStartForGroundLocation;
    private Button btnStopForGroundLocation;
    private boolean isGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initComponenet();


    }

    private void initComponenet() {


        btnServices = findViewById(R.id.btn_start);
        btnServicesForground = findViewById(R.id.btn_start_forgroound);
        btnStopForground = findViewById(R.id.btn_stop_forgroound);
        btnStopForGroundLocation = findViewById(R.id.btn_stop_forground_location_service);

        btnServices.setOnClickListener(this);
        btnServicesForground.setOnClickListener(this);
        btnStopForground.setOnClickListener(this);

        btnStartForGroundLocation = findViewById(R.id.btn_start_forground_location_service);
        btnStartForGroundLocation.setOnClickListener(this);
        btnStopForGroundLocation.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_start:

                if (checkGpsEnable()) {
                    setLocationPermision();
                } else {
                    Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_start_forgroound:
                setPermisionForGround();
                break;
            case R.id.btn_start_forground_location_service:
                setLocationPermisionForGround();
                break;
            case R.id.btn_stop_forgroound:
                Intent stopIntent = new Intent(MainActivity.this, ForegroundServiceForLocation.class);
                stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(stopIntent);
                break;
            case R.id.btn_stop_forground_location_service:
                Intent stopIntentLocation = new Intent(MainActivity.this, ForegroundServiceForLocationUpdate.class);
                stopIntentLocation.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(stopIntentLocation);
                break;
        }
    }

    private void setLocationPermision() {
        if (Build.VERSION.SDK_INT < 23) {

            Intent startServices = new Intent(MainActivity.this, CurrentLocation.class);
            startService(startServices);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                startService(new Intent(getApplicationContext(), CurrentLocation.class));

            } else {
                Utils.checkPermitionLocation(MainActivity.this);
            }
        }

    }


    public boolean checkGpsEnable() {

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
        return isGPS;
    }

    private void setPermisionForGround() {
        if (Build.VERSION.SDK_INT < 23) {

            Intent startIntent = new Intent(MainActivity.this, ForegroundServiceForLocation.class);
            startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            startService(startIntent);
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Intent startIntent = new Intent(MainActivity.this, ForegroundServiceForLocation.class);
                startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startIntent);

            } else {
                Utils.checkPermitionLocation(MainActivity.this);
            }
        }

    }

    private void setLocationPermisionForGround() {
        if (Build.VERSION.SDK_INT < 23) {

            Intent startIntent = new Intent(MainActivity.this, ForegroundServiceForLocationUpdate.class);
            startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            startService(startIntent);
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Intent startIntent = new Intent(MainActivity.this, ForegroundServiceForLocationUpdate.class);
                startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startIntent);

            } else {
                Utils.checkPermitionLocation(MainActivity.this);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 1050: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startService(new Intent(getApplicationContext(), CurrentLocation.class));

                } else {
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }


}
