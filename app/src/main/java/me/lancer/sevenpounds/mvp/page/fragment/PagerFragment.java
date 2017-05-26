package me.lancer.sevenpounds.mvp.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.fragment.BaseFragment;
import me.lancer.sevenpounds.util.LruImageCache;

public class PagerFragment extends BaseFragment {

    private String link;
    private NetworkImageView imageView;
    private RequestQueue mQueue;

    public static PagerFragment newInstance(String link) {
        Bundle args = new Bundle();
        args.putString("link", link);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(getActivity());
        link = getArguments().getString("link");
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (NetworkImageView) view.findViewById(R.id.imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        imageView.setImageUrl(link, loader);
    }
}

