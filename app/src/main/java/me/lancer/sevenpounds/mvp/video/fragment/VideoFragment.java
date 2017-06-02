package me.lancer.sevenpounds.mvp.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.ui.activity.AboutActivity;
import me.lancer.sevenpounds.ui.activity.MainActivity;
import me.lancer.sevenpounds.mvp.base.fragment.BaseFragment;

public class VideoFragment extends BaseFragment {

    private Toolbar toolbar;
    private int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.t_large);
        toolbar.setTitle("视频");
        ((MainActivity) getActivity()).initDrawer(toolbar);
        initTabLayout(view);
        inflateMenu();
        initSearchView();
        initView();
        initData();
    }

    private void initTabLayout(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_tab);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Fragment newfragment = new VideoThemeFragment();
        Bundle data = new Bundle();
        data.putInt("id", 0);
        data.putString("title", "动画");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "动画");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 1);
        data.putString("title", "番剧");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "番剧");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 2);
        data.putString("title", "音乐");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "音乐");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 3);
        data.putString("title", "舞蹈");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "舞蹈");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 4);
        data.putString("title", "游戏");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "游戏");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 5);
        data.putString("title", "科技");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "科技");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 6);
        data.putString("title", "鬼畜");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "鬼畜");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 7);
        data.putString("title", "娱乐");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "娱乐");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 8);
        data.putString("title", "电影");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "电影");

        newfragment = new VideoThemeFragment();
        data = new Bundle();
        data.putInt("id", 9);
        data.putString("title", "电视剧");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "电视剧");

        viewPager.setAdapter(adapter);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            index = bundle.getInt(getString(R.string.index));
        }
        viewPager.setCurrentItem(index, true);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initView() {
    }

    private void initData() {

    }

    private void inflateMenu() {
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_about:
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

//    private void inflateMenu() {
//        toolbar.inflateMenu(R.menu.menu_search);
//    }

    private void initSearchView() {
        final SearchView searchView = (SearchView) toolbar.getMenu()
                .findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("搜索...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showToast("...");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
