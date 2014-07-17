package com.ryanwahle.distancer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Distancer extends Activity implements LocationListener, Button.OnClickListener {

    private TextView myTextView = null;
    private Button buttonStartStopGPS = null;

    private Location oldLocation = null;
    private LocationManager locationManager = null;

    private float totalMetersDistance = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distancer);

        /*
            Setup the location manager to enable us to start and stop
            receiving GPS updates.
        */
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /*
            Setup the links to the interface
        */

        myTextView = (TextView) findViewById(R.id.textView);

        buttonStartStopGPS = (Button) findViewById(R.id.buttonStartStopGPS);
        buttonStartStopGPS.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String buttonStartStopGPSString = buttonStartStopGPS.getText().toString();

        if (buttonStartStopGPSString.compareTo("Start Recording") == 0) {
            // Start receiving GPS updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);

            // Reset the total miles counter
            totalMetersDistance = 0.0f;
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

    @Override
    public void onLocationChanged(Location location) {

        float distanceBetweenCurrentAndOldLocation = 0;
        if (oldLocation != null) {
            distanceBetweenCurrentAndOldLocation = location.distanceTo(oldLocation);
            addMetersToTotalDistance(distanceBetweenCurrentAndOldLocation);
        }

        oldLocation = location;

        String str = "" + location.getLatitude() + "," + location.getLongitude() + " Dist: " + distanceBetweenCurrentAndOldLocation + "\n";


        //myTextView.append(str);

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

    private void updateTotalMiles() {
        String totalMilesString = String.format("%.2f", totalMetersDistance * 0.00062137);

        //Toast.makeText(getBaseContext(), totalMilesString, Toast.LENGTH_LONG).show();
        myTextView.setText(totalMilesString);

        //MediaPlayer niceJobMileMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.niceJobMile);
        //niceJobMileMediaPlayer.start();
        //niceJobMileMediaPlayer.release();
        //niceJobMileMediaPlayer = null;
    }
}
