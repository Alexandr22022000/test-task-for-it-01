package com.redstar.alexandr.test_task_for_it_01.Callbacks;

public interface MainCallbacks {
    void onClickItem (int ItemId);

    void onClickGoToMyPage ();

    void onSearch (String query);

    void onClickBack ();

    void onRefresh ();

    void onScrollEnd ();

    void setScrollPosition (int position);
}
