package me.lancer.sevenpounds.mvp.news;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface INewsPresenter {

    void loadTopNewsSuccess(List<NewsBean> list);

    void loadTopNewsFailure(String log);

    void loadLatestSuccess(List<NewsBean> list);

    void loadLatestFailure(String log);

    void loadThemeSuccess(List<NewsBean> list);

    void loadThemeFailure(String log);

    void loadDetailSuccess(NewsBean bean);

    void loadDetailFailure(String log);
}