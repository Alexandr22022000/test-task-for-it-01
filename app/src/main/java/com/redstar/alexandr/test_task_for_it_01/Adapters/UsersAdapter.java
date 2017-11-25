package com.redstar.alexandr.test_task_for_it_01.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redstar.alexandr.test_task_for_it_01.Callbacks.ItemCallback;
import com.redstar.alexandr.test_task_for_it_01.R;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersDataItem;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    public static final int BANNER_LOADER = -1, BANNER_END = -2, BANNER_EMPTY = -3, BANNER_ERROR = -4;

    private ArrayList<UsersDataItem> items;
    private ItemCallback callback;
    private LayoutInflater layout;

    public UsersAdapter (ArrayList<UsersDataItem> items, ItemCallback callback, Context context) {
        this.items = items;
        this.callback = callback;
        layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        switch ((int) items.get(i).id) {
            case BANNER_LOADER:
                view = layout.inflate(R.layout.item_preloader, viewGroup, false);
                break;

            case BANNER_END:
                view = layout.inflate(R.layout.item_banner, viewGroup, false);
                ((TextView) view.findViewById(R.id.text)).setText("Данных больше нет");
                break;

            case BANNER_EMPTY:
                view = layout.inflate(R.layout.item_banner, viewGroup, false);
                ((TextView) view.findViewById(R.id.text)).setText("Введите запрос");
                break;

            case BANNER_ERROR:
                view = layout.inflate(R.layout.item_banner, viewGroup, false);
                ((TextView) view.findViewById(R.id.text)).setText("Ошибка");
                break;

            default:
                view = layout.inflate(R.layout.item_user, viewGroup, false);
        }

        if (items.get(i).id != -1 && items.get(i).id != -2 && items.get(i).id != -3 && items.get(i).id != -4) {
            int radius = items.get(i).img.getHeight() / 2;
            int diam = items.get(i).img.getHeight();

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Shader shader = new BitmapShader(items.get(i).img, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);

            Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);

            canvas.drawCircle(radius, radius, radius, paint);

            ((ImageView) view.findViewById(R.id.item_img)).setImageBitmap(targetBitmap);
            ((TextView) view.findViewById(R.id.item_text)).setText(items.get(i).name);
            view.setOnClickListener(view1 -> callback.onClick(i));
        }

        return view;
    }
}
