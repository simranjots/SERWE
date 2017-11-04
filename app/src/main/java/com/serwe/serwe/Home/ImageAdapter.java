package com.serwe.serwe.Home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.serwe.serwe.R;

/**
 * Created by simranjot on 01-09-2017.
 */

class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.domi, R.drawable.kfc,
            R.drawable.mc, R.drawable.ph,
            R.drawable.strbk, R.drawable.bk,

    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
