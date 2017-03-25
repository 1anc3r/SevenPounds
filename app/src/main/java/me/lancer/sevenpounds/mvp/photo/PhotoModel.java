package me.lancer.sevenpounds.mvp.photo;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class PhotoModel {

    IPhotoPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String imgListUrl = "https://www.pexels.com/?page=";
    String imgThemeUrl = "https://www.pexels.com/search/";
    String imgDetailUrl = "?w=940&h=650&auto=compress&cs=tinysrgb";

    public PhotoModel(IPhotoPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadLatest(int pager) {
        String content = contentGetterSetter.getContentFromHtml(imgListUrl + pager);
        List<PhotoBean> list;
        if (!content.contains("获取失败!")) {
            list = getPhotosFromContent(content);
            presenter.loadLatestSuccess(list);
        } else {
            presenter.loadLatestFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public void loadTheme(String type) {
        String content = contentGetterSetter.getContentFromHtml(imgThemeUrl + type);
        List<PhotoBean> list;
        if (!content.contains("获取失败!")) {
            list = getPhotosFromContent(content);
            presenter.loadThemeSuccess(list);
        } else {
            presenter.loadThemeFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public List<PhotoBean> getPhotosFromContent(String content) {
        List<PhotoBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByTag("img");
        for (Element element : elements) {
            PhotoBean mbItem = new PhotoBean();
            String imgSmall = element.getElementsByTag("img").attr("src");
            if (imgSmall.contains("auto=compress&cs=tinysrgb")) {
                mbItem.setType(0);
                mbItem.setImgSmall(imgSmall);
                mbItem.setImgLarge(imgSmall.split("/?")[0]+imgDetailUrl);
                list.add(mbItem);
            }
        }
        return list;
    }
}
