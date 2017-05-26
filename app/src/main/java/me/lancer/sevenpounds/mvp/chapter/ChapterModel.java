package me.lancer.sevenpounds.mvp.chapter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;
import me.lancer.sevenpounds.util.URL;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class ChapterModel {

    IChapterPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();

    public ChapterModel(IChapterPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadList(String url) {
        String content = contentGetterSetter.getContentFromHtml("Chapter.loadList", url);
        List<ChapterBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("loadList", content);
        }
    }

    private List<ChapterBean> getListFromContent(String content) {
        try {
            List<ChapterBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.equals("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray chapterList = returnData.getJSONArray("chapter_list");
                    for (int i = 0; i < chapterList.length(); i++) {
                        JSONObject chapter = chapterList.getJSONObject(i);
                        String title = chapter.getString("name");
                        String link = URL.CHAPTER_URL + chapter.getInt("chapter_id");
                        ChapterBean bean = new ChapterBean(title, link);
                        list.add(bean);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
