package com.serwe.serwe.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.serwe.serwe.R;

import java.util.ArrayList;

/**
 * Created by simranjot on 01-09-2017.
 */

public class MyAdapter extends BaseAdapter {
    ArrayList<Menu> arrdata = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<Menu> arrdata, Context context) {
        this.arrdata = arrdata;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrdata.size();
    }

    @Override
    public Object getItem(int position) {
        return arrdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        convertView = inflater.inflate(R.layout.list_item, null);
        TextView lblName = (TextView) convertView.findViewById(R.id.lbldish);
        lblName.setText(arrdata.get(position).getDishName());
        return convertView;
    }
}
