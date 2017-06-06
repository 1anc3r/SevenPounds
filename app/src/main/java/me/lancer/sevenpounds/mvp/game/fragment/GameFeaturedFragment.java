package me.lancer.sevenpounds.mvp.game.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.fragment.PresenterFragment;
import me.lancer.sevenpounds.mvp.game.IGameView;
import me.lancer.sevenpounds.mvp.game.GameBean;
import me.lancer.sevenpounds.mvp.game.GamePresenter;
import me.lancer.sevenpounds.mvp.game.adapter.GameAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class GameFeaturedFragment extends PresenterFragment<GamePresenter> implements IGameView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private GameAdapter mAdapter;

    private GridLayoutManager mGridLayoutManager;
    private List<GameBean> mList = new ArrayList<>();

    private int pager = 0, last = 0;

    private Handler handler = new Handler() {
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
                        if (pager == 0) {
                            mList = (List<GameBean>) msg.obj;
                            mAdapter = new GameAdapter(getActivity(), 1, mList);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mList.addAll((List<GameBean>) msg.obj);
                            for (int i = 0; i < 10; i++) {
                                mAdapter.notifyItemInserted(pager*10 + i);
                            }
                        }
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadFeatured = new Runnable() {
        @Override
        public void run() {
            presenter.loadFeatured();
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
        new Thread(loadFeatured).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pager = 0;
                new Thread(loadFeatured).start();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mGridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GameAdapter(getActivity(), 1, mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected GamePresenter onCreatePresenter() {
        return new GamePresenter(this);
    }


    @Override
    public void showFeatured(List<GameBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showCategories(List<GameBean> list) {

    }

    @Override
    public void showDetail(GameBean bean) {

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
