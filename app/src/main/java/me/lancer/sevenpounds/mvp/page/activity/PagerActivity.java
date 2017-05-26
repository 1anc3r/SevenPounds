package me.lancer.sevenpounds.mvp.page.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterActivity;
import me.lancer.sevenpounds.mvp.page.IPageView;
import me.lancer.sevenpounds.mvp.page.PageBean;
import me.lancer.sevenpounds.mvp.page.PagePresenter;
import me.lancer.sevenpounds.mvp.page.fragment.PagerFragment;

public class PagerActivity extends PresenterActivity<PagePresenter> implements IPageView {

    private String link;
    private List<PageBean> mData = new ArrayList<PageBean>();
    private PageAdapter mAdapter;
    private ViewPager viewPager;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        mData = (List<PageBean>) msg.obj;
                        mAdapter.refreshData(mData);
                    }
                    break;
            }
        }
    };

    private Runnable loadTop = new Runnable() {
        @Override
        public void run() {
            presenter.loadList(link);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        init();
    }

    public void init(){
        link = getIntent().getStringExtra("link");
        mAdapter = new PageAdapter(mData, getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        new Thread(loadTop).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected PagePresenter onCreatePresenter() {
        return new PagePresenter(this);
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

    @Override
    public void showList(List<PageBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    class PageAdapter extends FragmentPagerAdapter{

        List<PageBean> data;

        public PageAdapter(List<PageBean> data, FragmentManager fm) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return PagerFragment.newInstance(data.get(position).getLink());
        }

        @Override
        public int getCount() {
            return data.size();
        }

        public void refreshData(List<PageBean> newData) {
            data = newData;
            notifyDataSetChanged();
        }
    }
}
