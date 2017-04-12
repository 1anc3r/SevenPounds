package me.lancer.sevenpounds.mvp.video;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class VideoPresenter implements IBasePresenter<IVideoView>, IVideoPresenter {

    private IVideoView view;
    private VideoModel model;

    public VideoPresenter(IVideoView view) {
        attachView(view);
        model = new VideoModel(this);
    }

    @Override
    public void attachView(IVideoView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadTheme(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadTheme(pager);
        }
    }

    @Override
    public void loadThemeSuccess(List<VideoBean> list) {
        if (view != null) {
            view.showTheme(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadThemeFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadDetail(String url) {
        if (view != null) {
            view.showLoad();
            model.loadDetail(url);
        }
    }

    @Override
    public void loadDetailSuccess(VideoBean bean) {
        if (view != null) {
            view.showDetail(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadDetailFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
