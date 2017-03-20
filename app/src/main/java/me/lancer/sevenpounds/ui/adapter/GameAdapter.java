package me.lancer.sevenpounds.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
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
import me.lancer.sevenpounds.mvp.game.GameBean;
import me.lancer.sevenpounds.ui.activity.GameDetailActivity;
import me.lancer.sevenpounds.util.LruImageCache;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_TITLE = 1;

    private List<GameBean> list;
    private RequestQueue mQueue;
    private Context context;
    private int type;

    public GameAdapter(Context context, int type, List<GameBean> list) {
        this.context = context;
        this.list = list;
        this.type = type;
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
            if (type == 0) {
                viewHolder.llBottom.setVisibility(View.GONE);
            } else if (type == 1) {
                viewHolder.llBottom.setVisibility(View.VISIBLE);
            }
            if (getItemViewType(position) == TYPE_TITLE) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                viewHolder.tvTitle.setText(list.get(position).getName());
                viewHolder.tvTitle.setTextSize(20);
                viewHolder.tvTitle.setGravity(Gravity.CENTER);
                viewHolder.ivImg.setVisibility(View.GONE);
            } else if (getItemViewType(position) == TYPE_CONTENT) {
                viewHolder.tvTitle.setText(list.get(position).getName());
                viewHolder.tvScore.setText("好评率 : " + list.get(position).getScore() + "%");
                viewHolder.tvOwner.setText("用户量 : " + list.get(position).getOwners());
                viewHolder.tvPrice.setText("价格 : $" + Double.parseDouble(list.get(position).getPrice()) / 100);
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
                viewHolder.cvLarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameDetailActivity.startActivity((Activity) context, list.get(position).getName(), list.get(position).getImg(), list.get(position).getLink(), viewHolder.ivImg);
                    }
                });
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
            return TYPE_CONTENT;
        } else if (list.get(position).getType() == 1) {
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvLarge;
        public LinearLayout llBottom;
        public NetworkImageView ivImg;
        public TextView tvTitle, tvScore, tvOwner, tvPrice;

        public ViewHolder(View rootView) {
            super(rootView);
            cvLarge = (CardView) rootView.findViewById(R.id.cv_large);
            llBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
            llBottom.setVisibility(View.VISIBLE);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvTitle.setTextSize(20);
            tvScore = (TextView) rootView.findViewById(R.id.tv_obj1);
            tvOwner = (TextView) rootView.findViewById(R.id.tv_obj2);
            tvPrice = (TextView) rootView.findViewById(R.id.tv_obj3);
        }
    }
}
