package me.lancer.sevenpounds.mvp.photo;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class PhotoPresenter implements IBasePresenter<IPhotoView>, IPhotoPresenter {

    private IPhotoView view;
    private PhotoModel model;

    public PhotoPresenter(IPhotoView view) {
        attachView(view);
        model = new PhotoModel(this);
    }

    @Override
    public void attachView(IPhotoView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void download(String url, String title) {
        if (view != null) {
            view.showLoad();
            model.download(url, title);
        }
    }

    @Override
    public void downloadSuccess(String log) {
        if (view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    @Override
    public void downloadFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadLatest(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadLatest(pager);
        }
    }

    @Override
    public void loadLatestSuccess(List<PhotoBean> list) {
        if (view != null) {
            view.showLatest(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadLatestFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadTheme(String type) {
        if (view != null) {
            view.showLoad();
            model.loadTheme(type);
        }
    }

    @Override
    public void loadThemeSuccess(List<PhotoBean> list) {
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

    public void loadWelfare(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadWelfare(pager);
        }
    }

    @Override
    public void loadWelfareSuccess(List<PhotoBean> list) {
        if (view != null) {
            view.showWelfare(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadWelfareFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
