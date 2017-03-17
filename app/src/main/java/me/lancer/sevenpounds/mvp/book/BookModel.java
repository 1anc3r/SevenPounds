package me.lancer.sevenpounds.mvp.book;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
        String content = contentGetterSetter.getContentFromHtml(reviewerUrl+pager);
        List<BookBean> list;
        if (!content.contains("失败")) {
            list = getReviewerFromContent(content);
            presenter.loadReviewerSuccess(list);
        } else {
            presenter.loadReviewerFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public void loadTopBook(int pager) {//图书top250
        String content = contentGetterSetter.getContentFromHtml(topBookUrl+pager);
        List<BookBean> list;
        if (!content.contains("失败")) {
            list = getTopBookFromContent(content);
            presenter.loadTopBookSuccess(list);
        } else {
            presenter.loadTopBookFailure(content);
            Log.e("loadTopBook", content);
        }
    }

    public List<BookBean> getReviewerFromContent(String content){
        List<BookBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("main review-item");
        for (int i = 0; i < elements.size(); i++) {
            BookBean mbItem = new BookBean();
            mbItem.setSubTitle(elements.get(i).getElementsByTag("img").attr("alt"));
            mbItem.setMainTitle(elements.get(i).getElementsByClass("title-link").text());
            mbItem.setAuthor(elements.get(i).getElementsByClass("author").text());
            mbItem.setStar(elements.get(i).getElementsByClass("main-title-hide").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            mbItem.setSubLink(elements.get(i).getElementsByTag("a").get(1).attr("href"));
            list.add(mbItem);
        }
        return list;
    }

    public List<BookBean> getTopBookFromContent(String content){
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
}
