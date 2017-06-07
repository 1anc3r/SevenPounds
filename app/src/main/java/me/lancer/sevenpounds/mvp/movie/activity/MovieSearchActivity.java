package me.lancer.sevenpounds.mvp.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.PresenterActivity;
import me.lancer.sevenpounds.mvp.movie.IMovieView;
import me.lancer.sevenpounds.mvp.movie.MovieBean;
import me.lancer.sevenpounds.mvp.movie.MoviePresenter;
import me.lancer.sevenpounds.mvp.movie.adapter.MovieAdapter;
import me.lancer.sevenpounds.ui.activity.AboutActivity;
import me.lancer.sevenpounds.ui.activity.MainActivity;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.sevenpounds.ui.view.htmltextview.HtmlTextView;
import me.lancer.sevenpounds.util.LruImageCache;

public class MovieSearchActivity extends PresenterActivity<MoviePresenter> implements IMovieView {

    Toolbar toolbar;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private MovieAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    private List<MovieBean> mList = new ArrayList<>();

    private String keyword;

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
                        mList = (List<MovieBean>) msg.obj;
                        mAdapter = new MovieAdapter(MovieSearchActivity.this, mList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadQuery = new Runnable() {
        @Override
        public void run() {
            presenter.loadTopMovie(keyword);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("搜索结果");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_result);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(loadQuery).start();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_result);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        keyword = getIntent().getStringExtra("query");
        if (keyword != null) {
            new Thread(loadQuery).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("搜索...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                new Thread(loadQuery).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent0 = new Intent();
                intent0.putExtra("link", "https://github.com/1anc3r");
                intent0.putExtra("title", "Github");
                intent0.setClass(this, AboutActivity.class);
                startActivity(intent0);
                break;
            case R.id.menu_blog:
                Intent intent1 = new Intent();
                intent1.putExtra("link", "https://www.1anc3r.me");
                intent1.putExtra("title", "Blog");
                intent1.setClass(this, AboutActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    protected MoviePresenter onCreatePresenter() {
        return new MoviePresenter(this);
    }


    @Override
    public void showReviewer(List<MovieBean> list) {

    }

    @Override
    public void showTopMovie(List<MovieBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showReviewerDetail(MovieBean bean) {

    }

    @Override
    public void showTopDetail(MovieBean bean) {

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
