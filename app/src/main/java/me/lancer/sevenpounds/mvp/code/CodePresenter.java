package me.lancer.sevenpounds.mvp.code;

import java.util.List;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class CodePresenter implements IBasePresenter<ICodeView>, ICodePresenter {

    private ICodeView view;
    private CodeModel model;

    public CodePresenter(ICodeView view) {
        attachView(view);
        model = new CodeModel(this);
    }

    @Override
    public void attachView(ICodeView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadUsers(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadUsers(pager);
        }
    }

    @Override
    public void loadUsersSuccess(List<CodeBean> list) {
        if (view != null) {
            view.showUsers(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadUsersFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadOrganizations(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadOrganizations(pager);
        }
    }

    @Override
    public void loadOrganizationsSuccess(List<CodeBean> list) {
        if (view != null) {
            view.showOrganizations(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadOrganizationsFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadRepositories(int pager) {
        if (view != null) {
            view.showLoad();
            model.loadRepositories(pager);
        }
    }

    @Override
    public void loadRepositoriesSuccess(List<CodeBean> list) {
        if (view != null) {
            view.showRepositories(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadRepositoriesFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
