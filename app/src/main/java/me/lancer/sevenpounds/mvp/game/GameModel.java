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
    String allGameUrl = "http://steamspy.com/api.php?request=top100owned";
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

    public void loadAllGame() {
        String content = contentGetterSetter.getContentFromHtml(allGameUrl);
        List<GameBean> list;
        if (!content.contains("失败")) {
            list = getGameFromContent(content);
            presenter.loadAllGameSuccess(list);
        } else {
            presenter.loadAllGameFailure(content);
            Log.e("loadAll", content);
        }
    }

    public List<GameBean> getGameFromContent(String content) {
        try {
            List<GameBean> list = new ArrayList<>();
            JSONObject jbGame = new JSONObject(content);
            String temp = content;
            String[] appids1 = temp.split("\"appid\":");
            String[] appids2 = new String[appids1.length];
            for (int i = 0; i < appids1.length - 1; i++) {
                appids2[i] = appids1[i + 1].split(",\"name\"")[0];
            }
            for (int i = 0; i < appids1.length - 1; i++) {
                JSONObject jbItem = jbGame.getJSONObject(appids2[i]);
                GameBean gbItem = new GameBean();
                gbItem.setAppid(jbItem.getString("appid"));
                gbItem.setName(jbItem.getString("name"));
                gbItem.setDeveloper(jbItem.getString("developer"));
                gbItem.setPublisher(jbItem.getString("publisher"));
                gbItem.setScore(jbItem.getString("score_rank"));
                gbItem.setOwners(jbItem.getString("owners"));
                gbItem.setPrice(jbItem.getString("price"));
                gbItem.setImg(imgStartUrl + gbItem.getAppid() + imgEndUrl);
                list.add(gbItem);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
