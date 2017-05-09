package me.lancer.sevenpounds.mvp.repository.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.repository.RepositoryBean;
import me.lancer.sevenpounds.util.LruImageCache;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<RepositoryBean> list;
    private RequestQueue mQueue;
    private Context context;

    public RepositoryAdapter(Context context, List<RepositoryBean> list) {
        this.list = list;
        this.context = context;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repository_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvTitle.setText(list.get(position).getName());
        viewHolder.tvContent.setText(list.get(position).getDescription());
        viewHolder.tvDownload.setText("下载地址");
        viewHolder.tvDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(list.get(position).getDownload());
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });
        viewHolder.tvBlog.setText("博客链接");
        viewHolder.tvBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(list.get(position).getBlog());
                intent.setData(content_url);
                context.startActivity(intent);
            }
        });
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
        viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        viewHolder.ivImg.setImageUrl(list.get(position).getImg(), loader);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView ivImg;
        public TextView tvTitle, tvContent, tvDownload, tvBlog;

        public ViewHolder(View rootView) {
            super(rootView);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvContent = (TextView) rootView.findViewById(R.id.tv_content);
            tvDownload = (TextView) rootView.findViewById(R.id.tv_arg1);
            tvBlog = (TextView) rootView.findViewById(R.id.tv_arg2);
        }
    }
}
