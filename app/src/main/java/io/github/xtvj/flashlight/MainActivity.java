package io.github.xtvj.flashlight;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

//    private SharedPreferences sp;
//    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        sp = getSharedPreferences("FlashLight", MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sp.edit();
//
//
//        toggleButton = (ToggleButton) findViewById(R.id.bt);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                FlashSwitch.setFlashlightEnabled(MainActivity.this, b);
//                    editor.putBoolean("opened",b);
//                    editor.apply();
//
//            }
//        });

    }

}
