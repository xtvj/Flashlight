package io.github.xtvj.flashlight;

import android.app.Application;

/**
 * Created by PC on 2017/1/12.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
