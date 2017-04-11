package me.lancer.sevenpounds.mvp.photo;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPhotoPresenter {

    void downloadSuccess(String log);

    void downloadFailure(String log);

    void loadLatestSuccess(List<PhotoBean> list);

    void loadLatestFailure(String log);

    void loadThemeSuccess(List<PhotoBean> list);

    void loadThemeFailure(String log);

    void loadWelfareSuccess(List<PhotoBean> list);

    void loadWelfareFailure(String log);
}