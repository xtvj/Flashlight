package io.github.xtvj.flashlight;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;

public class FlashService extends Service {
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



        if (intent.getStringExtra("onClick") == null){
            return super.onStartCommand(intent, flags, startId);
        }
        // 设置开始监听
        Intent intentStart = new Intent(FlashService.this, FlashService.class);
        intentStart.putExtra("onClick","onClick");
        SharedPreferences sp = getSharedPreferences("FlashLight", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean b = sp.getBoolean("opened", false);
        if (b) {
            FlashSwitch.setFlashlightEnabled(FlashService.this, false);
            views.setImageViewResource(R.id.iv_widget, sp.getInt("image",0) == 0 ? R.drawable.flashlight_off : R.drawable.moon_off);
            editor.putBoolean("opened", false);
        } else {
            FlashSwitch.setFlashlightEnabled(FlashService.this, true);
            views.setImageViewResource(R.id.iv_widget, sp.getInt("image",0) == 0 ? R.drawable.flashlight_on : R.drawable.moon_on);
            editor.putBoolean("opened", true);
        }
        editor.apply();
        PendingIntent pendingitent = PendingIntent.getService(FlashService.this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_widget, pendingitent);

        boolean show_text = sp.getBoolean("show_text",true);
        if (show_text){
            views.setViewVisibility(R.id.appwidget_text, View.VISIBLE);
        }else{
            views.setViewVisibility(R.id.appwidget_text,View.GONE);
        }

        appWidgetManager.updateAppWidget(new ComponentName(getBaseContext(), FlashLight.class), views);

        if (!b) {
            stopSelf(flags);//关闭服务
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        SharedPreferences sp = getSharedPreferences("FlashLight", Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean("opened", false);
        if (b) {
                // 设置开始监听
                Intent intentStart = new Intent(FlashService.this, FlashService.class);
                intentStart.putExtra("onClick","onClick");
                FlashSwitch.setFlashlightEnabled(FlashService.this, false);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("opened", false);
                editor.apply();
                views.setImageViewResource(R.id.iv_widget, sp.getInt("image",0) == 0 ? R.drawable.flashlight_off : R.drawable.moon_off);
                PendingIntent pendingitent = PendingIntent.getService(FlashService.this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.iv_widget, pendingitent);
                appWidgetManager.updateAppWidget(new ComponentName(getBaseContext(), FlashLight.class), views);
        }
        super.onDestroy();
    }
}
