package me.lancer.sevenpounds.mvp.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.lancer.sevenpounds.mvp.base.IBasePresenter;
import me.lancer.sevenpounds.mvp.base.fragment.BaseFragment;

/**
 * Created by HuangFangzhi on 2016/12/15.
 */

public abstract class PresenterFragment<P extends IBasePresenter> extends BaseFragment {

    protected P presenter;

    protected abstract P onCreatePresenter();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = onCreatePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = onCreatePresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
