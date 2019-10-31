package vn.enclave.iramovies.ui.fragments.Movie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.SessionManager;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Detail.MovieDetailView;
import vn.enclave.iramovies.ui.fragments.Movie.adapter.MovieAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: Apply Mode-View_Presenter : MVP
 * => Done
 * @Run: https://stackoverflow.com/questions/28494637/android-how-to-stop-refreshing-fragments-on-tab-change
 * => Done
 * @Run: https://www.coderefer.com/android-recyclerview-cardview-tutorial/
 * => @TODO
 * @Run: http://pointofandroid.blogspot.com/2016/12/recyclerviewhorizontal-and-vertical.html
 * => Done
 * @Run: https://www.youtube.com/results?search_query=nested+fragment+viewpager
 * => nested fragment viewpager
 * @Run: https://stackoverflow.com/questions/39491655/communication-between-nested-fragments-in-android
 * => Communicate between nested fragments
 * @Run: https://tausiq.wordpress.com/2014/06/06/android-multiple-fragments-stack-in-each-viewpager-tab/
 * => Research More
 * @Run: https://stackoverflow.com/questions/14740445/what-is-difference-between-getsupportfragmentmanager-and-getchildfragmentmanag
 * @Run: https://stackoverflow.com/questions/39885502/communication-between-nested-fragments-activities-both-ways
 * => Done
 */

public class MovieView extends IRBaseFragment implements IMovieView {

    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;

    /* RecyclerView */
    @BindView(R.id.rcvMovies)
    public RecyclerView rcvMovies;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieInterface mMovieInterface;
    private MovieAdapter mMoviesAdapter;
    private List<Movie> mGroupMovies;

    /**
     * Work with MVP
     */
    private MoviePresenter mMoviesPresenter;
    /**
     * Work with load more
     */
    private int mPageIndex;
    private boolean mIsLoadMore = false;
    private UpdatedFavoriteScreen mInterfaceRefresh;
    private MODE mType;

