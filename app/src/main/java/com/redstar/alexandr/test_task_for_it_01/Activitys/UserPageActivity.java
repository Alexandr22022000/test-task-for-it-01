package com.redstar.alexandr.test_task_for_it_01.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.redstar.alexandr.test_task_for_it_01.Adapters.UserDataAdapter;
import com.redstar.alexandr.test_task_for_it_01.Callbacks.UserPageCallbacks;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.UserPageInterface;
import com.redstar.alexandr.test_task_for_it_01.Presenters.PresentersManager;
import com.redstar.alexandr.test_task_for_it_01.R;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserDataItem;

public class UserPageActivity extends AppCompatActivity implements UserPageInterface {
    private UserPageCallbacks callbacks;
    private ListView list;
    private ImageView image;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isMyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        list = findViewById(R.id.list);
        image = findViewById(R.id.image);
        swipeRefreshLayout = findViewById(R.id.refresh1);
        SwipeRefreshLayout swipeRefreshLayout2 = findViewById(R.id.refresh2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {
            PresentersManager.createPresenter(this, getIntent().getLongExtra("userId", -2));
        }
        else {
            PresentersManager.changeView(this);
        }

        swipeRefreshLayout.setOnRefreshListener(() -> callbacks.onRefresh());
        swipeRefreshLayout2.setOnRefreshListener(() -> {
            swipeRefreshLayout2.setRefreshing(false);
            swipeRefreshLayout.setRefreshing(true);
            callbacks.onRefresh();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_page_menu, menu);
        menu.getItem(1).setVisible(!isMyPage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_my_page:
                callbacks.onClickMyPage();
                break;

            case R.id.show_user_friends:
                callbacks.onClickFriends();
                break;

            case 16908332:          //Не нашел нужную константу, это кнопка назад
                callbacks.onClickBack();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setCallbacks(UserPageCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setMode(boolean isMyPage) {
        this.isMyPage = isMyPage;
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void setData(Bitmap img, UserDataItem[] data) {
        image.setImageBitmap(img);
        list.setAdapter(new UserDataAdapter(data, this));
    }

    @Override
    public void goToUserPage(long userId) {
        Intent intent = new Intent(this, UserPageActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void goToFriends(long userId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void goToBack() {
        PresentersManager.destroyPresenter();
        finish();
    }

    @Override
    public void openErrorWindow() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);

        builder3
                .setTitle("Ошибка!")
                .setMessage("Произошла ошибка при загрузки данных из сети. Проверте подключение к интернету. Попробовать еще раз?")
                .setCancelable(true)
                .setPositiveButton("Да", (dialogInterface, i) -> callbacks.onRefresh())
                .setNegativeButton("Назад", (dialogInterface, i) -> callbacks.onClickBack());

        builder3.create().show();
    }
}
