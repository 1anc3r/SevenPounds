package me.lancer.sevenpounds.mvp.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class NewsModel {

    INewsPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String newsUrl = "http://news-at.zhihu.com/api/4/news/";
    String beforeUrl = "http://news-at.zhihu.com/api/4/news/before/";
    String latestUrl = "http://news-at.zhihu.com/api/4/news/latest";
    String themeUrl = "http://news-at.zhihu.com/api/4/theme/";

    public NewsModel(INewsPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadTopNews() {
        String content = contentGetterSetter.getContentFromHtml(latestUrl);
        List<NewsBean> list;
        if (!content.contains("获取失败!")) {
            list = getTopNewsFromContent(content);
            presenter.loadTopNewsSuccess(list);
        } else {
            presenter.loadTopNewsFailure(content);
            Log.e("loadTop", content);
        }
    }

    public void loadLatest() {
        String content = contentGetterSetter.getContentFromHtml(latestUrl);
        List<NewsBean> list;
        if (!content.contains("获取失败!")) {
            list = getLatestNewsFromContent(content);
            presenter.loadLatestSuccess(list);
        } else {
            presenter.loadLatestFailure(content);
            Log.e("loadLatest", content);
        }
    }

    public void loadBefore(String date) {
        String content = contentGetterSetter.getContentFromHtml(beforeUrl+date);
        List<NewsBean> list;
        if (!content.contains("获取失败!")) {
            list = getLatestNewsFromContent(content);
            presenter.loadBeforeSuccess(list);
        } else {
            presenter.loadBeforeFailure(content);
            Log.e("loadLatest", content);
        }
    }

    public void loadTheme(int type) {
        String content = contentGetterSetter.getContentFromHtml(themeUrl + type);
        List<NewsBean> list;
        if (!content.contains("获取失败!")) {
            list = getThemeNewsFromContent(content);
            presenter.loadThemeSuccess(list);
        } else {
            presenter.loadThemeFailure(content);
            Log.e("loadTheme", content);
        }
    }

    public void loadDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml(url);
        NewsBean bean;
        if (!content.contains("获取失败!")) {
            bean = getDetailFromContent(content);
            presenter.loadDetailSuccess(bean);
        } else {
            presenter.loadDetailFailure(content);
            Log.e("loadDetail", content);
        }
    }

    public List<NewsBean> getTopNewsFromContent(String content) {
        try {
            List<NewsBean> list = new ArrayList<>();
            JSONObject jbNews = new JSONObject(content);
            JSONArray jaNews = jbNews.getJSONArray("top_stories");
            for (int i = 0; i < jaNews.length(); i++) {
                NewsBean nbItem = new NewsBean();
                JSONObject jbItem = jaNews.getJSONObject(i);
                nbItem.setId(jbItem.getInt("id"));
                if (i == 0) {
                    nbItem.setType(-1);
                } else {
                    nbItem.setType(0);
                }
                nbItem.setTitle(jbItem.getString("title"));
                nbItem.setImg(jbItem.getString("image"));
                nbItem.setLink(newsUrl + nbItem.getId());
                list.add(nbItem);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NewsBean> getLatestNewsFromContent(String content) {
        try {
            List<NewsBean> list = new ArrayList<>();
            JSONObject jbNews = new JSONObject(content);
            JSONArray jaNews = jbNews.getJSONArray("stories");
            for (int i = 0; i < jaNews.length(); i++) {
                NewsBean nbItem = new NewsBean();
                JSONObject jbItem = jaNews.getJSONObject(i);
                nbItem.setId(jbItem.getInt("id"));
                if (i == 0) {
                    nbItem.setType(-1);
                } else {
                    nbItem.setType(0);
                }
                nbItem.setTitle(jbItem.getString("title"));
                JSONArray jaImg = jbItem.getJSONArray("images");
                nbItem.setImg(jaImg.get(0).toString());
                nbItem.setLink(newsUrl + nbItem.getId());
                list.add(nbItem);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NewsBean> getThemeNewsFromContent(String content) {
        try {
            List<NewsBean> list = new ArrayList<>();
            JSONObject jbNews = new JSONObject(content);
            JSONArray jaNews = jbNews.getJSONArray("stories");
            int flag = 0;
            for (int i = 0; i < jaNews.length(); i++) {
                NewsBean nbItem = new NewsBean();
                JSONObject jbItem = jaNews.getJSONObject(i);
                if (!jbItem.isNull("images")) {
                    nbItem.setId(jbItem.getInt("id"));
                    if (flag == 0) {
                        nbItem.setType(-1);
                        flag = 1;
                    } else {
                        nbItem.setType(0);
                    }
                    nbItem.setTitle(jbItem.getString("title"));
                    JSONArray jaImg = jbItem.getJSONArray("images");
                    nbItem.setImg(jaImg.get(0).toString());
                    nbItem.setLink(newsUrl + nbItem.getId());
                    list.add(nbItem);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewsBean getDetailFromContent(String content) {
        try {
            NewsBean bean = new NewsBean();
            JSONObject jbNews = new JSONObject(content);
            bean.setId(jbNews.getInt("id"));
            bean.setType(jbNews.getInt("type"));
            bean.setTitle(jbNews.getString("title"));
            bean.setContent(jbNews.getString("body").replace("\\n", ""));
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
