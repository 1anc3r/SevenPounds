package me.lancer.sevenpounds.mvp.music;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IMusicPresenter {

    void loadReviewerSuccess(List<MusicBean> list);

    void loadReviewerFailure(String log);

    void loadTopMusicSuccess(List<MusicBean> list);

    void loadTopMusicFailure(String log);
}