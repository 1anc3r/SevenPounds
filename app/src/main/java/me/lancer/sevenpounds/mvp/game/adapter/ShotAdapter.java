package me.lancer.sevenpounds.mvp.game.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.game.GameBean;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.util.LruImageCache;

public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {

    private List<String> list;
    private RequestQueue mQueue;
    private Context context;

    public ShotAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        mQueue = ((mApp) ((Activity) context).getApplication()).getRequestQueue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shot_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue, cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position), loader);
            viewHolder.cvShot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvShot;
        public NetworkImageView ivImg;

        public ViewHolder(View rootView) {
            super(rootView);
            cvShot = (CardView) rootView.findViewById(R.id.cv_shot);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
        }
    }
}
