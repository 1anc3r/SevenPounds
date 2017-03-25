package me.lancer.sevenpounds.mvp.photo.fragment;

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
import me.lancer.sevenpounds.mvp.photo.IPhotoView;
import me.lancer.sevenpounds.mvp.photo.PhotoBean;
import me.lancer.sevenpounds.mvp.photo.PhotoPresenter;
import me.lancer.sevenpounds.mvp.photo.adapter.PhotoAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class PhotoThemeFragment extends PresenterFragment<PhotoPresenter> implements IPhotoView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private PhotoAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<PhotoBean> mList = new ArrayList<>();

    private int last = 0, flag = 0, load = 0;
    private final String[] typeen = {"art", "beach", "car", "ocean", "sunset", "business", "flowers",
            "music", "sport", "technology", "animal", "black-and-white", "mountains", "road", "universe",
            "abstract", "fashion", "landscape", "night", "people", "coffee", "photography", "sky",
            "vintage", "waterfall", "city", "food", "love", "summer", "travel", "desert"};
    private final String[] typezn = {"— 艺术 —", "— 沙滩 —", "— 跑车 —", "— 海洋 —", "— 夕阳 —", "— 商务 —", "— 花草 —",
            "— 音乐 —", "— 运动 —", "— 科技 —", "— 动物 —", "— 黑白 —", "— 山脉 —", "— 道路 —", "— 宇宙 —",
            "— 抽象 —", "— 时尚 —", "— 景观 —", "— 夜景 —", "— 人像 —", "— 咖啡 —", "— 摄影 —", "— 天空 —",
            "— 复古 —", "— 瀑布 —", "— 城市 —", "— 美食 —", "— 爱情 —", "— 盛夏 —", "— 旅途 —", "— 沙漠 —"};

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
                        mList.add(new PhotoBean(1, typezn[flag]));
                        mList.addAll((List<PhotoBean>) msg.obj);
                        mAdapter = new PhotoAdapter(getActivity(), mList);
                        mRecyclerView.setAdapter(mAdapter);
//                        for (int i = 0; i < ((List<PhotoBean>) msg.obj).size()+1; i++) {
//                            mAdapter.notifyItemInserted(i);
//                        }
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
                case 13:
                    int len = mList.size();
                    mList.add(new PhotoBean(1, typezn[flag]));
                    mList.addAll((List<PhotoBean>) msg.obj);
                    for (int i = 0; i < ((List<PhotoBean>) msg.obj).size() + 1; i++) {
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
            presenter.loadTheme(typeen[flag]);
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
        mAdapter = new PhotoAdapter(getActivity(), mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int Phototate) {
                super.onScrollStateChanged(recyclerView, Phototate);
                if (Phototate == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == mAdapter.getItemCount()) {
                    if (flag < 30 && load == 0) {
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
    protected PhotoPresenter onCreatePresenter() {
        return new PhotoPresenter(this);
    }

    @Override
    public void showLatest(List<PhotoBean> list) {

    }

    @Override
    public void showTheme(List<PhotoBean> list) {
        Message msg = new Message();
        msg.what = flag + 3;
        msg.obj = list;
        handler.sendMessage(msg);
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
