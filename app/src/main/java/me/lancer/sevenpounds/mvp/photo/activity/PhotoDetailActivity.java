package me.lancer.sevenpounds.mvp.photo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterActivity;
import me.lancer.sevenpounds.mvp.photo.IPhotoView;
import me.lancer.sevenpounds.mvp.photo.PhotoBean;
import me.lancer.sevenpounds.mvp.photo.PhotoPresenter;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.util.LruImageCache;

public class PhotoDetailActivity extends PresenterActivity<PhotoPresenter> implements IPhotoView {

    private NetworkImageView ivImg;
    private FrameLayout flPhoto;
    private Button btnShare, btnDownload;
    private LoadToast loadToast;

    private RequestQueue mQueue;
    private String img, title;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    loadToast.success();
                    break;
                case 1:
                    loadToast.show();
                    break;
            }
        }
    };

//    private Runnable loadTheme = new Runnable() {
//        @Overridez
//        public void run() {
//            presenter.loadTheme(typeen[flag]);
//        }
//    };z

    private Runnable download = new Runnable() {
        @Override
        public void run() {
            presenter.download(img, title);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_photo);
        initData();
        initView();
    }

    private void initData() {
        mQueue = ((mApp)getApplication()).getRequestQueue();
        img = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_large);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        flPhoto = (FrameLayout) findViewById(R.id.fl_photo);
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        btnShare = (Button) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(vOnClickListener);
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(vOnClickListener);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();

        Message msg = new Message();
        msg.what = 0;
        handler.sendMessageDelayed(msg, 800);
    }

    @Override
    protected PhotoPresenter onCreatePresenter() {
        return new PhotoPresenter(this);
    }

    private View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == btnShare) {

            } else if (view == btnDownload) {
                new Thread(download).start();
            }
        }
    };

    public static void startActivity(Activity activity, String img, String title, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, PhotoDetailActivity.class);
        intent.putExtra("img", img);
        intent.putExtra("title", title);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, mParams.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        ivImg.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    public void showMsg(String log) {
        showSnackbar(flPhoto, log);
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
    public void showLatest(List<PhotoBean> list) {

    }

    @Override
    public void showTheme(List<PhotoBean> list) {

    }

    @Override
    public void showWelfare(List<PhotoBean> list) {

    }
}
