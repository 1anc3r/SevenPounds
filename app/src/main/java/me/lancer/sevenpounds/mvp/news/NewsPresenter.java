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

    public void loadTopNews() {
        if (view != null) {
            view.showLoad();
            model.loadTopNews();
        }
    }

    @Override
    public void loadTopNewsSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showTopNews(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopNewsFailure(String log) {
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

    public void loadDetail(String url) {
        if (view != null) {
            view.showLoad();
            model.loadDetail(url);
        }
    }

    @Override
    public void loadDetailSuccess(NewsBean bean) {
        if (view != null) {
            view.showDetail(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadDetailFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
