package me.lancer.sevenpounds.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.BaseActivity;
import me.lancer.sevenpounds.ui.adapter.SettingAdapter;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;

public class SettingActivity extends BaseActivity {

    private mApp app;

    private LinearLayout llNight, llFunc, llProblem, llFeedback, llDownload, llAboutUs;
    private Button btnLoginOut;
    private SwitchCompat scNight;
    private BottomSheetDialog listDialog;
    private AlertDialog aboutDialog, downloadDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isNight = false;

    private List<String> funcList = new ArrayList<>(), problemList = new ArrayList<>();

    private final String root = Environment.getExternalStorageDirectory() + "/";

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
        showAboutDialog();
        showDownloadDialog();
    }

    private void initData() {
        app = (mApp) getApplication();
        sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isNight = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
        scNight.setChecked(isNight);
        scNight.setClickable(false);
        funcList.add("见闻如是说 : \n" +
                "\t\t\t\t每日 : 提供来自知乎社区的精选问答，还有国内一流媒体的专栏特稿\n" +
                "\t\t\t\t分类 : 包括动漫、游戏、财经、电影、音乐、互联网安全等丰富内容\n" +
                "\t\t\t\t — 数据来源 : 知乎日报\n\t\t\t\t（http://news-at.zhihu.com/api）");
        funcList.add("读书如抽丝 : \n" +
                "\t\t\t\t书评 : 豆瓣读书的最受欢迎书评\n" +
                "\t\t\t\t书榜 : 爬取呈现豆瓣图书TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣读书\n\t\t\t\t（https://book.douban.com/）");
        funcList.add("听音如沐风 : \n" +
                "\t\t\t\t乐评 : 豆瓣音乐的最受欢迎乐评\n" +
                "\t\t\t\t乐榜 : 爬取呈现豆瓣音乐TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣音乐\n\t\t\t\t（https://music.douban.com/）");
        funcList.add("观影如造梦 : \n" +
                "\t\t\t\t影评 : 豆瓣电影的最受欢迎影评\n" +
                "\t\t\t\t影榜 : 爬取呈现豆瓣电影TOP250\n" +
                "\t\t\t\t — 数据来源 : 豆瓣电影\n\t\t\t\t（https://movie.douban.com/）");
        funcList.add("吐槽如幕布 : \n" +
                "\t\t\t\t分类 : B站各分区排行榜\n" +
                "\t\t\t\t — 数据来源 : BiliBili\n\t\t\t\t（http://api.bilibili.com）");
        funcList.add("游戏如人生 : \n" +
                "\t\t\t\t周榜 : SteamSpy统计的最近两周玩家数量最多的TOP100游戏\n" +
                "\t\t\t\t分类 : Steam各分类排行榜\n" +
                "\t\t\t\t — 数据来源 : SteamSpy\n\t\t\t\t（http://steamspy.com/api.php）");
        funcList.add("编程如逆旅 : \n" +
                "\t\t\t\t个人 : GitHub上Star最多的个人\n" +
                "\t\t\t\t组织 : GitHub上Star最多的组织\n" +
                "\t\t\t\t目 : GitHub上Star最多的项目\n" +
                "\t\t\t\t — 数据来源 : GithubRanking\n\t\t\t\t（https://github-ranking.com/）");
        problemList.add("应用内意见反馈通道尚未开启, 遇到Bug请发送邮件至huangfangzhi0@foxmail.com");
    }

    private View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == llNight){
                if (!isNight) {
                    editor.putBoolean(mParams.ISNIGHT, true);
                    editor.apply();
                    scNight.setChecked(true);
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    editor.putBoolean(mParams.ISNIGHT, false);
                    editor.apply();
                    scNight.setChecked(false);
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            } else if (v == llFunc){
                showListDialog(1, funcList);
            } else if (v == llProblem){
                showListDialog(2, problemList);
            } else if (v == llFeedback){

            } else if (v == llDownload){
                downloadDialog.show();
            } else if (v == llAboutUs){
                aboutDialog.show();
            } else if (v == btnLoginOut){
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent().setClass(mActivity, MainActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
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

    private void showAboutDialog(){
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

    private void showDownloadDialog(){
        View downloadDialogView = LayoutInflater.from(mActivity).inflate(R.layout.download_dialog, null);
        TextView tvAirFreeDownload = (TextView) downloadDialogView.findViewById(R.id.tv_airfree_download);
        tvAirFreeDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://o7gy5l0ax.bkt.clouddn.com/AirFree-Client.apk");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvAirFreeBlog = (TextView) downloadDialogView.findViewById(R.id.tv_airfree_blog);
        tvAirFreeBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.1anc3r.me/airfree-android-to-pc-remote/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvAirFreeGithub = (TextView) downloadDialogView.findViewById(R.id.tv_airfree_github);
        tvAirFreeGithub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/1anc3r/AirFree-Client");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvXuptDownload = (TextView) downloadDialogView.findViewById(R.id.tv_xupt_download);
        tvXuptDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://raw.githubusercontent.com/1anc3r/XUPT/master/screenshot/xupt.apk");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvXuptBlog = (TextView) downloadDialogView.findViewById(R.id.tv_xupt_blog);
        tvXuptBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.1anc3r.me/%E8%A5%BF%E9%82%AE%E8%AE%B0xupt/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvXuptGithub = (TextView) downloadDialogView.findViewById(R.id.tv_xupt_github);
        tvXuptGithub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/1anc3r/XUPT");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvSevenPoundsDownload = (TextView) downloadDialogView.findViewById(R.id.tv_sevenpounds_download);
        tvSevenPoundsDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://raw.githubusercontent.com/1anc3r/SevenPounds/master/screenshot/sevenpounds.apk");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvSevenPoundsBlog = (TextView) downloadDialogView.findViewById(R.id.tv_sevenpounds_blog);
        tvSevenPoundsBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.1anc3r.me/%E4%B8%83%E7%A3%85sevenpounds/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvSevenPoundsGithub = (TextView) downloadDialogView.findViewById(R.id.tv_sevenpounds_github);
        tvSevenPoundsGithub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/1anc3r/AirFree-Client");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(downloadDialogView);
        downloadDialog = builder.create();
    }
}
