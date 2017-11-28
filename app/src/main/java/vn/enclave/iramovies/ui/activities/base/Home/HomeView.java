package vn.enclave.iramovies.ui.activities.base.Home;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.activities.base.Home.adapters.PaperAdapter;
import vn.enclave.iramovies.ui.fragments.About.AboutView;
import vn.enclave.iramovies.ui.fragments.Detail.MovieDetailView;
import vn.enclave.iramovies.ui.fragments.Favorite.FavoriteView;
import vn.enclave.iramovies.ui.fragments.Movie.MovieView;
import vn.enclave.iramovies.ui.fragments.Setting.SettingView;
import vn.enclave.iramovies.ui.views.TabItem;
import vn.enclave.iramovies.ui.views.ToolbarLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;


/**
 * @Run: https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * => Done
 * @Run: https://www.google.com/search?q=create+badge+icon+at+corner+layout&client=ubuntu&hs=Amc&channel=fs&source=lnms&sa=X&ved=0ahUKEwiu9dnh_MTXAhVHPo8KHV3OBAgQ_AUICSgA&biw=1505&bih=877&dpr=1
 * => Done
 * @Run: https://stackoverflow.com/questions/8348707/prevent-viewpager-from-destroying-off-screen-views
 * => Done mViewPager.setOffscreenPageLimit(tabNavigationBottomMenu.getTabCount()-1);
 * @Run: https://github.com/smartherd/AndroidToolbars/blob/master/app/src/main/java/com/example/sriyanksiddhartha/androidtoolbar/MainActivity.java
 * => Done
 * @Run: https://material.io/icons/
 * @Run: https://stackoverflow.com/questions/27556623/creating-a-searchview-that-looks-like-the-material-design-guidelines
 * => Implement
 * @Run: https://stackoverflow.com/questions/7066657/android-how-to-dynamically-change-menu-item-text-outside-of-onoptionsitemssele
 * => Implement
 * @Run: https://stackoverflow.com/questions/39963330/android-menuitem-setshowasaction-not-working
 * => Done
 * @Run: https://stackoverflow.com/questions/4207880/android-how-do-i-prevent-the-soft-keyboard-from-pushing-my-view-up
 * => Done
 * @Run: https://stackoverflow.com/questions/20639464/actionbaractivity-with-actionbardrawertoggle-not-using-drawerimageres
 * => Done
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

    private MovieView mMovieView;
    private FavoriteView mFavoriteView;
    private SettingView mSettingView;
    private AboutView mAboutView;
    private boolean isModeView = true;


    private TabItem moviesTab;
    private TabItem favoritesTab;
    private TabItem settingsTab;
    private TabItem aboutsTab;

    private MovieDetailView mDetailViewMovie;
    private MovieDetailView mDetailViewFavorite;

    private PaperAdapter mPageAdapter;
    private Menu mMenu;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void activityCreated(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setupDrawerToggle();
        // noinspection deprecation
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // init Fragments in ViewPaper
        initFragments();
        initialPages();
        initViews();
        defineFragmentOnViewPaper();
    }

    private void initFragments() {
        mMovieView = (MovieView) MovieView.instantiate(mContext, MovieView.class.getName());
        mFavoriteView = (FavoriteView) FavoriteView.instantiate(mContext, FavoriteView.class.getName());
        mSettingView = (SettingView) SettingView.instantiate(mContext, SettingView.class.getName());
        mAboutView = (AboutView) AboutView.instantiate(mContext, AboutView.class.getName());
    }

    private void initViews() {
        moviesTab = new TabItem(mContext, null);
        moviesTab.setTabIcon(R.drawable.ic_movies);
        moviesTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[0]);
        tabNavigationBottomMenu.getTabAt(0).setCustomView(moviesTab.getView());

        favoritesTab = new TabItem(mContext, null);
        favoritesTab.setTabIcon(R.drawable.ic_favorite);
        favoritesTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[1]);
        tabNavigationBottomMenu.getTabAt(1).setCustomView(favoritesTab.getView());


        settingsTab = new TabItem(mContext, null);
        settingsTab.setTabIcon(R.drawable.ic_settings);
        settingsTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[2]);
        tabNavigationBottomMenu.getTabAt(2).setCustomView(settingsTab.getView());

        aboutsTab = new TabItem(mContext, null);
        aboutsTab.setTabIcon(R.drawable.ic_about);
        aboutsTab.setTabText(getResources().getStringArray(R.array.menu_bottom_nav)[3]);
        tabNavigationBottomMenu.getTabAt(3).setCustomView(aboutsTab.getView());

        updateTitleBar(getResources().getString(R.string.popular));
    }

    public void initialPages() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(mMovieView);
        fragments.add(mFavoriteView);
        fragments.add(mSettingView);
        fragments.add(mAboutView);
        mPageAdapter = new PaperAdapter(mContext, getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(mPageAdapter.getCount() - 1);
        updateFragmentOnViewPaper();
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

    /**
     * @Run: seprate each child fragments
     * => Done
     * @Run: Faxage => Shoot change view[read/un-read] update to NavigationBar => The same
     * => OnActivityForResult
     */
    private void defineFragmentOnViewPaper() {
        mMovieView.setMovieInterface(new MovieView.MovieInterface() {
            @Override
            public void refreshStarInFavoriteScreen(Movie movie) {
                mFavoriteView.refreshStatusFavorite(movie);
            }

            @Override
            public void updateCountStarOnMenu(int value) {
                favoritesTab.updateNumberFavorites(value);
            }

            @Override
            public void getMovieDetailFragment(MovieDetailView movieDetailView, Movie movie) {
                final String title = mToolbar.getToolbar().getTitle().toString();
                movieDetailView.setMovieDetailInterface(new MovieDetailView.MovieDetailInterface() {
                    @Override
                    public void onDestroy() {
                        if (!isFinishing()) {
                            updateTitleBar(title);
                        }
                    }

                    @Override
                    public void updateCountStarOnMenu(int value) {
                        favoritesTab.updateNumberFavorites(value);
                    }

                    @Override
                    public void refreshStarInFavoriteScreen(Movie movie) {
                        mFavoriteView.refreshStatusFavorite(movie);
                    }

                    @Override
                    public void refreshStarInMovieScreen(Movie movie) {
                        if (movie.getFavorite() == Constants.Favorites.DEFAULT) {
                            mMovieView.removeMovieFavorite(movie);
                        } else {
                            mMovieView.refreshStatusFavorite(movie);
                        }
                    }

                    @Override
                    public void refreshStarInDetailScreen(Movie movie) {
                        if (mDetailViewMovie != null) {
                            mDetailViewMovie.reload(movie);
                        }
                    }
                });
                mDetailViewMovie = movieDetailView;
                mMovieView.openMovieDetail(movieDetailView);
                updateTitleBar(movie.getTitle());
            }
        });

        mFavoriteView.setFavoriteInterface(new FavoriteView.FavoriteInterface() {
            @Override
            public void setTotalFavoritesOnMenu(int total) {
                favoritesTab.setNumberFavorites(total);
            }

            @Override
            public void updateCountFavoritesOnMenu(int value) {
                favoritesTab.updateNumberFavorites(value);
            }

            @Override
            public void getMovieDetailFragment(final MovieDetailView movieDetailView, Movie movie) {
                final String title = mToolbar.getToolbar().getTitle().toString();
                movieDetailView.setMovieDetailInterface(new MovieDetailView.MovieDetailInterface() {
                    @Override
                    public void onDestroy() {
                        if (!isFinishing()) {
                            updateTitleBar(title);
                        }
                    }

                    @Override
                    public void updateCountStarOnMenu(int value) {
                        favoritesTab.updateNumberFavorites(value);
                    }

                    @Override
                    public void refreshStarInFavoriteScreen(Movie movie) {
                        mFavoriteView.refreshStatusFavorite(movie);
                    }

                    @Override
                    public void refreshStarInMovieScreen(Movie movie) {
                        if (movie.getFavorite() == Constants.Favorites.DEFAULT) {
                            mMovieView.removeMovieFavorite(movie);
                        } else {
                            mMovieView.refreshStatusFavorite(movie);
                        }
                    }

                    @Override
                    public void refreshStarInDetailScreen(Movie movie) {
                        if (mDetailViewMovie != null) {
                            mDetailViewMovie.reload(movie);
                        }
                    }
                });
                mDetailViewFavorite = movieDetailView;
                mFavoriteView.openMovieDetail(movieDetailView);
                updateTitleBar(movie.getTitle());
            }
        });
    }

    private void updateNavigationIcon(Drawable drawable) {
        mToolbar.getToolbar().setNavigationIcon(drawable);
    }

    private void updateFragmentOnViewPaper() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
                switch (position) {
                    case 0:
                        if (mMovieView.getChildFragmentManager().getBackStackEntryCount() == 0) {
                            updateTitleBar(getResources().getString(R.string.popular));
                            mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
                        } else {
                            mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_arrow_back);
                        }
                        mMenu.findItem(R.id.search).setVisible(false);
                        mMenu.findItem(R.id.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        mMenu.findItem(R.id.view_list).setVisible(true);
                        mMenu.findItem(R.id.view_list).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        mMovieView.setOnRefreshFavoriteOnMovieScreen(new MovieView.UpdatedFavoriteScreen() {
                            @Override
                            public void onRefreshFavoriteOnDetailScreen(Movie movie) {
                                if (mDetailViewFavorite != null) {
                                    mDetailViewFavorite.reload(movie);
                                }
                            }
                        });
                        break;
                    case 1:
                        if (mFavoriteView.getChildFragmentManager().getBackStackEntryCount() == 0) {
                            updateTitleBar(getResources().getString(R.string.favorites));
                            mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
                        } else {
                            mToolbar.getToolbar().setNavigationIcon(R.drawable.ic_arrow_back);
                        }
                        mMenu.findItem(R.id.search).setVisible(true);
                        mMenu.findItem(R.id.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        mMenu.findItem(R.id.view_list).setVisible(false);
                        mMenu.findItem(R.id.view_list).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        mFavoriteView.setOnRefreshFavoriteOnMovieScreen(new FavoriteView.UpdatedFavoriteScreen() {
                            @Override
                            public void onRefreshFavoriteOnMovieScreen(Movie movie) {
                                mMovieView.removeMovieFavorite(movie);
                            }

                            @Override
                            public void onRefreshFavoriteOnDetailScreen(Movie movie) {
                                if (mDetailViewMovie != null) {
                                    mDetailViewMovie.reload(movie);
                                }
                            }
                        });
                        break;
                    case 2:
                        updateTitleBar(getResources().getString(R.string.settings));
                        mMenu.findItem(R.id.search).setVisible(false);
                        mMenu.findItem(R.id.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        mMenu.findItem(R.id.view_list).setVisible(false);
                        mMenu.findItem(R.id.view_list).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        break;
                    case 3:
                        updateTitleBar(getResources().getString(R.string.about));
                        mMenu.findItem(R.id.search).setVisible(false);
                        mMenu.findItem(R.id.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        mMenu.findItem(R.id.view_list).setVisible(false);
                        mMenu.findItem(R.id.view_list).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void updateTitleBar(String title) {
        mToolbar.getToolbar().setTitle(title);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        initSearchView(menu);
        mMenu = menu;
        return true;
    }

    private void initSearchView(Menu menu) {
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(getWidthTextToolbar());
        removeSearchIconAsHint(mSearchView);
        removeSearchPlateInSearchView(mSearchView);
    }

    /**
     * @param searchView
     * @Run; https://stackoverflow.com/questions/27687116/how-to-remove-search-plate-in-searchview
     * => Done
     * @Run: https://stackoverflow.com/questions/30842921/how-to-remove-white-underline-in-a-searchview-widget-in-toolbar-android
     * => Done
     * @Run: => Done
     */

    private void removeSearchPlateInSearchView(final SearchView searchView) {
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        final EditText edtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        edtSearch.setHintTextColor(Color.parseColor("#919CD5"));
        edtSearch.setTextColor(Color.WHITE);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Utils.dimissKeyBoard(HomeView.this);
                    Utils.clearFocusOnSearchView(edtSearch);
                    mFavoriteView.filter(searchView.getQuery());
                    return true;
                }
                return false;
            }
        });
        mFavoriteView.filterAutomatic(edtSearch);
    }

    /**
     * @param searchView
     * @Run: https://stackoverflow.com/questions/20323990/remove-the-searchicon-as-hint-in-the-searchview
     * => Done
     * @Run: https://stackoverflow.com/questions/27556623/creating-a-searchview-that-looks-like-the-material-design-guidelines
     * => Done
     * @Run: https://stackoverflow.com/questions/31874836/why-is-androids-searchview-widgets-imageview-with-id-search-mag-icon-returni
     * => Remove icon right hint search => DONE
     * @Run: https://stackoverflow.com/questions/27687116/how-to-remove-search-plate-in-searchview
     * => Go to resource => Remove public static final int search_plate = 0x7f0f008b;
     * => Done
     */

    private void removeSearchIconAsHint(SearchView searchView) {
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        SpannableStringBuilder ssb = new SpannableStringBuilder(Constants.EMPTY_STRING);
        ssb.append(getString(R.string.search_view));
        searchEditText.setHint(ssb);
        searchView.setIconifiedByDefault(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.movies:
                mViewPager.setCurrentItem(Constants.Tab.Movie);
                break;
            case R.id.favorites:
                mViewPager.setCurrentItem(Constants.Tab.Favorite);
                break;
            case R.id.settings:
                mViewPager.setCurrentItem(Constants.Tab.Setting);
                break;
            case R.id.about:
                mViewPager.setCurrentItem(Constants.Tab.About);
                break;
            case R.id.view_list:
                item.setIcon(isModeView ? ContextCompat.getDrawable(this, R.drawable.ic_view_thumnail) : ContextCompat.getDrawable(this, R.drawable.ic_view_list));
                isModeView = !isModeView;
                mMovieView.setOnDisplay(isModeView);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getWidthTextToolbar() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels - 32;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = mPageAdapter.getItem(mViewPager.getCurrentItem());
        FragmentManager fm = fragment.getChildFragmentManager();
        if (fm != null) {
            int count = fm.getBackStackEntryCount();
            if (count > 0) {
                updateNavigationIcon(getDrawable(R.drawable.ic_menu));
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
