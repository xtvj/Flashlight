package io.github.xtvj.flashlight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class FlashLight extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.flashlight);

        Intent intentStart = new Intent(context,FlashService.class);

        intentStart.putExtra("appWidgetId", appWidgetId);

        SharedPreferences sp = context.getSharedPreferences("FlashLight",Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean("opened",true);
        if (b){
            views.setImageViewResource(R.id.iv_widget,R.drawable.flashlight_on);
        }else{
            views.setImageViewResource(R.id.iv_widget,R.drawable.flashlight_off);
        }

        PendingIntent pendingitent = PendingIntent.getService(context, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.iv_widget, pendingitent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
    }


}

