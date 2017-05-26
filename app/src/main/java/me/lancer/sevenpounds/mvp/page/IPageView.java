package me.lancer.sevenpounds.mvp.page;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/5/26.
 */

public interface IPageView extends IBaseView {

    void showList(List<PageBean> list);
}