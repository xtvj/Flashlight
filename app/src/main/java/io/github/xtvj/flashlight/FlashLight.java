package io.github.xtvj.flashlight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class FlashLight extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.flashlight);

        Intent intentStart = new Intent(context,FlashService.class);

        SharedPreferences sp = context.getSharedPreferences("FlashLight",Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean("opened",false);
        if (b){
            views.setImageViewResource(R.id.iv_widget,sp.getInt("image",0) == 0 ? R.drawable.flashlight_on : R.drawable.moon_on);
        }else{
            views.setImageViewResource(R.id.iv_widget,sp.getInt("image",0) == 0 ? R.drawable.flashlight_off : R.drawable.moon_off);
        }

        boolean show_text = sp.getBoolean("show_text",true);
        if (show_text){
            views.setViewVisibility(R.id.appwidget_text, View.VISIBLE);
        }else{
            views.setViewVisibility(R.id.appwidget_text,View.GONE);
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

