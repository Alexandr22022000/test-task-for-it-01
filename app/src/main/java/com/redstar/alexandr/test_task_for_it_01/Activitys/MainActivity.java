package com.redstar.alexandr.test_task_for_it_01.Activitys;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.redstar.alexandr.test_task_for_it_01.Adapters.UsersAdapter;
import com.redstar.alexandr.test_task_for_it_01.Callbacks.ItemCallback;
import com.redstar.alexandr.test_task_for_it_01.Callbacks.MainCallbacks;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.MainInterface;
import com.redstar.alexandr.test_task_for_it_01.Presenters.PresentersManager;
import com.redstar.alexandr.test_task_for_it_01.R;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersDataItem;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private MainCallbacks callbacks;
    private boolean isSearch;
    private ListView list;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        swipeRefreshLayout = findViewById(R.id.refresh);

        if (savedInstanceState == null) {
            long userId = getIntent().getLongExtra("userId", -2);
            if (userId == -2) VKSdk.login(this, "");
            PresentersManager.createPresenter(this, userId);
        }
        else {
            PresentersManager.changeView(this);
        }

        swipeRefreshLayout.setOnRefreshListener(() -> callbacks.onRefresh());

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i + i1 == i2) callbacks.onScrollEnd();
                callbacks.setScrollPosition(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (!isSearch) {
            menu.getItem(0).setVisible(false);
            ActionBar actionBar =getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MenuItem searchItem = menu.getItem(0);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                callbacks.onSearch(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {return false;}
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_my_page:
                callbacks.onClickGoToMyPage();
                return true;

            case 16908332:          //Не нашел нужную константу, это кнопка назад
                callbacks.onClickBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setCallbacks(MainCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setItems(ArrayList<UsersDataItem> items, int scrollPosition) {
        ItemCallback callback = itemId -> callbacks.onClickItem(itemId);
        list.setAdapter(new UsersAdapter(items, callback, this));
        list.setSelectionFromTop(scrollPosition, 0);
    }

    @Override
    public void setMode(boolean isSearch) {
        this.isSearch = isSearch;
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void goToUserPage(long userId) {
        Intent intent = new Intent(this, UserPageActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void goToBack() {
        PresentersManager.destroyPresenter();
        finish();
    }
}