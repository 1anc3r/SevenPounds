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

    public void loadTopGame() {
        if (view != null) {
            view.showLoad();
            model.loadTopGame();
        }
    }

    @Override
    public void loadTopGameSuccess(List<GameBean> list) {
        if (view != null) {
            view.showTopGame(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopGameFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadAllGame() {
        if (view != null) {
            view.showLoad();
            model.loadAllGame();
        }
    }

    @Override
    public void loadAllGameSuccess(List<GameBean> list) {
        if (view != null) {
            view.showAllGame(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadAllGameFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
