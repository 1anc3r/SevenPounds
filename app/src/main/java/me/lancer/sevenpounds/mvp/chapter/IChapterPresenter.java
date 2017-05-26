package me.lancer.sevenpounds.mvp.chapter;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IChapterPresenter {

    void loadListSuccess(List<ChapterBean> list);

    void loadListFailure(String log);
}
