package com.ryanwahle.topbookswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by ryanwahle on 7/24/14.
 */
public class TopBooksProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int numberOfAppWidgetIds = appWidgetIds.length;

        for (int currentAppWidgetIndex = 0; currentAppWidgetIndex < numberOfAppWidgetIds; currentAppWidgetIndex++) {
            int appWidgetId = appWidgetIds[currentAppWidgetIndex];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.homescreen_widget);

            Intent defineIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, defineIntent, 0);

            views.setOnClickPendingIntent(R.id.widget_textView_bookTitle, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    public void onEnabled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName("com.ryanwahle.topbookswidget", ".TopBooksProvider"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public void onDisabled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName("com.ryanwahle.topbookswidget", ".TopBooksProvider"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
