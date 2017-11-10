package vn.enclave.iramovies.ui.activities.base.Home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.activities.base.Home.adapters.PaperAdapter;
import vn.enclave.iramovies.ui.fragments.About.AboutFragment;
import vn.enclave.iramovies.ui.fragments.Favorite.FavoriteFragment;
import vn.enclave.iramovies.ui.fragments.Movie.MovieFragment;
import vn.enclave.iramovies.ui.fragments.Setting.SettingFragment;
import vn.enclave.iramovies.ui.views.ToolbarLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;


/**
 * @Run:
 */

public class HomeView extends BaseView {

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.profile)
    public LinearLayout mDrawerList;

    @BindView(R.id.main_content)
    public View mMainContent;

    @BindView(R.id.toolbar_layout)
    public ToolbarLayout mToolbar;

    @BindView(R.id.viewpaper_content)
    public ViewPager mViewPager;

    @BindView(R.id.vpMovies)
    public LinearLayout vpMovies;

    @BindView(R.id.vpFavorites)
    public LinearLayout vpFavorites;

    @BindView(R.id.vpSettings)
    public LinearLayout vpSettings;

    @BindView(R.id.vpAbout)
    public LinearLayout vpAbout;

    private float mLastTranslate = 0.0f;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void activityCreated() {
        setSupportActionBar(mToolbar.getToolbar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawerToggle();
        //noinspection deprecation
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // init Fragments in ViewPaper
        initialPages();
    }

    public void initialPages() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(MovieFragment.instantiate(mContext, MovieFragment.class.getName()));
        fragments.add(FavoriteFragment.instantiate(mContext, FavoriteFragment.class.getName()));
        fragments.add(Fragment.instantiate(mContext, SettingFragment.class.getName()));
        fragments.add(Fragment.instantiate(mContext, AboutFragment.class.getName()));

        mViewPager.setAdapter(new PaperAdapter(getSupportFragmentManager(), fragments));

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, mToolbar.getToolbar(), R.string.app_name, R.string.app_name);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, 0, 0) {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Detect if navigation drawer is opening
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = mDrawerList.getWidth() * slideOffset;

                if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
                    mMainContent.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(mLastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    mMainContent.startAnimation(anim);
                    mLastTranslate = moveFactor;
                }
            }
        };
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @OnClick({R.id.vpMovies, R.id.vpFavorites, R.id.vpSettings, R.id.vpAbout})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.vpMovies:
                mViewPager.setCurrentItem(Constants.MOVIES_INDEX, true);
                break;
            case R.id.vpFavorites:
                mViewPager.setCurrentItem(Constants.FAVORITES_INDEX, true);
                break;
            case R.id.vpSettings:
                mViewPager.setCurrentItem(Constants.SETTING_INDEX, true);
                break;
            case R.id.vpAbout:
                mViewPager.setCurrentItem(Constants.ABOUT_INDEX, true);
                break;
        }
    }
}
