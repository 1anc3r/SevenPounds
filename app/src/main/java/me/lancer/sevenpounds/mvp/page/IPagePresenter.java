package me.lancer.sevenpounds.mvp.page;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/5/26.
 */

public interface IPagePresenter {

    void loadListSuccess(List<PageBean> list);

    void loadListFailure(String log);
}