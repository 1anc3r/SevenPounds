package me.lancer.sevenpounds.mvp.news;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface INewsView extends IBaseView {

    void showHotest(List<NewsBean> list);

    void showLatest(List<NewsBean> list);

    void showBefore(List<NewsBean> list);

    void showList(List<NewsBean> list);

    void showItem(List<NewsBean> list);

    void showDetail(NewsBean bean);
}
