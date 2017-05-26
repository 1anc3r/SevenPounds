package me.lancer.sevenpounds.mvp.page;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class PageBean {

    private String title;
    private String link;

    public PageBean(){

    }

    public PageBean(String title, String link){
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
