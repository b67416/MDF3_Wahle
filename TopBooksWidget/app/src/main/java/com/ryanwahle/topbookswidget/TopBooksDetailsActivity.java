/*
    Author:     Ryan Wahle

    Project:    Top Books Widget

    Package:    com.ryanwahle.topbookswidget

    File:       TopBooksDetailsActivity.java

    Purpose:    This activity is called when the user clicks
                on the book icon from the widget. It displays
                the name of the book and the users first and
                last name.
 */

package com.ryanwahle.topbookswidget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class TopBooksDetailsActivity extends Activity {

    /*
        Called when the activity is created. It grabs the users
        first name, last name, and book title and displays it on
        the screen with a graphic.
    */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity);

        SharedPreferences widgetSharedPreferences = getSharedPreferences("TopBooksPreferences", 0);

        int currentBookInt = widgetSharedPreferences.getInt("current_book", 0);
        String currentBookString = widgetSharedPreferences.getString("book_" + currentBookInt, "");
        String firstNameString = widgetSharedPreferences.getString("first_name", "");
        String lastNameString = widgetSharedPreferences.getString("last_name", "");

        TextView selectedBookTextView = (TextView) findViewById(R.id.bookDetails_selectedBook);
        TextView NameTextView = (TextView) findViewById(R.id.bookDetails_name);

        selectedBookTextView.setText(currentBookString);
        NameTextView.setText(firstNameString + " " + lastNameString);

    }

}
