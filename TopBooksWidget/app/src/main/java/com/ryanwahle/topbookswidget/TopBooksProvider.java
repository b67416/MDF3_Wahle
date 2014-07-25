package com.ryanwahle.topbookswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by ryanwahle on 7/24/14.
 */
public class TopBooksProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int numberOfAppWidgetIds = appWidgetIds.length;

        for (int currentAppWidgetIndex = 0; currentAppWidgetIndex < numberOfAppWidgetIds; currentAppWidgetIndex++) {
            int appWidgetId = appWidgetIds[currentAppWidgetIndex];
            Log.v("AppWidgetProvider: " + appWidgetId, "onUpdate called");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.homescreen_widget);

            //Intent defineIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, defineIntent, 0);

            //views.setOnClickPendingIntent(R.id.widget_textView_bookTitle, pendingIntent);

            /*
                Manually onUpdate code
             */
            Intent intent = new Intent(context, TopBooksProvider.class);
            intent.setAction("MANUAL_UPDATE");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_textView_bookTitle, pendingIntent);
            /*
                End of Manually onUpdate code
             */

            /*
                Get the SharedPreferences and add new ones if none exist
             */
            SharedPreferences widgetSharedPreferences = context.getSharedPreferences("TopBooksPreferences", 0);
            if (widgetSharedPreferences.contains("book_1") == false) {
                Log.v("widget:", "no books found . . . assuming no data at all . . . creating!");

                SharedPreferences.Editor widgetSharedPreferencesEditor = widgetSharedPreferences.edit();

                widgetSharedPreferencesEditor.putInt("current_book", 1);
                widgetSharedPreferencesEditor.putString("book_1", "");
                widgetSharedPreferencesEditor.putString("book_2", "");
                widgetSharedPreferencesEditor.putString("book_3", "");
                widgetSharedPreferencesEditor.putString("book_4", "");

                widgetSharedPreferencesEditor.commit();

            } else {
                Log.v("widget:", "book data found!");
            }

            this.updateAppWidget(appWidgetManager, appWidgetId, views);
        }
    }

   private void updateAppWidget(AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views) {
       Log.v("AppWidgetProvider: " + appWidgetId, "updateAppWidget called");
       appWidgetManager.updateAppWidget(appWidgetId, views);
   }

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
