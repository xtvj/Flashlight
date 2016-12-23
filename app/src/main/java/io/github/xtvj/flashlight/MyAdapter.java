package io.github.xtvj.flashlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class MyAdapter extends BaseAdapter {
    private Context mContext;

    MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;

        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
            viewHoder.iv_1 = (ImageView) convertView.findViewById(R.id.iv_1);
            viewHoder.iv_2 = (ImageView) convertView.findViewById(R.id.iv_2);

            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }


        switch (position) {
            case 0:
                viewHoder.iv_1.setImageResource(R.drawable.flashlight_on);
                viewHoder.iv_2.setImageResource(R.drawable.flashlight_off);
                break;
            case 1:
                viewHoder.iv_1.setImageResource(R.drawable.moon_on);
                viewHoder.iv_2.setImageResource(R.drawable.moon_off);
                break;
        }


        return convertView;
    }


    private final class ViewHoder {
        private ImageView iv_1, iv_2;
    }


}
