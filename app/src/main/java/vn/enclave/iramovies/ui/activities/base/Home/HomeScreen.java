package vn.enclave.iramovies.ui.activities.base.Home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.BaseView;


public class HomeScreen extends BaseView {

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.profile)
    public ListView mDrawerList;

    @BindView(R.id.main_content)
    public View mMainContent;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.layoutMenu)
    public RelativeLayout mLayoutMenu;

    private float mLastTranslate = 0.0f;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void activityCreated() {

        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawerToggle();
        //noinspection deprecation
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name, R.string.app_name);
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
}
