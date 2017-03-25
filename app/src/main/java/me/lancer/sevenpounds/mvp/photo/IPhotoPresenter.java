package me.lancer.sevenpounds.mvp.photo;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPhotoPresenter {

    void loadLatestSuccess(List<PhotoBean> list);

    void loadLatestFailure(String log);

    void loadThemeSuccess(List<PhotoBean> list);

    void loadThemeFailure(String log);
}