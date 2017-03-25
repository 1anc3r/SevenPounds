package me.lancer.sevenpounds.mvp.photo;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IPhotoView extends IBaseView {

    void showLatest(List<PhotoBean> list);

    void showTheme(List<PhotoBean> list);
}
