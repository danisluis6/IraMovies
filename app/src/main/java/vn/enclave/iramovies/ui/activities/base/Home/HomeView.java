package vn.enclave.iramovies.ui.activities.base.Home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
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

    private static int mCurrentPage;
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
    @BindView(R.id.vpMoviesLine)
    public View vpMoviesLine;
    @BindView(R.id.vpFavoritesLine)
    public View vpFavoritesLine;
    @BindView(R.id.vpSettingsLine)
    public View vpSettingsLine;
    @BindView(R.id.vpAboutLine)
    public View vpAboutLine;
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
        resetLayoutView();
        vpMoviesLine.setVisibility(View.VISIBLE);
    }

    private void resetLayoutView() {
        vpAboutLine.setVisibility(View.GONE);
        vpMoviesLine.setVisibility(View.GONE);
        vpFavoritesLine.setVisibility(View.GONE);
        vpSettingsLine.setVisibility(View.GONE);
    }

    private float mStartPoint, mEndPoint;

    public void initialPages() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(MovieFragment.instantiate(mContext, MovieFragment.class.getName()));
        fragments.add(FavoriteFragment.instantiate(mContext, FavoriteFragment.class.getName()));
        fragments.add(Fragment.instantiate(mContext, SettingFragment.class.getName()));
        fragments.add(Fragment.instantiate(mContext, AboutFragment.class.getName()));

        mViewPager.setAdapter(new PaperAdapter(getSupportFragmentManager(), fragments));
        mCurrentPage = mViewPager.getCurrentItem();

        /*
         * @Run: https://stackoverflow.com/questions/8117523/how-do-you-get-the-current-page-number-of-a-viewpager-for-android
         * => Done
         *
         * @Run: https://stackoverflow.com/questions/15002339/android-viewpager-detect-page-scroll-state
         * => Done
         *
         * @Run: https://stackoverflow.com/questions/6645537/how-to-detect-the-swipe-left-or-right-in-android
         * => Done
         *
         * @Run: https://www.google.com/search?q=PagerTabStrip+example+android&client=ubuntu&hs=pk8&channel=fs&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjx2eCI17rXAhWGx7wKHXfOCB8Q_AUICigB&biw=1855&bih=983
         *
         */
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        mStartPoint = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        mEndPoint = motionEvent.getX();
                        float deltaX = mEndPoint - mStartPoint;
                        if (Math.abs(deltaX) > 150) {
                            // mViewPager.addOnPageChangeListener(new PageListener());
                            Utils.Toast.showToast(mContext, "Left to Right");
                        }
                        break;
                }
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
                resetLayoutView();
                vpMoviesLine.setVisibility(View.VISIBLE);
                break;
            case R.id.vpFavorites:
                mViewPager.setCurrentItem(Constants.FAVORITES_INDEX, true);
                resetLayoutView();
                vpFavoritesLine.setVisibility(View.VISIBLE);
                break;
            case R.id.vpSettings:
                mViewPager.setCurrentItem(Constants.SETTING_INDEX, true);
                resetLayoutView();
                vpSettingsLine.setVisibility(View.VISIBLE);
                break;
            case R.id.vpAbout:
                mViewPager.setCurrentItem(Constants.ABOUT_INDEX, true);
                resetLayoutView();
                vpAboutLine.setVisibility(View.VISIBLE);
                break;
        }
    }

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            mCurrentPage = position;
        }
    }
}
