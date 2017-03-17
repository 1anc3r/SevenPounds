package me.lancer.sevenpounds.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.code.CodeBean;
import me.lancer.sevenpounds.util.LruImageCache;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.ViewHolder> {

    private List<CodeBean> list;
    private RequestQueue mQueue;

    public CodeAdapter(Context context, List<CodeBean> list) {
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.code_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (list.get(position) != null) {
            viewHolder.tvRank.setText(list.get(position).getRank());
            viewHolder.tvName.setText(list.get(position).getName());
            viewHolder.tvStar.setText(list.get(position).getStar());
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue,cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView ivImg;
        public TextView tvRank, tvName, tvStar;

        public ViewHolder(View rootView) {
            super(rootView);
            tvRank = (TextView) rootView.findViewById(R.id.tv_rank);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
            tvStar = (TextView) rootView.findViewById(R.id.tv_star);
        }
    }
}
