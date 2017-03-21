package me.lancer.sevenpounds.mvp.video;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IVideoPresenter {

    void loadThemeSuccess(List<VideoBean> list);

    void loadThemeFailure(String log);

    void loadDetailSuccess(VideoBean bean);

    void loadDetailFailure(String log);
}