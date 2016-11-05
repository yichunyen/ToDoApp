package project.yen.yichun.todoapp.mvp;

/**
 * Created by Yan on 11/5/16.
 */

public interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
