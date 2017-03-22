package me.lancer.sevenpounds.mvp.game.fragment;

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
import me.lancer.sevenpounds.mvp.base.activity.PresenterFragment;
import me.lancer.sevenpounds.mvp.game.GameBean;
import me.lancer.sevenpounds.mvp.game.GamePresenter;
import me.lancer.sevenpounds.mvp.game.IGameView;
import me.lancer.sevenpounds.mvp.game.adapter.GameAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class GameThemeFragment extends PresenterFragment<GamePresenter> implements IGameView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private GameAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<GameBean> mList = new ArrayList<>();

    private int last = 0, flag = 0, load = 0;
    private final String[] typemenu = {"Action", "Strategy", "RPG", "Indie", "Adventure",
            "Sports", "Simulation", "Early+Access", "Massively", "Free"};
    private final String[] typestr = {"— 动作 —", "— 战略 —", "— 角色扮演 —", "— 独立 —", "— 冒险 —",
            "— 体育 —", "— 模拟 —", "— 抢先体验 —", "— 多人在线 —", "— 免费 —"};

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
                        mList.add(new GameBean(1, typestr[flag]));
                        mList.addAll((List<GameBean>) msg.obj);
                        mAdapter = new GameAdapter(getActivity(), 0, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    load = 0;
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    int len = mList.size();
                    mList.add(new GameBean(1, typestr[flag]));
                    mList.addAll((List<GameBean>) msg.obj);
                    for (int i = 0; i < ((List<GameBean>) msg.obj).size() + 1; i++) {
                        mAdapter.notifyItemInserted(len + i);
                    }
                    load = 0;
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadTheme = new Runnable() {
        @Override
        public void run() {
            presenter.loadTheme(typemenu[flag]);
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
        mAdapter = new GameAdapter(getActivity(), 0, mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == mAdapter.getItemCount()) {
                    if (flag < 9 && load == 0) {
                        load = 1;
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
    protected GamePresenter onCreatePresenter() {
        return new GamePresenter(this);
    }


    @Override
    public void showTopGame(List<GameBean> list) {

    }

    @Override
    public void showTheme(List<GameBean> list) {
        Message msg = new Message();
        msg.what = flag + 3;
        msg.obj = list;
        handler.sendMessage(msg);
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
