package me.lancer.sevenpounds.mvp.comic;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IComicPresenter {

    void loadListSuccess(List<ComicBean> list);

    void loadListFailure(String log);

    void loadRankSuccess(List<ComicBean> list);

    void loadRankFailure(String log);

    void loadSortSuccess(List<ComicBean> list);

    void loadSortFailure(String log);
}
