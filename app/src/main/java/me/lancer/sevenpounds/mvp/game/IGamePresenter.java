package me.lancer.sevenpounds.mvp.game;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IGamePresenter {

    void loadFeaturedSuccess(List<GameBean> list);

    void loadFeaturedFailure(String log);

    void loadCategoriesSuccess(List<GameBean> list);

    void loadCategoriesFailure(String log);

    void loadDetailSuccess(GameBean bean);

    void loadDetailFailure(String log);
}