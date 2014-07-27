/*
    Author:     Ryan Wahle

    Project:    Top Books Widget

    Package:    com.ryanwahle.topbookswidget

    File:       TopBooksConfigureActivity.java

    Purpose:    This activity is launched when the user first places
                a new widget on their home screen. It allows the user
                to enter their first and last name to add a little
                personalization to the widget.
 */

package com.ryanwahle.topbookswidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TopBooksConfigureActivity extends Activity {
    private int mAppWidgetId;

    /*
        This method is called when the activity is first created.
    */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_widget);

        Button buttonDone = (Button) this.findViewById(R.id.configure_buttonDone);
        buttonDone.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                buttonDoneClicked(view);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    /*
        When the user is done entering their first and last name, they click the
        done button which calls this method.
    */
    private void buttonDoneClicked(View view) {
        EditText editTextName = (EditText) this.findViewById(R.id.configure_editText_firstName);
        EditText editTextLastName = (EditText) this.findViewById(R.id.configure_editText_lastName);
        //Log.v("name:", editTextName.getText().toString());

        /*
            Save the name to the shared preferences
         */
        SharedPreferences sharedPreferences = getSharedPreferences("TopBooksPreferences", 0);

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("first_name", editTextName.getText().toString());
        sharedPreferencesEditor.putString("last_name", editTextLastName.getText().toString());
        sharedPreferencesEditor.commit();



        /*
            Report back to the widget saying everything went OK
         */
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        Intent updateWidgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, TopBooksProvider.class);
        updateWidgetIntent.setAction("MANUAL_UPDATE");
        updateWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] {mAppWidgetId});
        sendBroadcast(updateWidgetIntent);

        finish();
    }
}
