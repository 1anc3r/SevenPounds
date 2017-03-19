package me.lancer.sevenpounds.mvp.code;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class CodeBean {

    private int type;
    private String rank;
    private String name;
    private String star;
    private String img;
    private String link;
    private List<CodeBean> repositories;

    public CodeBean() {
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
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

    public List<CodeBean> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<CodeBean> repositories) {
        this.repositories = repositories;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
