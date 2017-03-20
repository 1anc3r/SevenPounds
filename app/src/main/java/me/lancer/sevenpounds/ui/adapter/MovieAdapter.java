package me.lancer.sevenpounds.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.movie.MovieBean;
import me.lancer.sevenpounds.ui.activity.MovieDetailActivity;
import me.lancer.sevenpounds.util.LruImageCache;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieBean> list;
    private RequestQueue mQueue;
    private Context context;

    public MovieAdapter(Context context, List<MovieBean> list) {
        this.context = context;
        this.list = list;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medimu_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            viewHolder.tvTitle.setText(list.get(position).getMainTitle());
            String temp = "";
            float star = 0;
            if (list.get(position).getSubTitle() != null && !list.get(position).getSubTitle().equals("")) {
                temp = " 评论 " + list.get(position).getSubTitle();
                star = Float.parseFloat(list.get(position).getStar());
                viewHolder.tvContent.setText(list.get(position).getAuthor() + temp);
            }else{
                star = Float.parseFloat(list.get(position).getStar())/2;
                viewHolder.tvContent.setText(list.get(position).getContent());
            }
            viewHolder.rbRating.setRating(star);
            LruImageCache cache = LruImageCache.instance();
            ImageLoader loader = new ImageLoader(mQueue,cache);
            viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
            viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);
            viewHolder.cvMedimu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getSubTitle() == null || list.get(position).getSubTitle().equals("")) {
                        Intent intent = new Intent();
                        intent.putExtra("title", list.get(position).getMainTitle());
                        intent.putExtra("type", 0);
                        intent.putExtra("link", list.get(position).getMainLink());
                        intent.putExtra("img", list.get(position).getImg());
                        intent.setClass(context, MovieDetailActivity.class);
                        context.startActivity(intent);
//                        MovieDetailActivity.startActivity((Activity) context, 0, list.get(position).getMainTitle(), list.get(position).getImg(), list.get(position).getMainLink(), viewHolder.ivImg);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("title", list.get(position).getMainTitle());
                        intent.putExtra("type", 1);
                        intent.putExtra("link", list.get(position).getSubLink());
                        intent.putExtra("img", list.get(position).getImg());
                        intent.setClass(context, MovieDetailActivity.class);
                        context.startActivity(intent);
//                        MovieDetailActivity.startActivity((Activity) context, 1, list.get(position).getMainTitle(), list.get(position).getImg(), list.get(position).getSubLink(), viewHolder.ivImg);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvMedimu;
        public NetworkImageView ivImg;
        public TextView tvTitle, tvContent;
        public RatingBar rbRating;

        public ViewHolder(View rootView) {
            super(rootView);
            cvMedimu = (CardView) rootView.findViewById(R.id.cv_medimu);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvContent = (TextView) rootView.findViewById(R.id.htv_content);
            rbRating = (RatingBar) rootView.findViewById(R.id.rb_medimu);
        }
    }
}
