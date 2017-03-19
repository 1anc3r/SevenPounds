package me.lancer.sevenpounds.mvp.music;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IMusicView extends IBaseView {

    void showReviewer(List<MusicBean> list);

    void showTopMusic(List<MusicBean> list);

    void showReviewerDetail(MusicBean bean);

    void showTopDetail(MusicBean bean);
}
