package me.lancer.sevenpounds.mvp.movie;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class MoviePresenter implements IBasePresenter<IMovieView>, IMoviePresenter {

    private IMovieView view;
    private MovieModel model;

    public MoviePresenter(IMovieView view) {
        attachView(view);
        model = new MovieModel(this);
    }

    @Override
    public void attachView(IMovieView view) {
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
    public void loadReviewerSuccess(List<MovieBean> list) {
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

    public void loadTopMovie(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadTopMovie(pager);
        }
    }

    @Override
    public void loadTopMovieSuccess(List<MovieBean> list) {
        if (view != null) {
            view.showTopMovie(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopMovieFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
