package me.lancer.sevenpounds.mvp.photo;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class PhotoBean {

    private int type;
    private String title;
    private String imgSmall;
    private String imgLarge;

    public PhotoBean() {
    }

    public PhotoBean(int type, String title) {
        this.type = type;
        this.title = title;
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

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getImgLarge() {
        return imgLarge;
    }

    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }
}
