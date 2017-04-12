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

    public void loadHotest() {
        if (view != null) {
            view.showLoad();
            model.loadHotest();
        }
    }

    @Override
    public void loadHotestSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showHotest(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadHotestFailure(String log) {
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

    public void loadBefore(String date) {
        if (view != null) {
            view.showLoad();
            model.loadBefore(date);
        }
    }

    @Override
    public void loadBeforeSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showBefore(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadBeforeFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadList() {
        if (view != null) {
            view.showLoad();
            model.loadList();
        }
    }

    @Override
    public void loadListSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showList(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadListFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadItem(int type) {
        if (view != null) {
            view.showLoad();
            model.loadItem(type);
        }
    }

    @Override
    public void loadItemSuccess(List<NewsBean> list) {
        if (view != null) {
            view.showItem(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadItemFailure(String log) {
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
