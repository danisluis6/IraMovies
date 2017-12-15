package vn.enclave.iramovies.ui.activities.base.Splash;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.BaseView;

/*
 * Created by lorence on 08/11/2017.
 */

/**
 * @Run: http://awsrh.blogspot.com/2017/10/welcome-screen-with-uptodown-animation.html
 * => Implement Splash Screen
 */

public class SplashScreen extends BaseView {

    @BindView(R.id.vpCarousel)
    ViewPager vpCarousel;

    @BindView(R.id.SliderDots)
    LinearLayout SliderDots;

    private int dotscount;
    private ImageView[] dots;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void activityCreated(Bundle savedInstanceState) {
        hiddenStatusBar();
        CarouselAdapter mCarouselAdapter = new CarouselAdapter(mContext, getBinder());
        vpCarousel.setAdapter(mCarouselAdapter);

        dotscount = mCarouselAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int index = 0; index < dotscount; index++ ) {
            dots[index] = new ImageView(mContext);
            dots[index].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.noactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[index]);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));
        vpCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int index = 0; index < dotscount; index++) {
                    dots[index].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.noactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hiddenStatusBar() {
        getRootView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    class CarouselAdapter extends PagerAdapter {

        @BindView(R.id.imv_carousel_item)
        ImageView imvCarouselItem;

        Context mContext;
        LayoutInflater mLayoutInflater;
        Unbinder mUnbinder;

        private int[] mImages = {
                R.drawable.carousel_roar,
                R.drawable.carousel_snowy,
                R.drawable.carousel_medieval,
                R.drawable.carousel_gameofthor,
        };

        CarouselAdapter(Context context, Unbinder unbinder) {
            mContext = context;
            mUnbinder = unbinder;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mLayoutInflater.inflate(R.layout.carousel_item, null);
            mUnbinder = ButterKnife.bind(this, view);
            imvCarouselItem.setImageResource(mImages[position]);
            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);
        }
    }
}
