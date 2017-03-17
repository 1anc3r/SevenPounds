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
    String url = "http://news-at.zhihu.com/api/4/news/";
    String topNewsUrl = "http://news-at.zhihu.com/api/4/news/latest";
    String themeUrl = "http://news-at.zhihu.com/api/4/theme/";

    public NewsModel(INewsPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadTop() {
        String content = contentGetterSetter.getContentFromHtml(topNewsUrl);
        List<NewsBean> list;
        if (!content.contains("失败")) {
            list = getTopNewsFromContent(content);
            presenter.loadTopSuccess(list);
        } else {
            presenter.loadTopFailure(content);
            Log.e("loadTop", content);
        }
    }

    public void loadLatest() {
        String content = contentGetterSetter.getContentFromHtml(topNewsUrl);
        List<NewsBean> list;
        if (!content.contains("失败")) {
            list = getLatestNewsFromContent(content);
            presenter.loadLatestSuccess(list);
        } else {
            presenter.loadLatestFailure(content);
            Log.e("loadLatest", content);
        }
    }

    public void loadTheme(int type) {
        String content = contentGetterSetter.getContentFromHtml(themeUrl + type);
        List<NewsBean> list;
        if (!content.contains("失败")) {
            list = getThemeNewsFromContent(content);
            presenter.loadThemeSuccess(list);
        } else {
            presenter.loadThemeFailure(content);
            Log.e("loadTheme", content);
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
                nbItem.setType(0);
                nbItem.setTitle(jbItem.getString("title"));
                nbItem.setImg(jbItem.getString("image"));
                nbItem.setLink(url + nbItem.getId());
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
                nbItem.setType(0);
                nbItem.setTitle(jbItem.getString("title"));
                JSONArray jaImg = jbItem.getJSONArray("images");
                nbItem.setImg(jaImg.get(0).toString());
                nbItem.setLink(url + nbItem.getId());
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
            for (int i = 0; i < jaNews.length(); i++) {
                NewsBean nbItem = new NewsBean();
                JSONObject jbItem = jaNews.getJSONObject(i);
                if (!jbItem.isNull("images")) {
                    nbItem.setId(jbItem.getInt("id"));
                    nbItem.setType(0);
                    nbItem.setTitle(jbItem.getString("title"));
                    JSONArray jaImg = jbItem.getJSONArray("images");
                    nbItem.setImg(jaImg.get(0).toString());
                    nbItem.setLink(url + nbItem.getId());
                    list.add(nbItem);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
