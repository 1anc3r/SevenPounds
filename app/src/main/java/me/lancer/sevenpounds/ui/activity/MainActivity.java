package me.lancer.sevenpounds.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.lancer.sevenpounds.R;
import me.lancer.sevenpounds.mvp.base.activity.BaseActivity;
import me.lancer.sevenpounds.mvp.photo.fragment.PhotoFragment;
import me.lancer.sevenpounds.mvp.video.activity.VideoPlayerActivity;
import me.lancer.sevenpounds.ui.application.mApp;
import me.lancer.sevenpounds.ui.application.mParams;
import me.lancer.sevenpounds.mvp.book.fragment.BookFragment;
import me.lancer.sevenpounds.mvp.code.fragment.CodeFragment;
import me.lancer.sevenpounds.mvp.game.fragment.GameFragment;
import me.lancer.sevenpounds.mvp.movie.fragment.MovieFragment;
import me.lancer.sevenpounds.mvp.music.fragment.MusicFragment;
import me.lancer.sevenpounds.mvp.news.fragment.NewsFragment;
import me.lancer.sevenpounds.mvp.video.fragment.VideoFragment;
import me.lancer.sevenpounds.ui.view.CircleImageView;

public class MainActivity extends BaseActivity {

    private mApp app = new mApp();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Fragment currentFragment;

    private int currentIndex;
    private long exitTime;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (mApp) this.getApplication();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initNavigationViewHeader();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        currentFragment = new NewsFragment();
        bundle.putInt(getString(R.string.index), 0);
        currentFragment.setArguments(bundle);
        switchContent(currentFragment);
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            actionBarDrawerToggle.syncState();
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }
    }

    private void initNavigationViewHeader() {
        navigationView = (NavigationView) findViewById(R.id.nv_main);
        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        CircleImageView civHead = (CircleImageView) view.findViewById(R.id.civ_head);
        TextView tvHead = (TextView) view.findViewById(R.id.tv_head);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }

    class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            Bundle bundle = new Bundle();
            drawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_1:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new NewsFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_2:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new PhotoFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_3:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new BookFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_4:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new MusicFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_5:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new MovieFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_6:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new VideoFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_7:
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new CodeFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
//                case R.id.navigation_item_6:
//                    currentIndex = 0;
//                    menuItem.setChecked(true);
//                    currentFragment = new GameFragment();
//                    bundle.putInt(getString(R.string.index), currentIndex);
//                    currentFragment.setArguments(bundle);
//                    switchContent(currentFragment);
//                    return true;
                case R.id.navigation_setting:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent().setClass(MainActivity.this, SettingActivity.class));
                            finish();
                        }
                    }, 180);
                    return true;
                default:
                    return true;
            }
        }
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
        invalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(mParams.CURRENT_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_bottomsheetdialog:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(this, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
