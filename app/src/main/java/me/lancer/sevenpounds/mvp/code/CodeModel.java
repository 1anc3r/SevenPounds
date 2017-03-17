package me.lancer.sevenpounds.mvp.code;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

    public CodeModel(ICodePresenter presenter) {
        this.presenter = presenter;
    }

    public void loadUsers(int pager) {//个人
        String content = contentGetterSetter.getContentFromHtml(usersUrl+pager);
        List<CodeBean> list;
        if (!content.contains("失败")) {
            list = getListFromContent(content);
            presenter.loadUsersSuccess(list);
        } else {
            presenter.loadUsersFailure(content);
            Log.e("loadUsers", content);
        }
    }

    public void loadOrganizations(int pager) {//组织
        String content = contentGetterSetter.getContentFromHtml(organizationsUrl+pager);
        List<CodeBean> list;
        if (!content.contains("失败")) {
            list = getListFromContent(content);
            presenter.loadOrganizationsSuccess(list);
        } else {
            presenter.loadOrganizationsFailure(content);
            Log.e("loadOrganizations", content);
        }
    }

    public void loadRepositories(int pager) {//项目
        String content = contentGetterSetter.getContentFromHtml(repositoriesUrl+pager);
        List<CodeBean> list;
        if (!content.contains("失败")) {
            list = getListFromContent(content);
            presenter.loadRepositoriesSuccess(list);
        } else {
            presenter.loadRepositoriesFailure(content);
            Log.e("loadRepositories", content);
        }
    }

    public List<CodeBean> getListFromContent(String content) {
        List<CodeBean> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByClass("list-group-item paginated_item");
        String temp1;
        String temp2;
        for (int i = 0; i < elements.size(); i++) {
            CodeBean cbItem = new CodeBean();
            temp1 = elements.get(i).getElementsByClass("hidden-xs hidden-sm").text();
            temp2 =elements.get(i).getElementsByClass("hidden-md hidden-lg").text();
            cbItem.setRank(elements.get(i).getElementsByClass("name").text().replace(temp1,"").replace(temp2,""));
            cbItem.setName(temp1);
            cbItem.setStar(elements.get(i).getElementsByClass("stargazers_count pull-right").text());
            cbItem.setImg(elements.get(i).getElementsByTag("img").attr("src"));
            cbItem.setLink(rankUrl + elements.get(i).getElementsByClass("list-group-item paginated_item").attr("href"));
            list.add(cbItem);
        }
        return list;
    }
}
