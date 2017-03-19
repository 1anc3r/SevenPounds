package me.lancer.sevenpounds.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.PresenterActivity;
import me.lancer.sevenpounds.mvp.code.ICodeView;
import me.lancer.sevenpounds.mvp.code.CodeBean;
import me.lancer.sevenpounds.mvp.code.CodePresenter;
import me.lancer.sevenpounds.ui.adapter.CodeAdapter;
import me.lancer.sevenpounds.util.AppConstants;
import me.lancer.sevenpounds.util.LruImageCache;

public class CodeDetailActivity extends PresenterActivity<CodePresenter> implements ICodeView {

    NetworkImageView ivImg;
    TextView tvName, tvStar, tvRank;
    RecyclerView rvRepositories;
    LoadToast loadToast;

    CodeAdapter mAdapter;

    LinearLayoutManager mLinearLayoutManager;
    List<CodeBean> mList = new ArrayList<>();

    private RequestQueue mQueue;
    private int type;
    private String title, star, rank, img, link;

    Handler handler = new Handler() {
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
                        CodeBean bb = (CodeBean) msg.obj;
                        mList = bb.getRepositories();
                        mAdapter = new CodeAdapter(CodeDetailActivity.this, mList);
                        rvRepositories.setAdapter(mAdapter);
                    }
                    break;
            }
        }
    };

    Runnable loadDetail = new Runnable() {
        @Override
        public void run() {
            presenter.loadDetail(link);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);
        initData();
        initView();
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);
        title = getIntent().getStringExtra("title");
        star = getIntent().getStringExtra("star");
        rank = getIntent().getStringExtra("rank");
        img = getIntent().getStringExtra("img");
        link = getIntent().getStringExtra("link");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, AppConstants.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(title);
        tvStar = (TextView) findViewById(R.id.tv_star);
        tvStar.setText("Star : "+star);
        tvRank = (TextView) findViewById(R.id.tv_rank);
        tvRank.setText("Rank : "+rank);
        rvRepositories = (RecyclerView) findViewById(R.id.rv_repositories);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvRepositories.setLayoutManager(mLinearLayoutManager);
        rvRepositories.setItemAnimator(new DefaultItemAnimator());
        rvRepositories.setHasFixedSize(true);
        mAdapter = new CodeAdapter(this, mList);
        rvRepositories.setAdapter(mAdapter);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();
        new Thread(loadDetail).start();
    }

    public static void startActivity(Activity activity, String title, String star, String rank, String img, String link, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, CodeDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("star", star);
        intent.putExtra("rank", rank);
        intent.putExtra("img", img);
        intent.putExtra("link", link);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, AppConstants.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        ivImg.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    protected CodePresenter onCreatePresenter() {
        return new CodePresenter(this);
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
    public void showUsers(List<CodeBean> list) {

    }

    @Override
    public void showOrganizations(List<CodeBean> list) {

    }

    @Override
    public void showRepositories(List<CodeBean> list) {

    }

    @Override
    public void showDetail(CodeBean bean) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = bean;
        handler.sendMessage(msg);
    }
}
