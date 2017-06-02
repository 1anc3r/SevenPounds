package me.lancer.sevenpounds.ui.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;

import org.polaric.colorful.Colorful;

import java.io.File;
import java.lang.reflect.Field;

import me.lancer.sevenpounds.R;

/**
 * Created by HuangFangzhi on 2016/12/15.
 */

public class mApp extends Application {

    private RequestQueue mRequestQueue;

    public static Typeface TypeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        boolean night = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
        Log.e(getString(R.string.night), String.valueOf(night));
//        if (night) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.BLUE)
                .accentColor(Colorful.ThemeColor.BLUE)
                .translucent(false)
                .dark(night);
        Colorful.init(this);
        TypeFace = Typeface.createFromAsset(getAssets(), "fonts/MaterialIcons_Regular.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, TypeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        File cacheDir = new File(this.getCacheDir(), "volley");
        DiskBasedCache cache = new DiskBasedCache(cacheDir);
        mRequestQueue.start();
        mRequestQueue.add(new ClearCacheRequest(cache, null));
        return mRequestQueue;
    }
}
