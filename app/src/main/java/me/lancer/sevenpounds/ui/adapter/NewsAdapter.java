package me.lancer.sevenpounds.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.news.NewsBean;
import me.lancer.sevenpounds.ui.activity.NewsDetailActivity;
import me.lancer.sevenpounds.util.LruImageCache;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final int TYPE_CONTENT_LARGE = -1;
    private static final int TYPE_CONTENT_SMALL = 0;
    private static final int TYPE_TITLE = 1;

    private List<NewsBean> list;
    private RequestQueue mQueue;
    private Context context;

    public NewsAdapter(Context context, List<NewsBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.large_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            if (getItemViewType(position) == TYPE_TITLE) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                viewHolder.tvTitle.setText(list.get(position).getTitle());
                viewHolder.tvTitle.setTextSize(20);
                viewHolder.tvTitle.setGravity(Gravity.CENTER);
                viewHolder.ivImg.setVisibility(View.GONE);
            } else if (getItemViewType(position) == TYPE_CONTENT_SMALL) {
                viewHolder.tvTitle.setText(list.get(position).getTitle());
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
            } else if (getItemViewType(position) == TYPE_CONTENT_LARGE) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                viewHolder.tvTitle.setText(list.get(position).getTitle());
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
            }
            viewHolder.cvLarge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsDetailActivity.startActivity((Activity) context, list.get(position).getTitle(), list.get(position).getImg(), list.get(position).getLink(), viewHolder.ivImg);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == -1) {
            return TYPE_CONTENT_LARGE;
        } else if (list.get(position).getType() == 0) {
            return TYPE_CONTENT_SMALL;
        } else if (list.get(position).getType() == 1) {
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvLarge;
        public NetworkImageView ivImg;
        public TextView tvTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            cvLarge = (CardView) rootView.findViewById(R.id.cv_large);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        }
    }
}
