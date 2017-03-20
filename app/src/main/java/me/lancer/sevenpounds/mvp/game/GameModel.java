package me.lancer.sevenpounds.mvp.game;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class GameModel {

    IGamePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String topGameUrl = "http://steamspy.com/api.php?request=top100in2weeks";
    String themeUrl = "http://steamspy.com/api.php?request=genre&genre=";
    String detailUrl = "http://steamspy.com/app/";
    String imgStartUrl = "http://cdn.akamai.steamstatic.com/steam/apps/";
    String imgEndUrl = "/capsule_184x69.jpg";

    public GameModel(IGamePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadTopGame() {
        String content = contentGetterSetter.getContentFromHtml(topGameUrl);
        List<GameBean> list;
        if (!content.contains("失败")) {
            list = getGameFromContent(content);
            presenter.loadTopGameSuccess(list);
        } else {
            presenter.loadTopGameFailure(content);
            Log.e("loadTop", content);
        }
    }

    public void loadTheme(String keyword) {
        String content = contentGetterSetter.getContentFromHtml(themeUrl+keyword);
        List<GameBean> list;
        if (!content.contains("失败")) {
            list = getThemeFromContent(content);
            presenter.loadThemeSuccess(list);
        } else {
            presenter.loadThemeFailure(content);
            Log.e("loadAll", content);
        }
    }

    public void loadDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml(url);
        GameBean bean;
        if (!content.contains("失败")) {
            bean = getDetailFromContent(content);
            presenter.loadDetailSuccess(bean);
        } else {
            presenter.loadDetailFailure(content);
            Log.e("loadDetail", content);
        }
    }

    public List<GameBean> getGameFromContent(String content) {
        try {
            List<GameBean> list = new ArrayList<>();
            JSONObject jbGame = new JSONObject(content);
            String temp = content;
            String[] appids1 = temp.split("\"appid\":");
            String[] appids2 = new String[appids1.length];
            int turn = appids1.length - 1;
            for (int i = 0; i < turn; i++) {
                appids2[i] = appids1[i + 1].split(",\"name\"")[0];
            }
            for (int i = 0; i < turn; i++) {
                JSONObject jbItem = jbGame.getJSONObject(appids2[i]);
                GameBean bean = new GameBean();
                bean.setType(0);
                bean.setAppid(jbItem.getString("appid"));
                bean.setName(jbItem.getString("name"));
                bean.setDeveloper(jbItem.getString("developer"));
                bean.setPublisher(jbItem.getString("publisher"));
                bean.setScore(jbItem.getString("score_rank"));
                bean.setOwners(jbItem.getString("owners"));
                bean.setPrice(jbItem.getString("price"));
                bean.setImg(imgStartUrl + bean.getAppid() + imgEndUrl);
                bean.setLink(detailUrl + bean.getAppid());
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<GameBean> getThemeFromContent(String content) {
        try {
            List<GameBean> list = new ArrayList<>();
            JSONObject jbGame = new JSONObject(content);
            String temp = content;
            String[] appids1 = temp.split("\"appid\":");
            String[] appids2 = new String[appids1.length];
            int turn = 10;
            for (int i = 0; i < turn; i++) {
                appids2[i] = appids1[i + 1].split(",\"name\"")[0];
            }
            for (int i = 0; i < turn; i++) {
                JSONObject jbItem = jbGame.getJSONObject(appids2[i]);
                GameBean bean = new GameBean();
                bean.setType(0);
                bean.setAppid(jbItem.getString("appid"));
                bean.setName(jbItem.getString("name"));
                bean.setDeveloper(jbItem.getString("developer"));
                bean.setPublisher(jbItem.getString("publisher"));
                bean.setScore(jbItem.getString("score_rank"));
                bean.setOwners(jbItem.getString("owners"));
                bean.setPrice(jbItem.getString("price"));
                bean.setImg(imgStartUrl + bean.getAppid() + imgEndUrl);
                bean.setLink(detailUrl + bean.getAppid());
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GameBean getDetailFromContent(String content) {
        GameBean bean = new GameBean();
        Document document = Jsoup.parse(content);
        String tag = document.getElementsByTag("p").get(1).html();
        bean.setTag(tag.substring(tag.indexOf("class=\"img-responsive\">")+23)
                .replace("Developer", "开发商").replace("Publisher", "<br>发行商")
                .replace("Genre", "风格").replace("Languages", "语言")
                .replace("Tags", "标签").replace("Category", "类型")
                .replace("Release date", "发行日期").replace("Price", "价格")
                .replace("Price", "价格").replace("Score rank", "评分等级")
                .replace("Userscore", "<br>用户评分").replace("Old userscore", "<br>老用户评分")
                .replace("Metascore", "<br>媒体评分").replace("Owners", "用户量")
                .replace("Players in the last 2 weeks", "最近两周在线人数")
                .replace("Players total", "总在线人数")
                .replace("Peak concurrent players yesterday", "昨日同时在线人数")
                .replace("YouTube stats", "YouTube统计")
                .replace("Playtime in the last 2 weeks", "最近两周游戏时间")
                .replace("Playtime total", "总游戏时间"));
        return bean;
    }
}
