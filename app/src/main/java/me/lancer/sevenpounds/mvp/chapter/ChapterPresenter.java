package me.lancer.sevenpounds.mvp.chapter;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class ChapterPresenter implements IBasePresenter<IChapterView>, IChapterPresenter {

    private IChapterView view;
    private ChapterModel model;

    public ChapterPresenter(IChapterView view) {
        attachView(view);
        model = new ChapterModel(this);
    }

    @Override
    public void attachView(IChapterView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadList(String url) {
        if (view != null) {
            view.showLoad();
            model.loadList(url);
        }
    }

    @Override
    public void loadListSuccess(List<ChapterBean> list) {
        if (view != null) {
            view.showList(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadListFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
