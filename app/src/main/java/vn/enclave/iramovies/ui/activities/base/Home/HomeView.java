package vn.enclave.iramovies.ui.activities.base.Home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.activities.base.Home.adapters.SectionPaperAdapter;
import vn.enclave.iramovies.ui.fragments.About.AboutFragment;
import vn.enclave.iramovies.ui.fragments.Favorite.FavoriteView;
import vn.enclave.iramovies.ui.fragments.Movie.MovieView;
import vn.enclave.iramovies.ui.fragments.Setting.SettingFragment;
import vn.enclave.iramovies.ui.views.TabItem;
import vn.enclave.iramovies.ui.views.ToolbarLayout;


/**
 * @Run: https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * => Done
 *
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
    @BindView(R.id.tbBottomBar)
    public TabLayout tabNavigationBottomMenu;
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
        // noinspection deprecation
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // init Fragments in ViewPaper
        initialPages();
        addViews();

    }

    private void addViews() {
        TabItem moviesTab = new TabItem(mContext, null);
        moviesTab.setTabIcon(R.drawable.ic_movies);
        moviesTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[0]);
        tabNavigationBottomMenu.getTabAt(0).setCustomView(moviesTab.getView());

        TabItem favoritesTab = new TabItem(mContext, null);
        favoritesTab.setTabIcon(R.drawable.ic_favorite);
        favoritesTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[1]);
        tabNavigationBottomMenu.getTabAt(1).setCustomView(favoritesTab.getView());


        TabItem settingsTab = new TabItem(mContext, null);
        settingsTab.setTabIcon(R.drawable.ic_settings);
        settingsTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[2]);
        tabNavigationBottomMenu.getTabAt(2).setCustomView(settingsTab.getView());

        TabItem aboutsTab = new TabItem(mContext, null);
        aboutsTab.setTabIcon(R.drawable.ic_about);
        aboutsTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[3]);
        tabNavigationBottomMenu.getTabAt(3).setCustomView(aboutsTab.getView());
    }

    public void initialPages() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(MovieView.instantiate(mContext, MovieView.class.getName()));
        fragments.add(FavoriteView.instantiate(mContext, FavoriteView.class.getName()));
        fragments.add(SettingFragment.instantiate(mContext, SettingFragment.class.getName()));
        fragments.add(AboutFragment.instantiate(mContext, AboutFragment.class.getName()));
        mViewPager.setAdapter(new SectionPaperAdapter(mContext, getSupportFragmentManager(), fragments));
        tabNavigationBottomMenu.setupWithViewPager(mViewPager);
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
         * => Done
         */
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
}
