package me.lancer.sevenpounds.mvp.book;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class BookPresenter implements IBasePresenter<IBookView>, IBookPresenter {

    private IBookView view;
    private BookModel model;

    public BookPresenter(IBookView view) {
        attachView(view);
        model = new BookModel(this);
    }

    @Override
    public void attachView(IBookView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadReviewer(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadReviewer(pager);
        }
    }

    @Override
    public void loadReviewerSuccess(List<BookBean> list) {
        if (view != null) {
            view.showReviewer(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadReviewerFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadTopBook(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadTopBook(pager);
        }
    }

    public void loadTopBook(String query) {
        if (view != null) {
            view.showLoad();
            model.loadTopBook(query);
        }
    }

    @Override
    public void loadTopBookSuccess(List<BookBean> list) {
        if (view != null) {
            view.showTopBook(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopBookFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadReviewerDetail(String url) {
        if (view != null) {
            view.showLoad();
            model.loadReviewerDetail(url);
        }
    }

    @Override
    public void loadReviewerDetailSuccess(BookBean bean) {
        if (view != null) {
            view.showReviewerDetail(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadReviewerDetailFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadTopDetail(String url) {
        if (view != null) {
            view.showLoad();
            model.loadTopDetail(url);
        }
    }

    @Override
    public void loadTopDetailSuccess(BookBean bean) {
        if (view != null) {
            view.showTopDetail(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopDetailFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
