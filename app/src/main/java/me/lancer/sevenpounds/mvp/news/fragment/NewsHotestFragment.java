package me.lancer.sevenpounds.mvp.news.fragment;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterFragment;
import me.lancer.sevenpounds.mvp.news.INewsView;
import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.mvp.news.NewsPresenter;
import me.lancer.sevenpounds.mvp.news.adapter.NewsAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class NewsHotestFragment extends PresenterFragment<NewsPresenter> implements INewsView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private NewsAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<NewsBean> mList = new ArrayList<>();

    private int last = 0, flag = 0, load = 0;
    private String date;

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
                        mList.clear();
                        mList.addAll((List<NewsBean>) msg.obj);
                        mAdapter = new NewsAdapter(getActivity(), mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadHotest = new Runnable() {
        @Override
        public void run() {
            presenter.loadHotest();
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
        new Thread(loadHotest).start();
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
//                new Thread(loadLatest).start();
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
    }

    @Override
    protected NewsPresenter onCreatePresenter() {
        return new NewsPresenter(this);
    }


    @Override
    public void showItem(List<NewsBean> list) {

    }

    @Override
    public void showDetail(NewsBean bean) {

    }

    @Override
    public void showHotest(List<NewsBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showLatest(List<NewsBean> list) {
        
    }

    @Override
    public void showBefore(List<NewsBean> list) {
        
    }

    @Override
    public void showList(List<NewsBean> list) {

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

