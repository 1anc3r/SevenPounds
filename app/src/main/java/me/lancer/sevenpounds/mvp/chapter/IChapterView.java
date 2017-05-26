package me.lancer.sevenpounds.mvp.chapter;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IChapterView extends IBaseView {

    void showList(List<ChapterBean> list);
}
