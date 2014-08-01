/*
    Author:     Ryan Wahle

    Project:    Get Me Home

    Package:    com.ryanwahle.getmehome

    File:       WebAppInterface.java

    Purpose:    This class provides an interface between the GUI's javascript
                code and the native Android code.

 */

package com.ryanwahle.getmehome;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    SharedPreferences sharedPreferences = null;

    private static String PREF_APP = "GetMeHome";
    private static String PREF_STREET_ADDRESS = "street_address";
    private static String PREF_CITY = "city";
    private static String PREF_STATE = "state";
    private static String PREF_ZIP_CODE = "zip_code";

    WebAppInterface(Context c) {
        mContext = c;

        sharedPreferences = mContext.getSharedPreferences(PREF_APP, 0);
    }

    /*
        Check to see if the user has supplied all fields for the address and return
        true or false.
    */
    @JavascriptInterface
    public boolean isDestinationSet() {
        if (sharedPreferences.getString(PREF_STREET_ADDRESS, "").isEmpty() || sharedPreferences.getString(PREF_CITY, "").isEmpty() || sharedPreferences.getString(PREF_STATE, "").isEmpty() || sharedPreferences.getString(PREF_ZIP_CODE, "").isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /*
        Creates an intent to start Google Maps and sends the destination address with
        that intent so Maps can provide directions.
    */
    @JavascriptInterface
    public void loadMapApplication() {
        String destinationAddressForUriString = "http://maps.google.com/maps?daddr=";
        destinationAddressForUriString = destinationAddressForUriString + sharedPreferences.getString(PREF_STREET_ADDRESS, "") + "+";
        destinationAddressForUriString = destinationAddressForUriString + sharedPreferences.getString(PREF_CITY, "") + "+";
        destinationAddressForUriString = destinationAddressForUriString + sharedPreferences.getString(PREF_STATE, "") + "+";
        destinationAddressForUriString = destinationAddressForUriString + sharedPreferences.getString(PREF_ZIP_CODE, "");

        Uri mapWithDestinationUri = Uri.parse(destinationAddressForUriString);
        Intent mapWithDestinationIntent = new Intent(Intent.ACTION_VIEW, mapWithDestinationUri);
        mContext.startActivity(mapWithDestinationIntent);
    }

    /*
        Allows the javascript code to display a Toast.
    */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
    }

    /*
        The rest of the methods provide setters and getters to either
        set or retrieve the address from the SharedPreferences.
    */
    @JavascriptInterface
    public void setDestinationStreetAddress(String stringToSave) {
        Log.v("Saved", "Street Address: " + stringToSave);

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(PREF_STREET_ADDRESS, stringToSave);
        sharedPreferencesEditor.commit();
    }

    @JavascriptInterface
    public String getDestinationStreetAddress() {
        Log.v("Get", "Street Address: " + sharedPreferences.getString(PREF_STREET_ADDRESS, ""));

        return sharedPreferences.getString(PREF_STREET_ADDRESS, "");
    }

    @JavascriptInterface
    public void setDestinationCity(String stringToSave) {
        Log.v("Saved", "City: " + stringToSave);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_APP, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(PREF_CITY, stringToSave);
        sharedPreferencesEditor.apply();
    }

    @JavascriptInterface
    public String getDestinationCity() {
        Log.v("Get", "City: " + sharedPreferences.getString(PREF_CITY, ""));

        return sharedPreferences.getString(PREF_CITY, "");
    }

    @JavascriptInterface
    public void setDestinationState(String stringToSave) {
        Log.v("Saved", "State: " + stringToSave);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_APP, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(PREF_STATE, stringToSave);
        sharedPreferencesEditor.apply();
    }

    @JavascriptInterface
    public String getDestinationState() {
        Log.v("Get", "State: " + sharedPreferences.getString(PREF_STATE, ""));

        return sharedPreferences.getString(PREF_STATE, "");
    }

    @JavascriptInterface
    public void setDestinationZipCode(String stringToSave) {
        Log.v("Saved", "Zip Code: " + stringToSave);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_APP, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(PREF_ZIP_CODE, stringToSave);
        sharedPreferencesEditor.apply();
    }

    @JavascriptInterface
    public String getDestinationZipCode() {
        Log.v("Get", "Zip Code: " + sharedPreferences.getString(PREF_ZIP_CODE, ""));

        return sharedPreferences.getString(PREF_ZIP_CODE, "");
    }

}
