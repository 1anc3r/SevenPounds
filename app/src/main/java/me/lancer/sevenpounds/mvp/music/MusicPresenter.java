package me.lancer.sevenpounds.mvp.music;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class MusicPresenter implements IBasePresenter<IMusicView>, IMusicPresenter {

    private IMusicView view;
    private MusicModel model;

    public MusicPresenter(IMusicView view) {
        attachView(view);
        model = new MusicModel(this);
    }

    @Override
    public void attachView(IMusicView view) {
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
    public void loadReviewerSuccess(List<MusicBean> list) {
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

    public void loadTopMusic(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadTopMusic(pager);
        }
    }

    @Override
    public void loadTopMusicSuccess(List<MusicBean> list) {
        if (view != null) {
            view.showTopMusic(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadTopMusicFailure(String log) {
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
    public void loadReviewerDetailSuccess(MusicBean bean) {
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
    public void loadTopDetailSuccess(MusicBean bean) {
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
