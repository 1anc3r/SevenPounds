package me.lancer.sevenpounds.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.PresenterFragment;
import me.lancer.sevenpounds.mvp.code.CodeBean;
import me.lancer.sevenpounds.mvp.code.CodePresenter;
import me.lancer.sevenpounds.mvp.code.ICodeView;
import me.lancer.sevenpounds.ui.adapter.CodeAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class CodeOrganFragment extends PresenterFragment<CodePresenter> implements ICodeView {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    CodeAdapter mAdapter;

    LinearLayoutManager mLinearLayoutManager;
    List<CodeBean> mList = new ArrayList<>();

    int pager = 1, last = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        if (pager == 1) {
                            mList = (List<CodeBean>) msg.obj;
                            mAdapter = new CodeAdapter(getActivity(), mList);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mList.addAll((List<CodeBean>) msg.obj);
                            for (int i = 0; i < 100; i++) {
                                mAdapter.notifyItemInserted(pager*100 + i);
                            }
                        }
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    Runnable loadOrganizations = new Runnable() {
        @Override
        public void run() {
            presenter.loadOrganizations(pager);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initData() {
        new Thread(loadOrganizations).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pager = 1;
                new Thread(loadOrganizations).start();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CodeAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == mAdapter.getItemCount()) {
                    pager += 1;
                    new Thread(loadOrganizations).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                last = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected CodePresenter onCreatePresenter() {
        return new CodePresenter(this);
    }


    @Override
    public void showUsers(List<CodeBean> list) {

    }

    @Override
    public void showOrganizations(List<CodeBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showRepositories(List<CodeBean> list) {

    }

    @Override
    public void showDetail(CodeBean bean) {

    }

    @Override
    public void showMsg(String log) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = log;
        handler.sendMessage(msg);
    }

    @Override
    public void showLoad() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void hideLoad() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }
}
