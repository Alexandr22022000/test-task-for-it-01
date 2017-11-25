package com.redstar.alexandr.test_task_for_it_01.Presenters;

public abstract class BasePresenter<V> {
    public V view;
    void changeView (V view) {
        this.view = view;
        setCallbacksAndMode();
    }

    void destroyView () {
        this.view = null;
    }

    abstract void setCallbacksAndMode();
}
