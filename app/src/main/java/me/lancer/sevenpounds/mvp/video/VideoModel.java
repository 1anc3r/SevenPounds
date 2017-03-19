package me.lancer.sevenpounds.mvp.video;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class VideoModel {

    IVideoPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String videoUrl = "http://www.bilibili.com/video/av";
    String topVideoUrl = "http://api.bilibili.cn/index";
    String searchUrl = "http://api.bilibili.cn/suggest?term=";

    public VideoModel(IVideoPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadTopVideo() {
        String content = contentGetterSetter.getContentFromHtml(topVideoUrl);
        List<VideoBean> list;
        if (!content.contains("失败")) {
            list = getTopVideoFromContent(content);
            presenter.loadTopVideoSuccess(list);
        } else {
            presenter.loadTopVideoFailure(content);
            Log.e("loadTopVideo", content);
        }
    }

    public void loadResult() {
        String content = contentGetterSetter.getContentFromHtml(searchUrl);
        List<VideoBean> list;
        if (!content.contains("失败")) {
            list = getTopVideoFromContent(content);
            presenter.loadTopVideoSuccess(list);
        } else {
            presenter.loadTopVideoFailure(content);
            Log.e("loadResult", content);
        }
    }

    public void loadDetail() {
        String content = contentGetterSetter.getContentFromHtml(searchUrl);
        List<VideoBean> list;
        if (!content.contains("失败")) {
            list = getTopVideoFromContent(content);
            presenter.loadTopVideoSuccess(list);
        } else {
            presenter.loadTopVideoFailure(content);
            Log.e("loadResult", content);
        }
    }

    public List<VideoBean> getTopVideoFromContent(String content) {
        try {
            List<VideoBean> list = new ArrayList<>();
            JSONObject jbVideo = new JSONObject(content);
            JSONObject jbType = jbVideo.getJSONObject("type1");
            for (int i = 0; i < 9; i++) {
                VideoBean nbItem = new VideoBean();
                JSONObject jbItem = jbType.getJSONObject("" + i);
                int id = jbItem.getInt("aid");
                nbItem.setId(id);
                nbItem.setTitle(decodeUnicode(jbItem.getString("title")));
                nbItem.setPlay(jbItem.getString("play"));
                nbItem.setReview(jbItem.getString("review"));
                nbItem.setComment(jbItem.getString("video_review"));
                nbItem.setFavorites(jbItem.getString("favorites"));
                nbItem.setAuthor(decodeUnicode(jbItem.getString("author")));
                nbItem.setImg(jbItem.getString("pic"));
                nbItem.setLink(videoUrl + id);
                list.add(nbItem);
            }
            jbType = jbVideo.getJSONObject("type3");
            for (int i = 0; i < 9; i++) {
                VideoBean nbItem = new VideoBean();
                JSONObject jbItem = jbType.getJSONObject("" + i);
                int id = jbItem.getInt("aid");
                nbItem.setId(id);
                nbItem.setTitle(decodeUnicode(jbItem.getString("title")));
                nbItem.setPlay(jbItem.getString("play"));
                nbItem.setReview(jbItem.getString("review"));
                nbItem.setComment(jbItem.getString("video_review"));
                nbItem.setFavorites(jbItem.getString("favorites"));
                nbItem.setAuthor(decodeUnicode(jbItem.getString("author")));
                nbItem.setImg(jbItem.getString("pic"));
                nbItem.setLink(videoUrl + id);
                list.add(nbItem);
            }
            jbType = jbVideo.getJSONObject("type4");
            for (int i = 0; i < 9; i++) {
                VideoBean nbItem = new VideoBean();
                JSONObject jbItem = jbType.getJSONObject("" + i);
                int id = jbItem.getInt("aid");
                nbItem.setId(id);
                nbItem.setTitle(decodeUnicode(jbItem.getString("title")));
                nbItem.setPlay(jbItem.getString("play"));
                nbItem.setReview(jbItem.getString("review"));
                nbItem.setComment(jbItem.getString("video_review"));
                nbItem.setFavorites(jbItem.getString("favorites"));
                nbItem.setAuthor(decodeUnicode(jbItem.getString("author")));
                nbItem.setImg(jbItem.getString("pic"));
                nbItem.setLink(videoUrl + id);
                list.add(nbItem);
            }
            jbType = jbVideo.getJSONObject("type5");
            for (int i = 0; i < 9; i++) {
                VideoBean nbItem = new VideoBean();
                JSONObject jbItem = jbType.getJSONObject("" + i);
                int id = jbItem.getInt("aid");
                Log.e("get", id+"");
                nbItem.setId(id);
                nbItem.setTitle(decodeUnicode(jbItem.getString("title")));
                nbItem.setPlay(jbItem.getString("play"));
                nbItem.setReview(jbItem.getString("review"));
                nbItem.setComment(jbItem.getString("video_review"));
                nbItem.setFavorites(jbItem.getString("favorites"));
                nbItem.setAuthor(decodeUnicode(jbItem.getString("author")));
                nbItem.setImg(jbItem.getString("pic"));
                nbItem.setLink(videoUrl + id);
                list.add(nbItem);
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
}
