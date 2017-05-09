package me.lancer.sevenpounds.mvp.repository;

/**
 * Created by HuangFangzhi on 2017/5/9.
 */

public class RepositoryBean {
    private String img;
    private String name;
    private String description;
    private String download;
    private String blog;

    public RepositoryBean(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
