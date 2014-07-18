/*
    Author:     Ryan Wahle

    Project:    Distancer

    Package:    com.ryanwahle.distancer

    File:       Distancer.java

    Purpose:    This activity is the only interface displayed to the user. It
                allows the user to start and stop recording the distance traveled
                in miles and also alerts the user when each mile has achieved and
                also stops GPS recordings when the battery level reaches less
                than 10%.

 */


package com.ryanwahle.distancer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Distancer extends Activity implements LocationListener, Button.OnClickListener {

    private TextView totalMilesTextView = null;
    private Button buttonStartStopGPS = null;

    private Location oldLocation = null;
    private LocationManager locationManager = null;

    private IntentFilter batteryIntentFilter = null;
    private Intent batteryLevelIntent = null;

    private float totalMetersDistance = 0.0f;
    private int nextMileMarkerInt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distancer);

        /*
            Setup the battery monitoring intent
        */
        batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryLevelIntent = getBaseContext().registerReceiver(null, batteryIntentFilter);

        /*
            Setup the location manager to enable us to start and stop
            receiving GPS updates.
        */
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /*
            Setup the links to the interface
        */

        totalMilesTextView = (TextView) findViewById(R.id.totalMilesTextView);

        buttonStartStopGPS = (Button) findViewById(R.id.buttonStartStopGPS);
        buttonStartStopGPS.setOnClickListener(this);
    }

    /*
        This is called when the user clicks Start or Stop Recording button.
        It starts and stops the GPS and updates the button to either Start
        or Stop Recording.
    */
    @Override
    public void onClick(View view) {
        String buttonStartStopGPSString = buttonStartStopGPS.getText().toString();

        if (buttonStartStopGPSString.compareTo("Start Recording") == 0) {
            // Start receiving GPS updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);

            // Reset the total miles counter
            oldLocation = null;
            totalMetersDistance = 0.0f;
            nextMileMarkerInt = 1;
            updateTotalMiles();

            // Change the Start/Stop button to "Stop Recording" and change
            // background color to red.
            buttonStartStopGPS.setText("Stop Recording");
            buttonStartStopGPS.setTextColor(getResources().getColor(android.R.color.white));
            buttonStartStopGPS.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            // Stop the recording
            locationManager.removeUpdates(this);

            // Change the Start/Stop button to "Start Recording" and change
            // background color to green.
            buttonStartStopGPS.setText("Start Recording");
            buttonStartStopGPS.setTextColor(getResources().getColor(android.R.color.black));
            buttonStartStopGPS.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }

    /*
        This is the method that gets called when the system reports a new GPS
        location. It gets the distance between the last two GPS points
        and adds the distance to the total distance traveled (in meters).

        It also checks the battery levels and stops the GPS if the battery
        is less than 10%.
    */
    @Override
    public void onLocationChanged(Location location) {

        float distanceBetweenCurrentAndOldLocation = 0;
        if (oldLocation != null) {
            distanceBetweenCurrentAndOldLocation = location.distanceTo(oldLocation);
            addMetersToTotalDistance(distanceBetweenCurrentAndOldLocation);
        }

        oldLocation = location;

        /*
            Check the battery level and if below 10% then stop
            recording distance from GPS and alert the user!
        */
        if (getBatteryLevel() < 0.10) {
            buttonStartStopGPS.callOnClick();
            Toast.makeText(getBaseContext(), "You battery is too low. We have stopped the recording for you!", Toast.LENGTH_LONG).show();
        }

        //String str = "" + location.getLatitude() + "," + location.getLongitude() + " Dist: " + distanceBetweenCurrentAndOldLocation + "\n";
        //Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Do Nothing
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Do nothing
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing
    }

    private void addMetersToTotalDistance(float meters) {
        totalMetersDistance = totalMetersDistance + meters;
        updateTotalMiles();
    }

    /*
        This updates the user interface and then converts the total meters
        into into miles and displays it to the user.

        It also plays a sound to let the user know that they have traveled another mile.
    */
    private void updateTotalMiles() {
        float totalMetersToMilesCalculationFloat = totalMetersDistance * 0.00062137f;
        String totalMilesString = String.format("%.2f", totalMetersToMilesCalculationFloat);

        //Toast.makeText(getBaseContext(), totalMetersToMilesCalculationFloat + "", Toast.LENGTH_LONG).show();
        totalMilesTextView.setText(totalMilesString);

        if (totalMetersToMilesCalculationFloat >= nextMileMarkerInt) {
            nextMileMarkerInt = nextMileMarkerInt + 1;
            MediaPlayer niceJobMileMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.sound_mile);
            niceJobMileMediaPlayer.start();
        }
    }

    /*
        Retrieve the battery level from the system and return it.
    */
    private float getBatteryLevel() {
        int batteryLevel = batteryLevelIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = batteryLevelIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        //Toast.makeText(getBaseContext(), "Battery: " + batteryLevel / (float)batteryScale, Toast.LENGTH_LONG).show();
        return batteryLevel / (float)batteryScale;
    }
}
