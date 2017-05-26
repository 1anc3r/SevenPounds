package me.lancer.sevenpounds.mvp.page;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/5/26.
 */

public class PagePresenter implements IBasePresenter<IPageView>, IPagePresenter {

    private IPageView view;
    private PageModel model;

    public PagePresenter(IPageView view) {
        attachView(view);
        model = new PageModel(this);
    }

    @Override
    public void attachView(IPageView view) {
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
    public void loadListSuccess(List<PageBean> list) {
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