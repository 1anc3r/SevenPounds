package me.lancer.sevenpounds.mvp.book;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IBookPresenter {

    void loadReviewerSuccess(List<BookBean> list);

    void loadReviewerFailure(String log);

    void loadTopBookSuccess(List<BookBean> list);

    void loadTopBookFailure(String log);
}