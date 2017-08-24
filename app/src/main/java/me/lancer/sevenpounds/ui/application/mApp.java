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
    private boolean isNight;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        boolean isNight = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
        if (isNight) {
            Colorful.defaults()
                    .primaryColor(Colorful.ThemeColor.DEEP_ORANGE)
                    .accentColor(Colorful.ThemeColor.DEEP_ORANGE)
                    .translucent(false)
                    .dark(isNight);
        } else {
            Colorful.defaults()
                    .primaryColor(Colorful.ThemeColor.GREEN)
                    .accentColor(Colorful.ThemeColor.GREEN)
                    .translucent(false)
                    .dark(isNight);
        }
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

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }
}
