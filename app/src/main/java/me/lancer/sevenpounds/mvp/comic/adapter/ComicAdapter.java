package me.lancer.sevenpounds.mvp.comic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.chapter.activity.ChapterActivity;
import me.lancer.sevenpounds.mvp.comic.ComicBean;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.util.LruImageCache;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;

    private List<ComicBean> list;
    private RequestQueue mQueue;
    private Context context;

    public ComicAdapter(Context context, List<ComicBean> list) {
        this.context = context;
        this.list = list;
        mQueue = ((mApp)((Activity)context).getApplication()).getRequestQueue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            ComicBean bean = list.get(position);
            if (getItemViewType(position) == TYPE_CONTENT) {
                viewHolder.tvTitle.setText(bean.getTitle());
                if (bean.getCategory().equals("")) {
                    viewHolder.tvCategory.setVisibility(View.GONE);
                } else {
                    viewHolder.tvCategory.setText(bean.getCategory());
                }
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivCover.setImageUrl(list.get(position).getCover(), loader);
                viewHolder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChapterActivity.startActivity((Activity) context, list.get(position).getLink(), list.get(position).getCover(), list.get(position).getTitle(), list.get(position).getCategory(), viewHolder.ivCover);
                    }
                });
            }else if (getItemViewType(position) == TYPE_TITLE) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                viewHolder.tvTitle.setText(bean.getTitle());
                viewHolder.tvCategory.setVisibility(View.GONE);
                viewHolder.ivCover.setVisibility(View.GONE);
                viewHolder.ivIconLeft.setVisibility(View.VISIBLE);
                viewHolder.ivIconRight.setVisibility(View.VISIBLE);
                if(!bean.getCover().equals("")){
                    LruImageCache cache = LruImageCache.instance();
                    ImageLoader loader = new ImageLoader(mQueue, cache);
                    viewHolder.ivIconLeft.setImageUrl(list.get(position).getCover(), loader);
                    viewHolder.ivIconRight.setImageUrl(list.get(position).getCover(), loader);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 0) {
            return TYPE_TITLE;
        } else if (list.get(position).getType() == 1) {
            return TYPE_CONTENT;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public LinearLayout container;
        public NetworkImageView ivCover, ivIconLeft, ivIconRight;
        public TextView tvTitle, tvCategory;

        public ViewHolder(View rootView) {
            super(rootView);
            cardView = (CardView) rootView.findViewById(R.id.cardView);
            container = (LinearLayout) rootView.findViewById(R.id.container);
            ivCover = (NetworkImageView) rootView.findViewById(R.id.ivCover);
            ivIconLeft = (NetworkImageView) rootView.findViewById(R.id.ivIconLeft);
            ivIconRight = (NetworkImageView) rootView.findViewById(R.id.ivIconRight);
            tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            tvCategory = (TextView) rootView.findViewById(R.id.tvCategory);
        }
    }
}
