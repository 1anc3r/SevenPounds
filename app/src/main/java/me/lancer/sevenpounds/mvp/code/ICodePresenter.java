package me.lancer.sevenpounds.mvp.code;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ICodePresenter {

    void loadUsersSuccess(List<CodeBean> list);

    void loadUsersFailure(String log);

    void loadOrganizationsSuccess(List<CodeBean> list);

    void loadOrganizationsFailure(String log);

    void loadRepositoriesSuccess(List<CodeBean> list);

    void loadRepositoriesFailure(String log);

    void loadTrendingSuccess(List<CodeBean> list);

    void loadTrendingFailure(String log);

    void loadSearchingSuccess(List<CodeBean> list);

    void loadSearchingFailure(String log);

    void loadDetailSuccess(CodeBean bean);

    void loadDetailFailure(String log);
}