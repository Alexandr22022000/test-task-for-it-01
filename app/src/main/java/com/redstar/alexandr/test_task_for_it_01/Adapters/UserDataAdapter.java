package com.redstar.alexandr.test_task_for_it_01.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.redstar.alexandr.test_task_for_it_01.R;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserDataItem;

public class UserDataAdapter extends BaseAdapter {
    private UserDataItem[] items;
    private LayoutInflater layout;

    public UserDataAdapter (UserDataItem[] items, Context context) {
        this.items = items;
        layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layout.inflate(R.layout.item_user_data, viewGroup, false);
        }

        ((TextView) view.findViewById(R.id.item_name)).setText(items[i].name);
        ((TextView) view.findViewById(R.id.item_value)).setText(items[i].value);

        return view;
    }
}
