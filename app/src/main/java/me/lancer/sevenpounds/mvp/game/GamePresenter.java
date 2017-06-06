package me.lancer.sevenpounds.mvp.game;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class GamePresenter implements IBasePresenter<IGameView>, IGamePresenter {

    private IGameView view;
    private GameModel model;

    public GamePresenter(IGameView view) {
        attachView(view);
        model = new GameModel(this);
    }

    @Override
    public void attachView(IGameView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadFeatured() {
        if (view != null) {
            view.showLoad();
            model.loadFeatured();
        }
    }

    @Override
    public void loadFeaturedSuccess(List<GameBean> list) {
        if (view != null) {
            view.showFeatured(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadFeaturedFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadCategories(String keyword) {
        if (view != null) {
            view.showLoad();
            model.loadCategories(keyword);
        }
    }

    @Override
    public void loadCategoriesSuccess(List<GameBean> list) {
        if (view != null) {
            view.showCategories(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadCategoriesFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadDetail(int id) {
        if (view != null) {
            view.showLoad();
            model.loadDetail(id);
        }
    }

    @Override
    public void loadDetailSuccess(GameBean bean) {
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
