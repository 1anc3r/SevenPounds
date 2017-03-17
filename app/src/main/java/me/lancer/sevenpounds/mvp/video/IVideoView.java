package me.lancer.sevenpounds.mvp.video;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IVideoView extends IBaseView {

    void showTopVideo(List<VideoBean> list);
}
