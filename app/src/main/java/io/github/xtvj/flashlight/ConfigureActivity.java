package io.github.xtvj.flashlight;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigureActivity extends Activity {


    private static final String PREFS_NAME = "io.github.xtvj.NewAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private TextView appwidget_text;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ImageView iv_widget;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    //    EditText mAppWidgetText;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = ConfigureActivity.this;

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            FlashLight.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public ConfigureActivity() {
        super();
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_configure);

        preferences = getSharedPreferences("FlashLight",Context.MODE_PRIVATE);
        editor = preferences.edit();

        appwidget_text = (TextView) findViewById(R.id.appwidget_text);
        iv_widget = (ImageView) findViewById(R.id.iv_widget);

        findViewById(R.id.bt).setOnClickListener(mOnClickListener);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //显示、隐藏文字设置
                appwidget_text.setVisibility(isChecked ? View.GONE :View.VISIBLE);
                editor.putBoolean("show_text",!isChecked);
                editor.apply();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(this));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Item点击事件
                //选择图标

                switch (position){
                    case 0:
                        editor.putInt("image",0);
                        iv_widget.setImageResource(R.drawable.flashlight_off);
                        break;
                    case 1:
                        editor.putInt("image",1);
                        iv_widget.setImageResource(R.drawable.moon_off);
                        break;
                }
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

//        mAppWidgetText.setText(loadTitlePref(ConfigureActivity.this, mAppWidgetId));
    }


}
