package me.lancer.sevenpounds.mvp.news;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class NewsPresenter implements IBasePresenter<INewsView>, INewsPresenter {

    private INewsView view;
    private NewsModel model;

    public NewsPresenter(INewsView view) {
        attachView(view);
        model = new NewsModel(this);
    }

    @Override
    public void attachView(INewsView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadTop() {
        if (view != null) {
            view.showLoad();
            model.loadTop();
        }
    }

    @Override
    public void loadTopSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showTop(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadLatest() {
        if (view != null) {
            view.showLoad();
            model.loadLatest();
        }
    }

    @Override
    public void loadLatestSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showLatest(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadLatestFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadTheme(int type) {
        if (view != null) {
            view.showLoad();
            model.loadTheme(type);
        }
    }

    @Override
    public void loadThemeSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showTheme(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadThemeFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
