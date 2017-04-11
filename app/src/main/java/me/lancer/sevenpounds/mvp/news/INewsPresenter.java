package me.lancer.sevenpounds.mvp.news;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface INewsPresenter {

    void loadHotestSuccess(List<NewsBean> list);

    void loadHotestFailure(String log);

    void loadLatestSuccess(List<NewsBean> list);

    void loadLatestFailure(String log);

    void loadBeforeSuccess(List<NewsBean> list);

    void loadBeforeFailure(String log);

    void loadThemeSuccess(List<NewsBean> list);

    void loadThemeFailure(String log);

    void loadDetailSuccess(NewsBean bean);

    void loadDetailFailure(String log);
}