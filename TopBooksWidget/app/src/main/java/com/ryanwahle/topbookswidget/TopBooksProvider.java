/*
    Author:     Ryan Wahle

    Project:    Top Books Widget

    Package:    com.ryanwahle.topbookswidget

    File:       TopBooksProvider.java

    Purpose:    This is the code that provides data for the widget
                to display. It handles everything from setting up
                the appwidget interface to refreshing it.
 */

package com.ryanwahle.topbookswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class TopBooksProvider extends AppWidgetProvider {

    /*
        Called by the OS when the widget is to be updating.
    */
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int numberOfAppWidgetIds = appWidgetIds.length;

        for (int currentAppWidgetIndex = 0; currentAppWidgetIndex < numberOfAppWidgetIds; currentAppWidgetIndex++) {
            int appWidgetId = appWidgetIds[currentAppWidgetIndex];
            Log.v("AppWidgetProvider: " + appWidgetId, "onUpdate called");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.homescreen_widget);

            /*
                Set the onClickHandler to launch the book details when touched
                from the widget.
            */
            Intent launchDetailsActivityIntent = new Intent(context, TopBooksDetailsActivity.class);
            PendingIntent launchDetailsActivityPendingIntent = PendingIntent.getActivity(context, 0, launchDetailsActivityIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_imageButton_bookDetails, launchDetailsActivityPendingIntent);

            /* * * * *
                This is TESTING CODE ONLY.

                It is used to allow the developer to click on the book title
                and have it refresh the widget. This way we do not have to wait
                the full hour to make sure our books get updated on the widget
                user interface.
            * * * * */

            //Intent intent = new Intent(context, TopBooksProvider.class);
            //intent.setAction("MANUAL_UPDATE");

            //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            //views.setOnClickPendingIntent(R.id.widget_textView_bookTitle, pendingIntent);

            /* * * * *
                End of Manually onUpdate code
            * * * * */

            /*
                Get the SharedPreferences and add new ones if none exist. If they do exist
                then increment the current_book counter.
             */
            SharedPreferences widgetSharedPreferences = context.getSharedPreferences("TopBooksPreferences", 0);
            if (widgetSharedPreferences.contains("book_1") == false) {
                Log.v("widget:", "no books found . . . assuming no data at all . . . creating!");

                SharedPreferences.Editor widgetSharedPreferencesEditor = widgetSharedPreferences.edit();

                widgetSharedPreferencesEditor.putInt("current_book", 1);
                widgetSharedPreferencesEditor.putString("book_1", "If I Stay");
                widgetSharedPreferencesEditor.putString("book_2", "The Fault in Our Stars");
                widgetSharedPreferencesEditor.putString("book_3", "Frozen Little Golden Book");
                widgetSharedPreferencesEditor.putString("book_4", "The Giver");

                widgetSharedPreferencesEditor.commit();

            } else {
                int currentBookInt = widgetSharedPreferences.getInt("current_book", 0);

                /*
                    If the current book counter is greater than 4, then there are no
                    more books, so set it back to the beginning.
                 */
                currentBookInt = currentBookInt + 1;
                if (currentBookInt > 4) {
                    currentBookInt = 1;
                }

                SharedPreferences.Editor widgetSharedPreferencesEditor = widgetSharedPreferences.edit();
                widgetSharedPreferencesEditor.putInt("current_book", currentBookInt);
                widgetSharedPreferencesEditor.commit();
            }


            /*
                Set the book to the widget textView
             */
            int currentBookInt = widgetSharedPreferences.getInt("current_book", 0);
            String currentBookString = widgetSharedPreferences.getString("book_" + currentBookInt, "");
            String nameString = widgetSharedPreferences.getString("first_name", "");

            views.setTextViewText(R.id.widget_textView_title, "Top Book for " + nameString);
            views.setTextViewText(R.id.widget_textView_bookTitle, currentBookString);

            /*
                Refresh the widget UI
            */
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

   /*
        This is called when there is a manual broadcast sent via our
        widget to update the user interface.

        eg. the initial update after configuring the widget.
        eg. when the testing code is uncommented to simulate an update broadcast.
   */
   @Override
   public void onReceive(Context context, Intent intent) {
       super.onReceive(context, intent);

       if (intent.getAction().equals("MANUAL_UPDATE")) {
           Log.v("AppWidgetProvider", "MANUAL_UPDATE RECEIVED");

           AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
           ComponentName widget = new ComponentName(context.getPackageName(), TopBooksProvider.class.getName());
           int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);

           onUpdate(context, appWidgetManager, appWidgetIds);
       }
   }
}
