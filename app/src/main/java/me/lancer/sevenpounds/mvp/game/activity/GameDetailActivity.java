package me.lancer.sevenpounds.mvp.game.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterActivity;
import me.lancer.sevenpounds.mvp.game.GameBean;
import me.lancer.sevenpounds.mvp.game.GamePresenter;
import me.lancer.sevenpounds.mvp.game.IGameView;
import me.lancer.sevenpounds.mvp.game.adapter.ShotAdapter;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlTextView;
import me.lancer.sevenpounds.util.LruImageCache;

public class GameDetailActivity extends PresenterActivity<GamePresenter> implements IGameView {

    private NetworkImageView ivImg;
    private TextView tvDiscount, tvOriginal, tvFinal, tvDevelopers, tvPublishers;
    private HtmlTextView htvLanguages,htvDescription, htvRequirements;
    private RecyclerView rvList;
    private ShotAdapter adapter;
    private List<String> shots = new ArrayList<>();
    private LoadToast loadToast;

    private RequestQueue mQueue;
    private String title, img;
    private int id;

    private Handler handler = new Handler() {
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
                        GameBean bb = (GameBean) msg.obj;
                        tvDiscount.setText("-"+bb.getDiscountPercent()+"%");
                        tvOriginal.setText(""+bb.getOriginalPrice());
                        tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvFinal.setText(""+bb.getFinalPrice());
                        tvDevelopers.setText(bb.getDevelopers());
                        tvPublishers.setText(bb.getPublishers());
                        htvLanguages.setHtml(bb.getSupportedLanguages(), new HtmlHttpImageGetter(htvLanguages));
                        htvDescription.setHtml(bb.getDescription(), new HtmlHttpImageGetter(htvDescription));
                        htvRequirements.setHtml(bb.getRequirements(), new HtmlHttpImageGetter(htvRequirements));
                        shots = bb.getScreenshots();
                        adapter = new ShotAdapter(GameDetailActivity.this, shots);
                        rvList.setAdapter(adapter);
                    }
                    break;
            }
        }
    };

    private Runnable loadDetail = new Runnable() {
        @Override
        public void run() {
            presenter.loadDetail(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initData();
        initView();
    }

    private void initData() {
        mQueue = ((mApp)getApplication()).getRequestQueue();
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        img = getIntent().getStringExtra("img");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_large);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        tvDiscount = (TextView) findViewById(R.id.tv_discount);
        tvOriginal = (TextView) findViewById(R.id.tv_original);
        tvFinal = (TextView) findViewById(R.id.tv_final);
        tvDevelopers = (TextView) findViewById(R.id.tv_developers);
        tvPublishers = (TextView) findViewById(R.id.tv_publishers);
        htvLanguages = (HtmlTextView) findViewById(R.id.htv_languages);
        htvDescription = (HtmlTextView) findViewById(R.id.htv_description);
        htvRequirements = (HtmlTextView) findViewById(R.id.htv_requirements);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(llm);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShotAdapter(this, shots);
        rvList.setAdapter(adapter);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();
        new Thread(loadDetail).start();
    }

    public static void startActivity(Activity activity, int id, String title, String img, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, GameDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("img", img);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, mParams.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivImg.destroyDrawingCache();
        htvDescription.destroyDrawingCache();
        htvRequirements.destroyDrawingCache();
    }

    @Override
    protected GamePresenter onCreatePresenter() {
        return new GamePresenter(this);
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
    public void showFeatured(List<GameBean> list) {

    }

    @Override
    public void showCategories(List<GameBean> list) {

    }

    @Override
    public void showDetail(GameBean bean) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = bean;
        handler.sendMessage(msg);
    }
}
