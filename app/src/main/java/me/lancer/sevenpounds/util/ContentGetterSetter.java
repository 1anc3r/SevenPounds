package me.lancer.sevenpounds.util;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HuangFangzhi on 2016/12/14.
 */

public class ContentGetterSetter {

    public ContentGetterSetter() {
    }

    public String getContentFromHtml(String url) {
        StringBuilder content = new StringBuilder();
        OkHttpClient client = new OkHttpClient();
        client.setFollowRedirects(false);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                BufferedReader reader = new BufferedReader(response.body().charStream());
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                Log.e("getContentFromHtml", "获取成功!");
                return content.toString();
            } else {
                Log.e("getContentFromHtml", "获取失败!状态码:" + response.code());
                return "获取失败!状态码:" + response.code();
            }
        } catch (IOException e) {
            Log.e("getContentFromHtml", "获取失败!捕获异常:" + e.toString());
            return "获取失败!捕获异常:" + e.toString();
        }
    }
}
