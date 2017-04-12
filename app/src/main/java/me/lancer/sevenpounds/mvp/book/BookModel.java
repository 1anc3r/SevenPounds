package me.lancer.sevenpounds.mvp.book;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
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

public class BookModel {

    IBookPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String reviewerUrl = "https://book.douban.com/review/best/?start=";
    String topBookUrl = "https://book.douban.com/top250?start=";

    public BookModel(IBookPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadReviewer(int pager) {//评论
        String content = contentGetterSetter.getContentFromHtml("Book.loadReviewer", reviewerUrl + pager);
        List<BookBean> list;
        if (!content.contains("获取失败!")) {
            list = getReviewerFromContent(content);
            presenter.loadReviewerSuccess(list);
        } else {
            presenter.loadReviewerFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public void loadTopBook(int pager) {//图书top250
        String content = contentGetterSetter.getContentFromHtml("Book.loadTopBook", topBookUrl + pager);
        List<BookBean> list;
        if (!content.contains("获取失败!")) {
            list = getTopBookFromContent(content);
            presenter.loadTopBookSuccess(list);
        } else {
            presenter.loadTopBookFailure(content);
            Log.e("loadTopBook", content);
        }
    }

    public void loadReviewerDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Book.loadReviewerDetail", url);
        BookBean bean;
        if (!content.contains("获取失败!")) {
            bean = getReviewerDetailFromContent(content);
            presenter.loadReviewerDetailSuccess(bean);
        } else {
            presenter.loadReviewerDetailFailure(content);
            Log.e("loadReviewerDetail", content);
        }
    }

    public void loadTopDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Book.loadTopDetail", url);
        BookBean bean;
        if (!content.contains("获取失败!")) {
            bean = getTopDetailFromContent(content);
            presenter.loadTopDetailSuccess(bean);
        } else {
            presenter.loadTopDetailFailure(content);
            Log.e("loadTopDetail", content);
        }
    }

    public List<BookBean> getReviewerFromContent(String content) {
        List<BookBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("main review-item");
        for (int i = 0; i < elements.size(); i++) {
            BookBean mbItem = new BookBean();
            mbItem.setMainTitle(elements.get(i).getElementsByClass("title-link").text());
            mbItem.setSubTitle(elements.get(i).getElementsByTag("img").attr("alt"));
            mbItem.setAuthor(elements.get(i).getElementsByClass("author").text());
            mbItem.setStar(elements.get(i).getElementsByClass("main-title-hide").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            mbItem.setSubLink(elements.get(i).getElementsByTag("a").get(1).attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public List<BookBean> getTopBookFromContent(String content) {
        List<BookBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("item");
        for (int i = 0; i < elements.size(); i++) {
            BookBean mbItem = new BookBean();
            mbItem.setMainTitle(elements.get(i).getElementsByTag("a").get(1).attr("title"));
            mbItem.setAuthor(elements.get(i).getElementsByClass("pl").text());
            mbItem.setContent(elements.get(i).getElementsByClass("inq").text());
            mbItem.setStar(elements.get(i).getElementsByClass("rating_nums").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public BookBean getReviewerDetailFromContent(String content) {
        BookBean bean = new BookBean();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        bean.setSubTitle(element.getElementsByClass("info-list").get(0).html());
        bean.setContent("— 书评 —<br>" + element.getElementsByClass("review-content clearfix").get(0).html());
        return bean;
    }

    public BookBean getTopDetailFromContent(String content) {
        BookBean bean = new BookBean();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        bean.setSubTitle(element.getElementById("info").html());
        if (element.getElementsByClass("all hidden").size() > 0) {
            bean.setContent("— 作者简介 —<br>" + element.getElementsByClass("intro").get(1).html()
                    + "<br>— 内容简介 —<br>" + element.getElementsByClass("all hidden").get(0).html());
        } else {
            bean.setContent("— 作者简介 —<br>" + element.getElementsByClass("intro").get(1).html()
                    + "<br>— 内容简介 —<br>" + element.getElementById("link-report").html());
        }
        return bean;
    }
}
