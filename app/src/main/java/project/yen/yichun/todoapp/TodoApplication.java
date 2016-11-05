package project.yen.yichun.todoapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Yan on 11/5/16.
 */

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
