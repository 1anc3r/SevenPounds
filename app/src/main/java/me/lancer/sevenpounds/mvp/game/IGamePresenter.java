package me.lancer.sevenpounds.mvp.game;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IGamePresenter {

    void loadTopGameSuccess(List<GameBean> list);

    void loadTopGameFailure(String log);

    void loadThemeSuccess(List<GameBean> list);

    void loadThemeFailure(String log);

    void loadDetailSuccess(GameBean bean);

    void loadDetailFailure(String log);
}