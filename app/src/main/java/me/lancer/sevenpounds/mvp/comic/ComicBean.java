package me.lancer.sevenpounds.mvp.comic;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class ComicBean {

    private String title;
    private String category;
    private int type;
    private String cover;
    private String link;

    public ComicBean(){

    }

    public ComicBean(String title, String category, int type, String cover, String link){
        this.title = title;
        this.category = category;
        this.type = type;
        this.cover = cover;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
