package me.lancer.sevenpounds.mvp.game;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IGameView extends IBaseView {

    void showFeatured(List<GameBean> list);

    void showCategories(List<GameBean> list);

    void showDetail(GameBean bean);
}
