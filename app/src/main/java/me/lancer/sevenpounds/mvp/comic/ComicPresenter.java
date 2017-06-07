package me.lancer.sevenpounds.mvp.comic;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class ComicPresenter implements IBasePresenter<IComicView>, IComicPresenter {

    private IComicView view;
    private ComicModel model;

    public ComicPresenter(IComicView view) {
        attachView(view);
        model = new ComicModel(this);
    }

    @Override
    public void attachView(IComicView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadList() {
        if (view != null) {
            view.showLoad();
            model.loadList();
        }
    }

    public void loadList(String query) {
        if (view != null) {
            view.showLoad();
            model.loadList(query);
        }
    }

    @Override
    public void loadListSuccess(List<ComicBean> list) {
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

    public void loadRankTitle() {
        if (view != null) {
            view.showLoad();
            model.loadRankTitle();
        }
    }

    @Override
    public void loadRankSuccess(List<ComicBean> list) {
        if (view != null) {
            view.showRank(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadRankFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadSortTitle() {
        if (view != null) {
            view.showLoad();
            model.loadSortTitle();
        }
    }

    public void loadSortContent(String url) {
        if (view != null) {
            view.showLoad();
            model.loadSortContent(url);
        }
    }

    @Override
    public void loadSortSuccess(List<ComicBean> list) {
        if (view != null) {
            view.showSort(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadSortFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
