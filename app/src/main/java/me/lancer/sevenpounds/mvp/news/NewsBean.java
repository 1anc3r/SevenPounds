package me.lancer.sevenpounds.mvp.news;

import me.lancer.sevenpounds.ui.adapter.StringAdapter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class NewsBean {

    private int id;
    private int type;
    private String title;
    private String img;
    private String link;
    private String content;

    public NewsBean() {
    }

    public NewsBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
