package me.lancer.sevenpounds.mvp.video;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class VideoModel {

    IVideoPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String cidUrl = "http://api.bilibili.com/view?type=json&appkey=12737ff7776f1ade&access_key=816d1fda0c1fdbb6a44b3dc0de8b579b&page=1&id=";
    String ThemeUrl = "http://api.bilibili.cn/index";

    public VideoModel(IVideoPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadTheme(int pager) {
        String content = contentGetterSetter.getContentFromHtml("Video.loadTheme", ThemeUrl);
        List<VideoBean> list;
        if (!content.contains("失败")) {
            list = getThemeFromContent(content, pager);
            presenter.loadThemeSuccess(list);
        } else {
            presenter.loadThemeFailure(content);
            Log.e("loadTheme", content);
        }
    }

    public void loadDetail(String url) {
        String content = contentGetterSetter.getContentFromHtml("Video.loadDetail", url);
        VideoBean bean;
        if (!content.contains("失败")) {
            bean = getDetailFromContent(content);
            presenter.loadDetailSuccess(bean);
        } else {
            presenter.loadDetailFailure(content);
            Log.e("loadDetail", content);
        }
    }

    public List<VideoBean> getThemeFromContent(String content, int pager) {
        try {
            List<VideoBean> list = new ArrayList<>();
            JSONObject jbVideo = new JSONObject(content);
            JSONObject jbType;
            if (pager == 0) {
                jbType = jbVideo.getJSONObject("type1");//动画
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 1) {
                jbType = jbVideo.getJSONObject("type13");//番剧
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 2) {
                jbType = jbVideo.getJSONObject("type3");//音乐
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 3) {
                jbType = jbVideo.getJSONObject("type129");//舞蹈
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 4) {
                jbType = jbVideo.getJSONObject("type4");//游戏
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 5) {
                jbType = jbVideo.getJSONObject("type36");//科技
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 6) {
                jbType = jbVideo.getJSONObject("type119");//鬼畜
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 7) {
                jbType = jbVideo.getJSONObject("type5");//娱乐
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 8) {
                jbType = jbVideo.getJSONObject("type11");//电影
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            if (pager == 9) {
                jbType = jbVideo.getJSONObject("type23");//电视剧
                for (int i = 0; i < 9; i++) {
                    VideoBean bean = new VideoBean();
                    JSONObject jbItem = jbType.getJSONObject("" + i);
                    int aid = jbItem.getInt("aid");
                    bean.setAid(aid);
                    if (i == 0) {
                        bean.setType(-1);
                    } else {
                        bean.setType(0);
                    }
                    bean.setTitle(decodeUnicode(jbItem.getString("title")));
                    bean.setPlay(jbItem.getString("play"));
                    bean.setReview(jbItem.getString("review"));
                    bean.setComment(jbItem.getString("video_review"));
                    bean.setFavorites(jbItem.getString("favorites"));
                    bean.setAuthor(decodeUnicode(jbItem.getString("author")));
                    bean.setImg(jbItem.getString("pic"));
                    bean.setLink(cidUrl + aid);
                    list.add(bean);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodeUnicode(String str) {
        char ch;
        int len = str.length();
        StringBuffer buffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            ch = str.charAt(x++);
            if (ch == '\\') {
                ch = str.charAt(x++);
                if (ch == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        ch = str.charAt(x++);
                        switch (ch) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + ch - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + ch - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + ch - 'A';
                                break;
                            default:
                                break;
                        }
                    }
                    buffer.append((char) value);
                } else {
                    if (ch == 't')
                        ch = '\t';
                    else if (ch == 'r')
                        ch = '\r';
                    else if (ch == 'n')
                        ch = '\n';
                    else if (ch == 'f')
                        ch = '\f';
                    buffer.append(ch);
                }
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public VideoBean getDetailFromContent(String content) {
        return null;
    }
}
