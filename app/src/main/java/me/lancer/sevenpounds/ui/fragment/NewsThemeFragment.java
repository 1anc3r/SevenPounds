package me.lancer.sevenpounds.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.PresenterFragment;
import me.lancer.sevenpounds.mvp.news.INewsView;
import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.mvp.news.NewsPresenter;
import me.lancer.sevenpounds.ui.adapter.NewsAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class NewsThemeFragment extends PresenterFragment<NewsPresenter> implements INewsView {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    NewsAdapter mAdapter;

    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    List<NewsBean> mList = new ArrayList<>();

    int last = 0, flag = 0;
    final int[] typeint = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13};
    final String[] typestr = {"— 游戏 · 发现 —",
            "— 电影 · 发现 —", "— 设计 · 发现 —",
            "— 企业 · 发现 —", "— 财经 · 发现 —",
            "— 音乐 · 发现 —", "— 体育 · 发现 —",
            "— 动漫 · 发现 —", "— 安全 · 发现 —",
            "— 娱乐 · 发现 —", "— 心理 · 发现 —"};

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
                        mList.clear();
                        mList.add(new NewsBean(1, typestr[flag]));
                        mList.addAll((List<NewsBean>) msg.obj);
                        mAdapter = new NewsAdapter(getActivity(), mList);
                        mRecyclerView.setAdapter(mAdapter);
//                        for (int i = 0; i < ((List<NewsBean>) msg.obj).size()+1; i++) {
//                            mAdapter.notifyItemInserted(i);
//                        }
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 4:case 5:case 6:case 7:case 8:
                case 9:case 10:case 11:case 12:case 13:
                    int len = mList.size();
                    mList.add(new NewsBean(1, typestr[flag]));
                    mList.addAll((List<NewsBean>) msg.obj);
                    for (int i = 0; i < ((List<NewsBean>) msg.obj).size()+1; i++) {
                        mAdapter.notifyItemInserted(len + i);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    Runnable loadTheme = new Runnable() {
        @Override
        public void run() {
            presenter.loadTheme(typeint[flag]);
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
        new Thread(loadTheme).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessageDelayed(msg, 800);
//                flag = 0;
//                new Thread(loadTheme).start();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NewsAdapter(getActivity(), mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == mAdapter.getItemCount()) {
                    if (flag < 9) {
                        flag += 1;
                        new Thread(loadTheme).start();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                last = getMax(mStaggeredGridLayoutManager.findLastVisibleItemPositions(new int[mStaggeredGridLayoutManager.getSpanCount()]));
            }
        });
    }

    private int getMax(int[] arr) {
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    @Override
    protected NewsPresenter onCreatePresenter() {
        return new NewsPresenter(this);
    }


    @Override
    public void showTheme(List<NewsBean> list) {
        Message msg = new Message();
        msg.what = flag + 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showDetail(NewsBean bean) {

    }

    @Override
    public void showTopNews(List<NewsBean> list) {

    }

    @Override
    public void showLatest(List<NewsBean> list) {

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
