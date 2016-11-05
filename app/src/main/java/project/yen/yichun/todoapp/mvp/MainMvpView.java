package project.yen.yichun.todoapp.mvp;

import java.util.ArrayList;

import project.yen.yichun.todoapp.TaskObject;

/**
 * Created by Yan on 11/5/16.
 */

public interface MainMvpView extends MvpView {
    void readLocalData(ArrayList<TaskObject> arrayList);

    void writeTask(TaskObject object);
}
