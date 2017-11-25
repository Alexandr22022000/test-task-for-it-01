package com.redstar.alexandr.test_task_for_it_01.Presenters;

import android.support.v7.app.AppCompatActivity;

import com.redstar.alexandr.test_task_for_it_01.Activitys.MainActivity;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.MainInterface;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.UserPageInterface;

import java.util.Stack;

public class PresentersManager {
    private static Stack<BasePresenter> presenterStack = new Stack<>();

    public static void createPresenter (AppCompatActivity view, long userId) {
        if (view instanceof MainActivity) {
            presenterStack.push(new MainPresenter((MainInterface) view, userId));
        }
        else {
            presenterStack.push(new UserPagePresenter((UserPageInterface) view, userId));
        }
    }

    public static void changeView (AppCompatActivity view) {
        presenterStack.peek().changeView(view);
    }

    public static void destroyPresenter () {
        presenterStack.pop().destroyView();
    }
}
