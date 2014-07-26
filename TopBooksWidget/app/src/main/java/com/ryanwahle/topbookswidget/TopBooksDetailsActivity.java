package com.ryanwahle.topbookswidget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ryanwahle on 7/25/14.
 */
public class TopBooksDetailsActivity extends Activity {

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
