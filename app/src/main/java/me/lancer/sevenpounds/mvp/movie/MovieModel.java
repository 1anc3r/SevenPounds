package me.lancer.sevenpounds.mvp.movie;

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

public class MovieModel {

    IMoviePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String reviewerUrl = "https://movie.douban.com/review/best/?start=";
    String topMovieUrl = "https://movie.douban.com/top250?start=";

    public MovieModel(IMoviePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadReviewer(int pager) {//评论
        String content = contentGetterSetter.getContentFromHtml(reviewerUrl+pager);
        List<MovieBean> list;
        if (!content.contains("失败")) {
            list = getReviewerFromContent(content);
            presenter.loadReviewerSuccess(list);
        } else {
            presenter.loadReviewerFailure(content);
            Log.e("loadReviewer", content);
        }
    }

    public void loadTopMovie(int pager) {//电影top250
        String content = contentGetterSetter.getContentFromHtml(topMovieUrl+pager);
        List<MovieBean> list;
        if (!content.contains("失败")) {
            list = getTopMovieFromContent(content);
            presenter.loadTopMovieSuccess(list);
        } else {
            presenter.loadTopMovieFailure(content);
            Log.e("loadTopMovie", content);
        }
    }

    public List<MovieBean> getReviewerFromContent(String content){
        List<MovieBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("main review-item");
        for (int i = 0; i < elements.size(); i++) {
            MovieBean mbItem = new MovieBean();
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

    public List<MovieBean> getTopMovieFromContent(String content){
        List<MovieBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("item");
        for (int i = 0; i < elements.size(); i++) {
            MovieBean mbItem = new MovieBean();
            mbItem.setMainTitle(elements.get(i).getElementsByTag("img").attr("alt"));
            mbItem.setContent(elements.get(i).getElementsByClass("inq").text());
            mbItem.setStar(elements.get(i).getElementsByClass("rating_num").text());
            mbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            mbItem.setMainLink(elements.get(i).getElementsByTag("a").attr("href"));
            list.add(mbItem);
        }
        return list;
    }
}
