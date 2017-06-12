package me.lancer.sevenpounds.mvp.code;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
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

public class CodeModel {

    ICodePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String rankUrl = "https://github-ranking.com/";
    String usersUrl = "https://github-ranking.com/users?page=";
    String organizationsUrl = "https://github-ranking.com/organizations?page=";
    String repositoriesUrl = "https://github-ranking.com/repositories?page=";
    String trendingUrl = "https://github.com/trending?since=";
//    String trendingUrl = "https://trendings.herokuapp.com/api/repo/language/?since=";

    public CodeModel(ICodePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadUsers(int pager) {//个人
        String content = contentGetterSetter.getContentFromHtml("Code.loadUsers", usersUrl + pager);
        List<CodeBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadUsersSuccess(list);
        } else {
            presenter.loadUsersFailure(content);
            Log.e("loadUsers", content);
        }
    }

    public void loadOrganizations(int pager) {//组织
        String content = contentGetterSetter.getContentFromHtml("Code.loadOrganizations", organizationsUrl + pager);
        List<CodeBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadOrganizationsSuccess(list);
        } else {
            presenter.loadOrganizationsFailure(content);
            Log.e("loadOrganizations", content);
        }
    }

    public void loadRepositories(int pager) {//项目
        String content = contentGetterSetter.getContentFromHtml("Code.loadRepositories", repositoriesUrl + pager);
        List<CodeBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadRepositoriesSuccess(list);
        } else {
            presenter.loadRepositoriesFailure(content);
            Log.e("loadRepositories", content);
        }
    }

    public void loadTrending(String since) {
        String content = contentGetterSetter.getContentFromHtml("Code.loadTrending", trendingUrl+since);
        List<CodeBean> list;
        if (!content.contains("获取失败!")) {
            list = getTrendingFromContent(content);
            presenter.loadTrendingSuccess(list);
        } else {
            presenter.loadTrendingFailure(content);
            Log.e("loadRepositories", content);
        }
    }

    public void loadDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Code.loadDetail", url);
        CodeBean bean;
        if (!content.contains("获取失败!")) {
            bean = getDetailFromContent(content);
            presenter.loadDetailSuccess(bean);
        } else {
            presenter.loadDetailFailure(content);
            Log.e("loadDetail", content);
        }
    }

    public List<CodeBean> getListFromContent(String content) {
        List<CodeBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByClass("list-group-item paginated_item");
        String temp1;
        String temp2;
        for (int i = 0; i < elements.size(); i++) {
            CodeBean bean = new CodeBean();
            temp1 = elements.get(i).getElementsByClass("hidden-xs hidden-sm").text();
            temp2 = elements.get(i).getElementsByClass("hidden-md hidden-lg").text();
            bean.setType(0);
            bean.setRank(elements.get(i).getElementsByClass("name").text().replace(temp1, "").replace(temp2, ""));
            bean.setName(temp1);
            bean.setStar(elements.get(i).getElementsByClass("stargazers_count pull-right").text());
            bean.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            bean.setLink("https://github.com/" + elements.get(i).getElementsByClass("list-group-item paginated_item").attr("href"));
            list.add(bean);
        }
        return list;
    }

    public List<CodeBean> getTrendingFromContent(String content) {
        List<CodeBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByClass("col-12 d-block width-full py-4 border-bottom");
        for (int i = 0; i < elements.size(); i++) {
            CodeBean bean = new CodeBean();
            bean.setType(0);
            bean.setRank((i+1)+".");
            bean.setName(elements.get(i).getElementsByTag("a").attr("href"));
            bean.setStar(elements.get(i).getElementsByClass("muted-link mr-3").text().split(" ")[0]);
            bean.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            bean.setLink("https://github.com/"+bean.getName());
            list.add(bean);
        }
        return list;
    }

    public CodeBean getDetailFromContent(String content) {
        CodeBean bean = new CodeBean();
        List<CodeBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        if (document.getElementsByClass("user_value col-xs-9").size() > 0)
            bean.setStar(document.getElementsByClass("user_value col-xs-9").get(0).text());
        if (document.getElementsByClass("user_value col-xs-7").size() > 0)
            bean.setRank(document.getElementsByClass("user_value col-xs-7").get(0).text());
        if (document.getElementsByTag("link").size() > 0)
            bean.setLink(document.getElementsByTag("link").get(1).text());
        Elements elements = document.getElementsByClass("list-group-item paginated_full_item");
        for (int i = 0; i < elements.size(); i++) {
            CodeBean item = new CodeBean();
            item.setType(1);
            item.setName(elements.get(i).getElementsByClass("login").get(0).text());
            item.setStar(elements.get(i).getElementsByClass("stargazers_count pull-right").get(0).text());
            item.setLink(bean.getLink() + "/" + item.getName());
            list.add(item);
        }
        bean.setRepositories(list);
        return bean;
    }
}
