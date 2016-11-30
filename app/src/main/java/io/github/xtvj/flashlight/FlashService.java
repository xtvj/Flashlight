package io.github.xtvj.flashlight;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.RemoteViews;

public class FlashService extends Service {
    private int appWidgetId;
    private AppWidgetManager appWidgetManager;
    private RemoteViews views;

    public FlashService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appWidgetManager = AppWidgetManager.getInstance(FlashService.this);
        views = new RemoteViews(getPackageName(), R.layout.flashlight);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        appWidgetId = intent.getIntExtra("appWidgetId", 0);

        // 设置开始监听
        Intent intentStart = new Intent(FlashService.this, FlashService.class);

        SharedPreferences sp = getSharedPreferences("FlashLight", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean b = sp.getBoolean("opened", true);
        if (b) {
            FlashSwitch.setFlashlightEnabled(FlashService.this, false);
            views.setImageViewResource(R.id.iv_widget, R.drawable.flashlight_off);
            editor.putBoolean("opened", false);
        } else {
            FlashSwitch.setFlashlightEnabled(FlashService.this, true);
            views.setImageViewResource(R.id.iv_widget, R.drawable.flashlight_on);
            editor.putBoolean("opened", true);
        }
        editor.apply();


        intentStart.putExtra("appWidgetId", appWidgetId);
        PendingIntent pendingitent = PendingIntent.getService(FlashService.this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_widget, pendingitent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // 设置开始监听
        Intent intentStart = new Intent(FlashService.this, FlashService.class);
        // 为Intent对象设置Action
//        intentStart.setAction(FLASHLIGHT);

        SharedPreferences sp = getSharedPreferences("FlashLight",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        FlashSwitch.setFlashlightEnabled(FlashService.this, false);
        views.setImageViewResource(R.id.iv_widget, R.drawable.flashlight_off);
        editor.putBoolean("opened", false);
        editor.apply();

        intentStart.putExtra("appWidgetId", appWidgetId);
        PendingIntent pendingitent = PendingIntent.getService(FlashService.this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_widget, pendingitent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}
