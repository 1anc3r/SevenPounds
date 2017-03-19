package me.lancer.sevenpounds.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import me.lancer.sevenpounds.util.LruImageCache;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<GameBean> list;
    private RequestQueue mQueue;

    public GameAdapter(Context context, List<GameBean> list) {
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.large_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (list.get(position) != null) {
            viewHolder.tvTitle.setText(list.get(position).getName());
            viewHolder.tvScore.setText("好评率:"+list.get(position).getScore()+"%");
            viewHolder.tvOwner.setText("持有量:"+list.get(position).getOwners());
            viewHolder.tvPrice.setText("价格:$"+Double.parseDouble(list.get(position).getPrice())/100);
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

        public LinearLayout llBottom;
        public NetworkImageView ivImg;
        public TextView tvTitle, tvScore, tvOwner, tvPrice;

        public ViewHolder(View rootView) {
            super(rootView);
            llBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
            llBottom.setVisibility(View.VISIBLE);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvTitle.setTextSize(20);
            tvScore = (TextView) rootView.findViewById(R.id.tv_score);
            tvOwner = (TextView) rootView.findViewById(R.id.tv_owners);
            tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
        }
    }
}
