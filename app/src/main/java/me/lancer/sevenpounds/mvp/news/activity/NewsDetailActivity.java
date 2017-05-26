package me.lancer.sevenpounds.mvp.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import me.lancer.sevenpounds.mvp.news.adapter.NewsAdapter;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlTextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterActivity;
import me.lancer.sevenpounds.mvp.news.INewsView;
import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.mvp.news.NewsPresenter;
import me.lancer.sevenpounds.util.LruImageCache;

public class NewsDetailActivity extends PresenterActivity<NewsPresenter> implements INewsView {

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<NewsBean> mList = new ArrayList<>();

    private CollapsingToolbarLayout layout;
    private NetworkImageView ivImg;
    private HtmlTextView htvContent;
    private LoadToast loadToast;

    private RequestQueue mQueue;
    private int id;
    private String title, img, link;

    private  Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    loadToast.error();
                    break;
                case 3:
                    if (msg.obj != null) {
                        loadToast.success();
                        NewsBean nb = (NewsBean) msg.obj;
                        layout.setTitle(nb.getTitle());
                        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
                        LruImageCache cache = LruImageCache.instance();
                        ImageLoader loader = new ImageLoader(mQueue, cache);
                        ivImg.setImageUrl(nb.getImg(), loader);
                        if (nb.getContent()!=null) {
                            htvContent.setHtml(nb.getContent(), new HtmlHttpImageGetter(htvContent));
                        }
                    }
                    break;
                case 4:
                    if (msg.obj != null) {
                        loadToast.success();
                        mList.clear();
                        mList.addAll((List<NewsBean>) msg.obj);
                        mAdapter = new NewsAdapter(NewsDetailActivity.this, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    break;
            }
        }
    };

    private Runnable loadDetail = new Runnable() {
        @Override
        public void run() {
            presenter.loadDetail(link);
        }
    };

    private Runnable loadItem = new Runnable() {
        @Override
        public void run() {
            presenter.loadItem(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large);
        initData();
        initView();
    }

    private void initData() {
        mQueue = ((mApp)getApplication()).getRequestQueue();
        id = getIntent().getIntExtra("id", 2);
        title = getIntent().getStringExtra("title");
        img = getIntent().getStringExtra("img");
        link = getIntent().getStringExtra("link");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_large);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layout = (CollapsingToolbarLayout) findViewById(R.id.ctl_large);
        layout.setTitle(title);
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        htvContent = (HtmlTextView) findViewById(R.id.htv_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NewsAdapter(this, mList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();
        if (link != null && !link.equals("")) {
            htvContent.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            new Thread(loadDetail).start();
        }else{
            htvContent.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            new Thread(loadItem).start();
        }
    }

    public static void startActivity(Activity activity, int id, String title, String img, String link, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, NewsDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("img", img);
        intent.putExtra("link", link);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, mParams.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        ivImg.destroyDrawingCache();
        htvContent.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    protected NewsPresenter onCreatePresenter() {
        return new NewsPresenter(this);
    }

    @Override
    public void showHotest(List<NewsBean> list) {

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
    public void showItem(List<NewsBean> list) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showDetail(NewsBean bean) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = bean;
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


    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }
}
