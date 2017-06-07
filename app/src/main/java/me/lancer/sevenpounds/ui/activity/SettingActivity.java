package me.lancer.sevenpounds.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.polaric.colorful.ColorPickerDialog;
import org.polaric.colorful.Colorful;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.BaseActivity;
import me.lancer.sevenpounds.mvp.repository.RepositoryBean;
import me.lancer.sevenpounds.mvp.repository.adapter.RepositoryAdapter;
import me.lancer.sevenpounds.ui.adapter.SettingAdapter;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.util.ContentGetterSetter;

public class SettingActivity extends BaseActivity {

    private mApp app;

    private LinearLayout llNight, llTheme, llFunc, llProblem, llFeedback, llDownload, llAboutUs;
    private Button btnLoginOut;
    private SwitchCompat scNight;
    private BottomSheetDialog listDialog;
    private AlertDialog aboutDialog;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean night = false;

    private List<String> funcList = new ArrayList<>(), problemList = new ArrayList<>();
    private List<RepositoryBean> reList = new ArrayList<>();
    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    private final String root = Environment.getExternalStorageDirectory() + "/";

    private Runnable repository = new Runnable() {
        @Override
        public void run() {
            String content = contentGetterSetter.getContentFromHtml("repository", "https://raw.githubusercontent.com/1anc3r/1anc3r-s-Programming-Journey/master/AppLink.md");
            if (!content.contains("获取失败!")) {
                try {
                    List<RepositoryBean> list = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(content);
                    JSONArray jsonArr = jsonObj.getJSONArray("apps");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        RepositoryBean bean = new RepositoryBean();
                        JSONObject jsonItem = jsonArr.getJSONObject(i);
                        bean.setImg(jsonItem.getString("img"));
                        bean.setName(jsonItem.getString("name"));
                        bean.setDescription(jsonItem.getString("description"));
                        bean.setDownload(jsonItem.getString("download"));
                        bean.setBlog(jsonItem.getString("blog"));
                        list.add(bean);
                    }
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = list;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj != null) {
                        reList = (List<RepositoryBean>) msg.obj;
                        showRepositoryDialog(reList);
                        progressDialog.dismiss();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    private void initView() {
        initToolbar(getString(R.string.settingcn));
        llNight = (LinearLayout) findViewById(R.id.ll_night);
        llNight.setOnClickListener(vOnClickListener);
        llTheme = (LinearLayout) findViewById(R.id.ll_theme);
        llTheme.setOnClickListener(vOnClickListener);
        llFunc = (LinearLayout) findViewById(R.id.ll_func);
        llFunc.setOnClickListener(vOnClickListener);
        llProblem = (LinearLayout) findViewById(R.id.ll_problem);
        llProblem.setOnClickListener(vOnClickListener);
        llFeedback = (LinearLayout) findViewById(R.id.ll_feedback);
        llFeedback.setOnClickListener(vOnClickListener);
        llDownload = (LinearLayout) findViewById(R.id.ll_download);
        llDownload.setOnClickListener(vOnClickListener);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llAboutUs.setOnClickListener(vOnClickListener);
        btnLoginOut = (Button) findViewById(R.id.btn_login_out);
        btnLoginOut.setOnClickListener(vOnClickListener);
        scNight = (SwitchCompat) findViewById(R.id.sc_night);
        progressDialog = new ProgressDialog(SettingActivity.this);
        progressDialog.setMessage("正在加载，请稍后...");
        progressDialog.setCancelable(false);
        showAboutDialog();
    }

    private void initData() {
        app = (mApp) getApplication();
        sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        night = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
        scNight.setChecked(night);
        scNight.setClickable(false);
        funcList.add("见闻 : \n" +
                "\t\t\t\t每日 : 知乎日报的每日信息\n" +
                "\t\t\t\t热门 : 知乎日报的热门信息\n" +
                "\t\t\t\t分类 : 包括动漫、游戏、财经、电影、音乐、互联网安全等日报\n" +
                "\t\t\t\t — 数据来源 : 知乎日报\n\t\t\t\t（http://news-at.zhihu.com/api）");
        funcList.add("图片 : \n" +
                "\t\t\t\t佳人 : 豆瓣读书的最受欢迎书评\n" +
                "\t\t\t\t美图 : 爬取呈现豆瓣图书TOP250\n" +
                "\t\t\t\t — 数据来源 : " +
                "\n\t\t\t\t 佳人 : Gank.io\n\t\t\t\t（http://gank.io）" +
                "\n\t\t\t\t 美图 : Pexels Popular Photos\n\t\t\t\t（https://www.pexels.com）");
        funcList.add("读书 : \n" +
                "\t\t\t\t书评 : 豆瓣读书的最受欢迎书评\n" +
                "\t\t\t\t书榜 : 爬取呈现豆瓣图书TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣读书\n\t\t\t\t（https://book.douban.com）");
        funcList.add("听音 : \n" +
                "\t\t\t\t乐评 : 豆瓣音乐的最受欢迎乐评\n" +
                "\t\t\t\t乐榜 : 爬取呈现豆瓣音乐TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣音乐\n\t\t\t\t（https://music.douban.com）");
        funcList.add("观影 : \n" +
                "\t\t\t\t影评 : 豆瓣电影的最受欢迎影评\n" +
                "\t\t\t\t影榜 : 爬取呈现豆瓣电影TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣电影\n\t\t\t\t（https://movie.douban.com）");
        funcList.add("视频 : \n" +
                "\t\t\t\t分类 : 来自B站各分区排行榜前十\n" +
                "\t\t\t\t — 数据来源 : BiliBili\n\t\t\t\t（http://api.bilibili.com）");
        funcList.add("漫画 : \n" +
                "\t\t\t\t推荐 : 推荐好看的漫画\n" +
                "\t\t\t\t排行 : 漫画排行榜\n" +
                "\t\t\t\t分类 : 来自有妖气各分区排行榜\n" +
                "\t\t\t\t — 数据来源 : 有妖气\n\t\t\t\t（https://www.u17.com）");
        funcList.add("游戏 : \n" +
                "\t\t\t\t精选 : 精选各大平台热门游戏\n" +
                "\t\t\t\t优惠、热销、新品、即将推出\n" +
                "\t\t\t\t — 数据来源 : Steam\n\t\t\t\t（https://store.steampowered.com）");
        funcList.add("编程 : \n" +
                "\t\t\t\t个人 : GitHub上Star最多的个人\n" +
                "\t\t\t\t组织 : GitHub上Star最多的组织\n" +
                "\t\t\t\t项目 : GitHub上Star最多的项目\n" +
                "\t\t\t\t趋势 : GitHub上今日最热的项目\n" +
                "\t\t\t\t — 数据来源 : GithubRanking\n\t\t\t\t（https://github-ranking.com）");
        problemList.add("遇到Bug不要憋在心里∑(っ°Д°)っ\n请发送邮件至huangfangzhi0@foxmail.com");
    }

    private View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == llNight) {
                if (!night) {
//                    editor.putBoolean(mParams.ISNIGHT, true);
//                    editor.apply();
//                    scNight.setChecked(true);
//                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    recreate();
                    scNight.setChecked(true);
                    editor.putBoolean(mParams.ISNIGHT, true);
                    editor.apply();
                    Colorful.config(SettingActivity.this)
                            .translucent(false)
                            .dark(true)
                            .apply();
                    recreate();
                } else {
//                    editor.putBoolean(mParams.ISNIGHT, false);
//                    editor.apply();
//                    scNight.setChecked(false);
//                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    recreate();
                    scNight.setChecked(false);
                    editor.putBoolean(mParams.ISNIGHT, false);
                    editor.apply();
                    Colorful.config(SettingActivity.this)
                            .translucent(false)
                            .dark(false)
                            .apply();
                    recreate();
                }
                night = !night;
            } else if (v == llTheme) {
                ColorPickerDialog dialog = new ColorPickerDialog(SettingActivity.this);
                dialog.setTitle("切换主题");
                dialog.setOnColorSelectedListener(new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(Colorful.ThemeColor themeColor) {
                        Colorful.config(SettingActivity.this)
                                .primaryColor(themeColor)
                                .accentColor(themeColor)
                                .translucent(false)
                                .dark(night)
                                .apply();
                        recreate();
                    }
                });
                dialog.show();
            } else if (v == llFunc) {
                showListDialog(1, funcList);
            } else if (v == llProblem) {
                showListDialog(2, problemList);
            } else if (v == llFeedback) {

            } else if (v == llDownload) {
                new Thread(repository).start();
                progressDialog.show();
            } else if (v == llAboutUs) {
                aboutDialog.show();
            } else if (v == btnLoginOut) {
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent().setClass(mActivity, MainActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent().setClass(SettingActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showListDialog(int type, List<String> list) {
        View listDialogView = View.inflate(mActivity, R.layout.list_dialog, null);
        TextView tvType = (TextView) listDialogView.findViewById(R.id.tv_type);
        switch (type) {
            case 1:
                tvType.setText("功能介绍");
                break;
            case 2:
                tvType.setText("常见问题");
                break;
        }
        RecyclerView rvList = (RecyclerView) listDialogView.findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter = new SettingAdapter(list);
        rvList.setAdapter(adapter);

        listDialog = new BottomSheetDialog(mActivity);
        listDialog.setContentView(listDialogView);
        listDialog.show();
    }

    private void showAboutDialog() {
        View aboutDialogView = LayoutInflater.from(mActivity).inflate(R.layout.about_dialog, null);
        TextView tvOrganization = (TextView) aboutDialogView.findViewById(R.id.tv_organization);
        tvOrganization.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.xiyoumobile.com/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvBlog = (TextView) aboutDialogView.findViewById(R.id.tv_blog);
        tvBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.1anc3r.me");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(aboutDialogView);
        aboutDialog = builder.create();
    }

    private void showRepositoryDialog(List<RepositoryBean> list) {
        View listDialogView = View.inflate(mActivity, R.layout.list_dialog, null);
        TextView tvType = (TextView) listDialogView.findViewById(R.id.tv_type);
        tvType.setText("我的作品");
        RecyclerView rvList = (RecyclerView) listDialogView.findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter = new RepositoryAdapter(this, list);
        rvList.setAdapter(adapter);
        listDialog = new BottomSheetDialog(mActivity);
        listDialog.setContentView(listDialogView);
        listDialog.show();
    }
}
