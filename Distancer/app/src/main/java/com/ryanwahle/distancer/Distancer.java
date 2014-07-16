package com.ryanwahle.distancer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Distancer extends Activity implements LocationListener {

    private TextView myTextView = null;
    private Location oldLocation = null;
    private float totalMetersDistance = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distancer);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);

        myTextView = (TextView) findViewById(R.id.textView);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMetersToTotalDistance(float meters) {
        totalMetersDistance = totalMetersDistance + meters;
        updateTotalMiles();
    }

    private void updateTotalMiles() {
        String totalMilesString = String.format("%.2f", totalMetersDistance * 0.00062137);

        //Toast.makeText(getBaseContext(), totalMilesString, Toast.LENGTH_LONG).show();
        myTextView.setText(totalMilesString);
    }
}