    public MovieView() {
    }

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void fragmentCreated() {
        initViews();
        initAttributes();
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            getMoviesFromApi();
        } else {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},
                    Constants.Permissions.ACCESS_INTERNET);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getMoviesFromApi() {
        handleData(mGroupMovies);
    }

    private void handleData(List<Movie> movies) {
        if (Utils.isInternetOn(mActivity)) {
            mMoviesPresenter.getMoviesFromApi(mPageIndex, true, mType);
        } else {
            if (movies.isEmpty()) {
                mFailureLayout.setFailureMessage(getResources().getString(R.string.no_internet_connection));
            } else {
                Utils.Toast.showToast(mActivity, getString(R.string.no_internet_connection));
            }
        }
    }

    private void initViews() {
        mGroupMovies = new ArrayList<>();
        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (rcvMovies != null) {
            rcvMovies.setHasFixedSize(true);
        }
        if (mMoviesAdapter == null) {
            mMoviesAdapter = new MovieAdapter(mActivity, mActivity, mGroupMovies, true, new MovieAdapter.IMovieAdapter() {
                @Override
                public void openDetailMovie(Movie movie) {
                    MovieDetailView mDetailView = new MovieDetailView();
                    mDetailView.setArguments(getMovieBundle(movie));
                    mMovieInterface.getMovieDetailFragment(mDetailView, movie);
                }
            });
        }
        mLayoutManager = new LinearLayoutManager(mActivity);
        rcvMovies.setLayoutManager(mLayoutManager);
        rcvMovies.setAdapter(mMoviesAdapter);
        mMoviesAdapter.setMoreDataAvailable(true);
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesAdapter.setLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvMovies.post(new Runnable() {
                    @Override
                    public void run() {
                        loadNextDataFromApi();
                    }
                });
            }
        });
        mMoviesAdapter.setChooseFavoriteListener(new MovieAdapter.OnChooseFavoriteListener() {
            @Override
            public void onChoose(Movie movie) {
                mMoviesPresenter.addMovie(movie);
            }

            @Override
            public void onRemove(Movie movie) {
                mMoviesPresenter.deleteMovie(movie);
            }
        });
    }

    private Bundle getMovieBundle(Movie movie) {
        Bundle mBundle = new Bundle();
        mBundle.putInt(DatabaseInfo.Movie.COLUMN_ID, movie.getId());
        mBundle.putString(DatabaseInfo.Movie.COLUMN_TITLE, movie.getTitle());
        mBundle.putString(DatabaseInfo.Movie.COLUMN_POSTER_PATH, movie.getPosterPath());
        mBundle.putString(DatabaseInfo.Movie.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        mBundle.putDouble(DatabaseInfo.Movie.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        mBundle.putString(DatabaseInfo.Movie.COLUMN_OVERVIEW, movie.getOverview());
        mBundle.putInt(DatabaseInfo.Movie.COLUMN_FAVORITE, movie.getFavorite());
        return mBundle;
    }

    private void loadNextDataFromApi() {
        if (mIsLoadMore) {
            return;
        }
        mMoviesAdapter.add(new Movie(Constants.Objects.LOAD));
        mIsLoadMore = true;
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesPresenter.getMoviesFromApi(mPageIndex, false, mType);
    }

    private void initAttributes() {
        mMoviesPresenter = new MoviePresenter(mActivity);
        mMoviesPresenter.attachView(this);

        mPageIndex = Constants.FIRST_PAGE;
        mType = MODE.POPULAR;
        mMoviesAdapter.setMoreDataAvailable(true);
    }

    @Override
    public void showProgressDialog(boolean isLoadMore) {
        if (!mDiaLoadView.isShowing() && isLoadMore) {
            mDiaLoadView.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mDiaLoadView.isShowing()) {
            mDiaLoadView.dismiss();
        }
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        mMoviesAdapter.setMoreDataAvailable(mPageIndex < SessionManager.getInstance(mActivity).getTotalPages());
        ++mPageIndex; /* End fix */
        if (mIsLoadMore) {
            // Handle the dismiss loadingMore if there is another caller API is executing
            updateListMovies(getListMovies(movies));
        } else {
            dismissProgressDialog();
            mMoviesAdapter.setMovies(getListMovies(movies));
        }
    }

    private List<Movie> getListMovies(List<Movie> movies) {
        List<Movie> temps = new ArrayList<>();
        if (TextUtils.equals(SessionManager.getInstance(mActivity).getRate(), Constants.EMPTY_STRING)) {
            return movies;
        }
        for (int index = 0; index < movies.size(); index++ ) {
            if (movies.get(index).getVoteAverage() >= Double.parseDouble(SessionManager.getInstance(mActivity).getRate())) {
                temps.add(movies.get(index));
            }
        }
        return temps;
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        mMovieInterface.updateCountStarOnMenu(movie.getFavorite());
        mMovieInterface.refreshStarInFavoriteScreen(movie);
        if (mInterfaceRefresh != null) {
            mInterfaceRefresh.onRefreshFavoriteOnDetailScreen(movie);
        }
    }

    private void updateListMovies(List<Movie> listMovies) {
        mMoviesAdapter.remove(mMoviesAdapter.getItemCount() - 1);
        mMoviesAdapter.addAll(listMovies);
        mIsLoadMore = false;
    }

    @Override
    public void onFailure(String message) {
        Utils.Toast.showToast(mActivity, message);
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        mMovieInterface.updateCountStarOnMenu(movie.getFavorite());
        mMovieInterface.refreshStarInFavoriteScreen(movie);
        if (mInterfaceRefresh != null) {
            mInterfaceRefresh.onRefreshFavoriteOnDetailScreen(movie);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.ACCESS_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                for (int permissionId : grantResults) {
                    if (permissionId != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "Permission denied!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                handleData(mGroupMovies);
                break;
            }
        }
    }

    public void removeMovieFavorite(Movie movie) {
        mMoviesAdapter.refreshFavorite(movie);
    }

    public void setMovieInterface(MovieInterface movieInterface) {
        this.mMovieInterface = movieInterface;
    }

    public void setOnDisplay(boolean onDisplay) {
        rcvMovies.setAdapter(mMoviesAdapter);
        if (onDisplay) {
            rcvMovies.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            rcvMovies.setLayoutManager(new GridLayoutManager(mActivity, 2));
        }
        mMoviesAdapter.setModeDisplay(onDisplay);
    }

    /**
     * Initialize object FragmentManger to manager fragment
     */
    public void openMovieDetail(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_movies, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public void refreshStatusFavorite(Movie movie) {
        mMoviesAdapter.refreshStatusFavorite(movie);
    }

    public void deleteMovie(Movie movie) {
        mMoviesPresenter.deleteMovie(movie);
    }

    public void setOnRefreshFavoriteOnMovieScreen(UpdatedFavoriteScreen mInterfaceRefresh) {
        this.mInterfaceRefresh = mInterfaceRefresh;
    }

    public void reloadCategory(String category) {
        if (!TextUtils.equals(category, Constants.EMPTY_STRING)) {
            resetPageIndex(Constants.FIRST_PAGE);
            switch (getType(category)) {
                case POPULAR:
                    mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.POPULAR);
                    break;
                case TOP_RATED:
                    mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.TOP_RATED);
                    break;
                case UPCOMING:
                    mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.UPCOMING);
                    break;
                case NOW_PLAYING:
                    mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.NOW_PLAYING);
                    break;
                default:
                    mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.POPULAR);
                    break;
            }
        }
    }

    private void resetPageIndex(int firstPage) {
        mPageIndex = firstPage;
        mIsLoadMore = false;
    }

    public MODE getType(String category) {
        if (TextUtils.equals(category, getString(R.string.category_popular))) {
            return MODE.POPULAR;
        } else if (TextUtils.equals(category, getString(R.string.category_top_rated))) {
            return MODE.TOP_RATED;
        } else if (TextUtils.equals(category, getString(R.string.category_up_coming))) {
            return MODE.UPCOMING;
        } else if (TextUtils.equals(category, getString(R.string.category_now_playing))) {
            return MODE.NOW_PLAYING;
        }
        return MODE.POPULAR;
    }

    public void reloadRating() {
        resetPageIndex(Constants.FIRST_PAGE);
        mMoviesPresenter.getMoviesFromApi(mPageIndex, false, mType);
    }

    enum MODE {
        POPULAR, TOP_RATED, UPCOMING, NOW_PLAYING
    }

    /* Interface */
    public interface MovieInterface {
        void refreshStarInFavoriteScreen(Movie movie);

        void updateCountStarOnMenu(int value);

        void getMovieDetailFragment(MovieDetailView movieDetailView, Movie movie);
    }

    public interface UpdatedFavoriteScreen {
        void onRefreshFavoriteOnDetailScreen(Movie movie);
    }
}