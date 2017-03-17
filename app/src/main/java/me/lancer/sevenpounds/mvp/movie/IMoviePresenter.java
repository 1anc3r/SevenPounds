package me.lancer.sevenpounds.mvp.movie;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IMoviePresenter {

    void loadReviewerSuccess(List<MovieBean> list);

    void loadReviewerFailure(String log);

    void loadTopMovieSuccess(List<MovieBean> list);

    void loadTopMovieFailure(String log);
}