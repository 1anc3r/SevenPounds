package me.lancer.sevenpounds.mvp.page;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/5/26.
 */

public class PageModel {

    IPagePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();

    public PageModel(IPagePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadList(String url) {
        String content = contentGetterSetter.getContentFromHtml("Page.loadList", url);
        List<PageBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("loadList", content);
        }
    }

    private List<PageBean> getListFromContent(String content) {
        try {
            List<PageBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.contains("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray imageList = returnData.getJSONArray("image_list");
                    for (int i = 0; i < imageList.length(); i++) {
                        JSONObject image = imageList.getJSONObject(i);
                        String title = image.getString("image_id");
                        String link = image.getString("location");
                        PageBean bean = new PageBean(title, link);
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