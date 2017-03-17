package me.lancer.sevenpounds.mvp.base;

/**
 * Created by HuangFangzhi on 2016/12/13.
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detachView();
}
