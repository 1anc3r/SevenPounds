package me.lancer.sevenpounds.mvp.game;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IGamePresenter {

    void loadTopGameSuccess(List<GameBean> list);

    void loadTopGameFailure(String log);

    void loadAllGameSuccess(List<GameBean> list);

    void loadAllGameFailure(String log);
}