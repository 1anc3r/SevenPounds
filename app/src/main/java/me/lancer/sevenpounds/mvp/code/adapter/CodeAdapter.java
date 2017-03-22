package me.lancer.sevenpounds.mvp.code.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import me.lancer.sevenpounds.mvp.code.CodeBean;
import me.lancer.sevenpounds.mvp.code.activity.CodeDetailActivity;
import me.lancer.sevenpounds.util.LruImageCache;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.ViewHolder> {

    private static final int TYPE_ONE = 1;
    private static final int TYPE_ZERO = 0;

    private List<CodeBean> list;
    private RequestQueue mQueue;
    private Context context;

    public CodeAdapter(Context context, List<CodeBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            if (getItemViewType(position) == TYPE_ZERO) {
                viewHolder.tvRank.setText(list.get(position).getRank());
                viewHolder.tvName.setText(list.get(position).getName());
                viewHolder.tvStar.setText(list.get(position).getStar());
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
                viewHolder.cvSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CodeDetailActivity.startActivity((Activity) context, list.get(position).getName(), list.get(position).getStar(), list.get(position).getRank(), list.get(position).getImg(), list.get(position).getLink(), viewHolder.ivImg);
                    }
                });
            }else if (getItemViewType(position)==TYPE_ONE){
                viewHolder.ivImg.setVisibility(View.GONE);
                viewHolder.tvName.setText(list.get(position).getName());
                viewHolder.tvStar.setText(list.get(position).getStar());
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
            return TYPE_ZERO;
        } else if (list.get(position).getType() == 1) {
            return TYPE_ONE;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvSmall;
        public NetworkImageView ivImg;
        public TextView tvRank, tvName, tvStar;

        public ViewHolder(View rootView) {
            super(rootView);
            cvSmall = (CardView) rootView.findViewById(R.id.cv_small);
            tvRank = (TextView) rootView.findViewById(R.id.tv_rank);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvName = (TextView) rootView.findViewById(R.id.tv_title);
            tvStar = (TextView) rootView.findViewById(R.id.tv_star);
        }
    }
}
