package me.lancer.sevenpounds.mvp.video;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IVideoPresenter {

    void loadTopVideoSuccess(List<VideoBean> list);

    void loadTopVideoFailure(String log);
}